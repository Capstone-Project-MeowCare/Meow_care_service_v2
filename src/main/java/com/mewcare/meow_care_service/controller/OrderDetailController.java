package com.mewcare.meow_care_service.controller;

import com.mewcare.meow_care_service.dto.OrderDetailDto;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.services.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order-details")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @GetMapping("/{id}")
    public ApiResponse<OrderDetailDto> getOrderDetailById(@PathVariable UUID id) {
        return orderDetailService.get(id);
    }

    @GetMapping
    public ApiResponse<List<OrderDetailDto>> getAllOrderDetails() {
        return orderDetailService.getAll();
    }

    @PostMapping
    public ApiResponse<OrderDetailDto> createOrderDetail(@RequestBody OrderDetailDto orderDetailDto) {
        return orderDetailService.create(orderDetailDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<OrderDetailDto> updateOrderDetail(@PathVariable UUID id, @RequestBody OrderDetailDto orderDetailDto) {
        return orderDetailService.update(id, orderDetailDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteOrderDetail(@PathVariable UUID id) {
        return orderDetailService.delete(id);
    }

}
