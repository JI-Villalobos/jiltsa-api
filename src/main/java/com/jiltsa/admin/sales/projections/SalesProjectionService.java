package com.jiltsa.admin.sales.projections;

import com.jiltsa.admin.branch.domain.dto.BranchDto;
import com.jiltsa.admin.branch.domain.service.BranchService;
import com.jiltsa.admin.sales.domain.dto.*;
import com.jiltsa.admin.sales.persistence.entity.Sale;
import com.jiltsa.admin.sales.persistence.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SalesProjectionService {
    static final int DAILY_SALES_DAYS = 30;

    private final SaleRepository saleRepository;
    private final BranchService branchService;

    public SalesProjectionDto getSalesProjection(Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate){
        List<Sale> rawSales = saleRepository.findByBranchIdAndTimestampBetween(branchId, initialDate, finalDate);
        List<Sale> sales = rawSales.stream().filter(sale -> !sale.getKey().equals("TA")).toList();
        List<Sale> onlyMedicine = sales.stream().filter(sale -> sale.getCategory().equals("MEDICAMENTO")).toList();

        Map<String, SaleSummaryDto> totalByCategory = sales.stream().collect(
                Collectors.toUnmodifiableMap(
                        Sale::getCategory,
                        sale -> new SaleSummaryDto(sale.getTotal(), sale.getQuantity(), sale.getApproximatedUtility(), 1),
                        SaleSummaryDto::merge
                )
        );

        //this is ok
        Map<String, Double> totalByUser = sales.stream().collect(
                Collectors.groupingBy(
                        Sale::getUser,
                        Collectors.summingDouble(Sale::getTotal)
                )
        );

        LinkedHashMap<String, SaleSummaryDto> totalByArticle = onlyMedicine.stream().collect(
                Collectors.toUnmodifiableMap(
                        Sale::getDescription,
                        sale -> new SaleSummaryDto(sale.getTotal(), sale.getQuantity(), sale.getApproximatedUtility(), 1),
                        SaleSummaryDto::merge
                )
        )
                .entrySet().stream()
                .sorted(Map.Entry.<String, SaleSummaryDto>comparingByValue(Comparator.comparingInt(SaleSummaryDto::quantity).reversed()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1,e2) -> e1,
                        LinkedHashMap::new
                ));




        return new SalesProjectionDto(totalByCategory, totalByUser, getBestSellingArticles(totalByArticle));
    }

    public TotalSalesDto getTotalSales(Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate){
        List<Sale> sales = saleRepository.findByBranchIdAndTimestampBetween(branchId, initialDate, finalDate);

        List<Sale> filteredSales = sales.stream().filter(sale -> !sale.getKey().equals("TA")).toList();
        Double total = filteredSales.stream().reduce(0.0, (amount, sale) -> amount + sale.getTotal(), Double::sum);

        return new TotalSalesDto(branchId, total);
    }

    private Map<String, SaleSummaryDto> getBestSellingArticles(LinkedHashMap<String, SaleSummaryDto> totalByArticle){
        return totalByArticle.entrySet().stream().limit(5).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public PharmacySalesResumeDto getPharmacySales (Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate) {
        Predicate<Sale> med = sale -> sale.getCategory().equals("MEDICAMENTO");
        Predicate<Sale> mat = sale -> sale.getCategory().equals("MATERIAL DE CURACION/INSTR.");
        Predicate<Sale> vit = sale -> sale.getCategory().equals("VITAMINAS Y SUPLEMENTOS");

        List<Sale> sales = saleRepository.findByBranchIdAndTimestampBetween(branchId, initialDate, finalDate);
        List<Sale> pharmacySales = sales.stream().filter(med.or(mat).or(vit)).toList();

        Double pharmacy = pharmacySales.stream().reduce(0.0, (acc, curr) -> acc + curr.getTotal(), Double::sum);
        Double utility = pharmacySales.stream().reduce(0.0, (acc, curr) -> acc + curr.getApproximatedUtility(), Double::sum);

        return new PharmacySalesResumeDto(pharmacy, utility, branchId);
    }

    public Map<String, SaleSummaryDto> computeBudget(Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate){
        List<String> pharmacyCat = List.of("MEDICAMENTO", "MATERIAL DE CURACION/INSTR.", "VITAMINAS Y SUPLEMENTOS");
        List<Sale> sales = saleRepository.findByBranchIdAndTimestampBetweenAndCategoryIn(branchId, initialDate, finalDate, pharmacyCat);

        return sales.stream().collect(
                Collectors.toUnmodifiableMap(
                        Sale::getCategory,
                        sale -> new SaleSummaryDto(sale.getTotal(), sale.getQuantity(), sale.getApproximatedUtility(), 1),
                        SaleSummaryDto::merge
                )
        );
    }

    public List<ConsignmentSaleDto> consignmentSalesReport(LocalDateTime initialDate, LocalDateTime finalDate){
        List<ConsignmentSaleDto> consignmentSales = new ArrayList<>();
        List<Sale> sales = saleRepository.findByTimestampBetweenAndCategoryIn(initialDate, finalDate, List.of("ORLANDO"));

        sales.forEach(sale -> consignmentSales.add(
                new ConsignmentSaleDto(
                        sale.getBranchId(),
                        sale.getDescription(),
                        sale.getQuantity(),
                        sale.getTotal(),
                        sale.getTimestamp()
                )
        ));

        return consignmentSales;
    }

    public DailySalesProjectionDto getDailySales() {
        //user of this app are only from mx
        return getDailySales(LocalDate.now(ZoneId.of("America/Mexico_City")));
    }

    /** Sales per active branch for each of the {@value DAILY_SALES_DAYS} days ending on {@code today}. */
    DailySalesProjectionDto getDailySales(LocalDate today) {
        LocalDate initialDate = today.minusDays(DAILY_SALES_DAYS - 1);
        Map<Integer, Map<LocalDate, DailySaleTotal>> totalsByBranch = saleRepository
                .sumDailyByBranch(initialDate.atStartOfDay(), today.plusDays(1).atStartOfDay()).stream()
                .collect(Collectors.groupingBy(DailySaleTotal::branchId,
                        Collectors.toMap(DailySaleTotal::date, total -> total)));

        List<BranchDailySalesDto> branches = branchService.getAll().stream()
                .map(branch -> branchDailySales(branch, initialDate,
                        totalsByBranch.getOrDefault(branch.getId(), Map.of())))
                .toList();

        return new DailySalesProjectionDto(initialDate, today, branches);
    }

    private BranchDailySalesDto branchDailySales(BranchDto branch, LocalDate initialDate, Map<LocalDate, DailySaleTotal> totalsByDate) {
        List<DailySalesDto> days = initialDate.datesUntil(initialDate.plusDays(DAILY_SALES_DAYS))
                .map(date -> Optional.ofNullable(totalsByDate.get(date))
                        .map(total -> new DailySalesDto(date, orZero(total.total()), orZero(total.approximatedUtility()), false))
                        .orElseGet(() -> new DailySalesDto(date, 0, 0, true)))
                .toList();

        double totalSales = days.stream().mapToDouble(DailySalesDto::total).sum();
        long daysWithSales = days.stream().filter(day -> !day.pending()).count();
        double average = daysWithSales == 0 ? 0 : totalSales / daysWithSales;

        return new BranchDailySalesDto(branch.getId(), branch.getName(), totalSales, average, days);
    }

    private static double orZero(Double value) {
        return value == null ? 0 : value;
    }
}
