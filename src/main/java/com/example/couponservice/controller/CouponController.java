package com.example.couponservice.controller;

import com.example.couponservice.dto.CouponDto;
import com.example.couponservice.service.CouponService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    // 1. Create Coupon -> 201 CREATED
    @PostMapping
    public ResponseEntity<CouponDto> createCoupon(@Valid @RequestBody CouponDto couponDto) {
        CouponDto createdCoupon = couponService.createCoupon(couponDto);
        return new ResponseEntity<>(createdCoupon, HttpStatus.CREATED);
    }

    // 2. Get All Coupons -> 200 OK
    @GetMapping
    public ResponseEntity<List<CouponDto>> getAllCoupons() {
        List<CouponDto> coupons = couponService.getAllCoupons();
        return ResponseEntity.ok(coupons);
    }

    // 3. Get Coupon by ID -> 200 OK (or 404 NOT FOUND via exception handler)
    @GetMapping("/{id}")
    public ResponseEntity<CouponDto> getCouponById(@PathVariable("id") Long id) {
        CouponDto coupon = couponService.getCouponById(id);
        return ResponseEntity.ok(coupon);
    }

    // 4. Get Coupon by Code -> 200 OK (or 404 NOT FOUND via exception handler)
    @GetMapping("/code/{code}")
    public ResponseEntity<CouponDto> getCouponByCode(@PathVariable("code") String code) {
        CouponDto coupon = couponService.getCouponByCode(code);
        return ResponseEntity.ok(coupon);
    }

    // 5. Update Coupon -> 200 OK (or 404 NOT FOUND via exception handler)
    @PutMapping("/{id}")
    public ResponseEntity<CouponDto> updateCoupon(
            @PathVariable("id") Long id,
            @Valid @RequestBody CouponDto couponDto) {
        CouponDto updatedCoupon = couponService.updateCoupon(id, couponDto);
        return ResponseEntity.ok(updatedCoupon);
    }

    // 6. Delete Coupon -> 200 OK (or 404 NOT FOUND via exception handler)
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteCoupon(@PathVariable("id") Long id) {
        couponService.deleteCoupon(id);
        Map<String, Object> response = Map.of(
                "message", "Coupon deleted successfully with id: " + id,
                "status", HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }
}
