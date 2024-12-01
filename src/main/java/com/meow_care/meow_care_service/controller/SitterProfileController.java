package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.SitterProfileDto;
import com.meow_care.meow_care_service.dto.SitterProfileWithUserDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.enums.SitterProfileStatus;
import com.meow_care.meow_care_service.services.SitterProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sitter-profiles")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class SitterProfileController {

    private final SitterProfileService sitterProfileService;

    @GetMapping("/{id}")
    public ApiResponse<SitterProfileWithUserDto> getSitterProfileById(@PathVariable UUID id) {
        return sitterProfileService.getProfileWithUser(id);
    }

    @GetMapping("/sitter/{id}")
    public ApiResponse<SitterProfileDto> getSitterProfileBySitterId(@PathVariable UUID id) {
        return sitterProfileService.getBySitterId(id);
    }

    @PostMapping
    public ApiResponse<SitterProfileDto> createSitterProfile(@Valid @RequestBody SitterProfileDto sitterProfileDto) {
        return sitterProfileService.create(sitterProfileDto);
    }

    @GetMapping
    public ApiResponse<List<SitterProfileDto>> getAllSitterProfiles() {
        return sitterProfileService.getAll();
    }

    //    public ApiResponse<Page<SitterProfileDto>> findAllOrderByDistance(double latitude, double longitude, Pageable pageable) {
    @GetMapping("/distance")
    public ApiResponse<Page<SitterProfileDto>> findAllOrderByDistance(@RequestParam double latitude,
                                                                      @RequestParam double longitude,
                                                                      @RequestParam int page,
                                                                      @RequestParam int size,
                                                                      @RequestParam(defaultValue = "distance") String sort,
                                                                      @RequestParam(defaultValue = "DESC") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sort));

        if (sort.equals("distance")) {
            pageable = PageRequest.of(page - 1, size);
            return sitterProfileService.findAllOrderByDistance(latitude, longitude, pageable);
        }
        else  {
            return sitterProfileService.findAllWithDistance(latitude, longitude, pageable);
        }
    }

    // get all by status
    @GetMapping("/status/{status}")
    public ApiResponse<List<SitterProfileDto>> getAllByStatus(@PathVariable SitterProfileStatus status) {
        return sitterProfileService.getAllByStatus(status);
    }

    @PutMapping("/{id}")
    public ApiResponse<SitterProfileDto> updateSitterProfile(@PathVariable UUID id, @RequestBody SitterProfileDto sitterProfileDto) {
        return sitterProfileService.update(id, sitterProfileDto);
    }

    //update status by id
    @PutMapping("/status/{id}")
    public ApiResponse<Void> updateStatusById(@PathVariable UUID id, @RequestParam SitterProfileStatus status) {
        return sitterProfileService.updateStatusById(status, id);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteSitterProfile(@PathVariable UUID id) {
        return sitterProfileService.delete(id);
    }
}
