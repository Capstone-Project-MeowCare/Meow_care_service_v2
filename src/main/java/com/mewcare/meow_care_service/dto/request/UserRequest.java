package com.mewcare.meow_care_service.dto.request;

import com.mewcare.meow_care_service.dto.RoleDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String Username;

    private UUID id;
    @NotNull
    @Size(max = 50)
    private String username;
    @NotNull
    private String password;
    @Size(max = 100)
    private String email;
    @Size(max = 255)
    private String fullName;
    @Size(max = 255)
    private String avatar;
    @Size(max = 15)
    private String phoneNumber;
    private Instant dob;
    @Size(max = 20)
    private String gender;
    @Size(max = 255)
    private String address;
    private Instant registrationDate;
    private Integer status;
    private Set<RoleDto> roles = new LinkedHashSet<>();
}