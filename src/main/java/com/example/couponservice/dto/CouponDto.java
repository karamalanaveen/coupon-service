package com.example.couponservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CouponDto {

    private Long id;

    @NotBlank(message = "Coupon code is mandatory")
    private String code;

    @NotNull(message = "Discount is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Discount must be greater than 0")
    private BigDecimal discount;

    @NotNull(message = "Expiration date is mandatory")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expDate;

    public CouponDto() {
    }

    public CouponDto(Long id, String code, BigDecimal discount, LocalDate expDate) {
        this.id = id;
        this.code = code;
        this.discount = discount;
        this.expDate = expDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }
}
