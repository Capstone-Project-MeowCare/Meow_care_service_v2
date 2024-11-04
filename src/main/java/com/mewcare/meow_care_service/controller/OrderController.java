package com.mewcare.meow_care_service.controller;

import com.mewcare.meow_care_service.dto.OrderDto;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.services.OrderService;
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
@RequestMapping("/orders")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public ApiResponse<OrderDto> getOrderById(@PathVariable UUID id) {
        return orderService.get(id);
    }

    @GetMapping
    public ApiResponse<List<OrderDto>> getAllOrders() {
        return orderService.getAll();
    }

    @PostMapping
    public ApiResponse<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        return orderService.create(orderDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<OrderDto> updateOrder(@PathVariable UUID id, @RequestBody OrderDto orderDto) {
        return orderService.update(id, orderDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteOrder(@PathVariable UUID id) {
        return orderService.delete(id);
    }
}
