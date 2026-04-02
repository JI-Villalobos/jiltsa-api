package com.jiltsa.admin.sales.domain.dto;

public record SaleSummaryDto( double total, int quantity, double utility, long count) {
    public SaleSummaryDto merge(SaleSummaryDto sale){
        return new SaleSummaryDto(
                this.total + sale.total,
                this.quantity + sale.quantity,
                this.utility + sale.utility,
                this.count + sale.count
        );
    }
}

