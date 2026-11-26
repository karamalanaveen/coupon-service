package com.example.couponservice.service.impl;

import com.example.couponservice.dto.CouponDto;
import com.example.couponservice.entity.Coupon;
import com.example.couponservice.exception.ResourceNotFoundException;
import com.example.couponservice.repository.CouponRepository;
import com.example.couponservice.service.CouponService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public CouponDto createCoupon(CouponDto couponDto) {
        if (couponRepository.existsByCode(couponDto.getCode())) {
            throw new IllegalArgumentException("Coupon with code '" + couponDto.getCode() + "' already exists");
        }

        Coupon coupon = mapToEntity(couponDto);
        Coupon savedCoupon = couponRepository.save(coupon);
        return mapToDto(savedCoupon);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CouponDto> getAllCoupons() {
        List<Coupon> coupons = couponRepository.findAll();
        return coupons.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CouponDto getCouponById(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon", "id", id));
        return mapToDto(coupon);
    }

    @Override
    @Transactional(readOnly = true)
    public CouponDto getCouponByCode(String code) {
        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon", "code", code));
        return mapToDto(coupon);
    }

    @Override
    public CouponDto updateCoupon(Long id, CouponDto couponDto) {
        Coupon existingCoupon = couponRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon", "id", id));

        // If coupon code is changed, check for uniqueness
        if (!existingCoupon.getCode().equalsIgnoreCase(couponDto.getCode()) &&
                couponRepository.existsByCode(couponDto.getCode())) {
            throw new IllegalArgumentException("Coupon with code '" + couponDto.getCode() + "' already exists");
        }

        existingCoupon.setCode(couponDto.getCode());
        existingCoupon.setDiscount(couponDto.getDiscount());
        existingCoupon.setExpDate(couponDto.getExpDate());

        Coupon updatedCoupon = couponRepository.save(existingCoupon);
        return mapToDto(updatedCoupon);
    }

    @Override
    public void deleteCoupon(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon", "id", id));
        couponRepository.delete(coupon);
    }

    private CouponDto mapToDto(Coupon coupon) {
        return new CouponDto(
                coupon.getId(),
                coupon.getCode(),
                coupon.getDiscount(),
                coupon.getExpDate()
        );
    }

    private Coupon mapToEntity(CouponDto couponDto) {
        return new Coupon(
                couponDto.getId(),
                couponDto.getCode(),
                couponDto.getDiscount(),
                couponDto.getExpDate()
        );
    }
}
