package com.example.couponservice.service;

import com.example.couponservice.dto.CouponDto;

import java.util.List;

public interface CouponService {

    CouponDto createCoupon(CouponDto couponDto);

    List<CouponDto> getAllCoupons();

    CouponDto getCouponById(Long id);

    CouponDto getCouponByCode(String code);

    CouponDto updateCoupon(Long id, CouponDto couponDto);

    void deleteCoupon(Long id);
}
