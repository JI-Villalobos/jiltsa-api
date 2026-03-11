package com.jiltsa.admin.sales.projections;

import com.jiltsa.admin.operativity.domain.dto.SaleResultDto;
import com.jiltsa.admin.sales.domain.dto.SalesProjectionDto;
import com.jiltsa.admin.sales.domain.dto.TotalSalesDto;
import com.jiltsa.admin.sales.persistence.entity.Sale;
import com.jiltsa.admin.sales.persistence.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesProjectionService {
    private final SaleRepository saleRepository;

    public SalesProjectionDto getSalesProjection(Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate){
        List<Sale> sales = saleRepository.findByBranchIdAndTimestampBetween(branchId, initialDate, finalDate);

        Map<String, Double> totalByCategory = sales.stream().collect(
                Collectors.groupingBy(
                        Sale::getCategory,
                        Collectors.summingDouble(Sale::getTotal)
                )
        );

        Map<String, Double> totalByUser = sales.stream().collect(
                Collectors.groupingBy(
                        Sale::getUser,
                        Collectors.summingDouble(Sale::getTotal)
                )
        );

        LinkedHashMap<String, Double> totalByArticle = sales.stream().collect(
                Collectors.groupingBy(
                        Sale::getDescription,
                        Collectors.summingDouble(Sale::getTotal)
                )
        )
                .entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder()))
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

    private Map<String, Double> getBestSellingArticles(LinkedHashMap<String, Double> totalByArticle){
        return totalByArticle.entrySet().stream().limit(5).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}

