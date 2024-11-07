package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.MenuDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.MenuService;
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
@RequestMapping("/menus")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/{id}")
    public ApiResponse<MenuDto> getMenuById(@PathVariable UUID id) {
        return menuService.get(id);
    }

    @GetMapping
    public ApiResponse<List<MenuDto>> getAllMenus() {
        return menuService.getAll();
    }

    @PostMapping
    public ApiResponse<MenuDto> createMenu(@RequestBody MenuDto menuDto) {
        return menuService.create(menuDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<MenuDto> updateMenu(@PathVariable UUID id, @RequestBody MenuDto menuDto) {
        return menuService.update(id, menuDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteMenu(@PathVariable UUID id) {
        return menuService.delete(id);
    }
}
