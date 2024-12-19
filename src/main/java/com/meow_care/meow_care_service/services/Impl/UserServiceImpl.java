package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.dto.user.ChangePasswordRequest;
import com.meow_care.meow_care_service.dto.user.UserDto;
import com.meow_care.meow_care_service.dto.user.UserWithRoleDto;
import com.meow_care.meow_care_service.dto.user.UserWithSitterProfileDto;
import com.meow_care.meow_care_service.entities.Role;
import com.meow_care.meow_care_service.entities.User;
import com.meow_care.meow_care_service.entities.Wallet;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.enums.RoleName;
import com.meow_care.meow_care_service.enums.UserStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.UserMapper;
import com.meow_care.meow_care_service.repositories.UserRepository;
import com.meow_care.meow_care_service.repositories.WalletRepository;
import com.meow_care.meow_care_service.services.RoleService;
import com.meow_care.meow_care_service.services.UserService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import com.meow_care.meow_care_service.util.UserUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserDto, User, UserRepository, UserMapper> implements UserService {

    private final RoleService roleService;

    private final WalletRepository walletRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, UserMapper mapper, RoleService roleService, WalletRepository walletRepository, PasswordEncoder passwordEncoder) {
        super(repository, mapper);
        this.roleService = roleService;
        this.walletRepository = walletRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //get user with roles
    @Override
    public ApiResponse<UserWithRoleDto> getUserWithRoles(UUID id) {
        User user = repository.findById(id).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        return ApiResponse.success(mapper.toDtoWithRole(user));
    }

    @Override
    public ApiResponse<UserDto> create(UserDto dto) {
        User user = mapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(roleService
                .findEntityByName(RoleName.USER)
                .orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND))));
        user.setStatus(UserStatus.ACTIVE);
        user = repository.save(user);
        walletRepository.save(Wallet.builder().user(user).build());
        return ApiResponse.created(mapper.toDto(user));
    }

    @Override
    public void addRoleToUser(UUID userId, UUID roleId) {
        User user = repository.findById(userId).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        Role role = roleService.findEntityById(roleId);
        user.add(role);
        repository.save(user);
    }

    @Override
    public ApiResponse<Void> addRoleToUser(UUID userId, RoleName roleName) {
        User user = repository.findById(userId).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        Role role = roleService.findEntityByName(roleName).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        user.add(role);
        repository.save(user);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<Long> countAllUsers() {
        return ApiResponse.success(repository.count());
    }

    @Override
    public ApiResponse<Long> countAllUsersByRole(RoleName role) {
        return ApiResponse.success(repository.countByRolesRoleName(role));
    }

    @Override
    public ApiResponse<List<UserWithSitterProfileDto>> getAllUsersByRole(RoleName role) {
        List<User> users = repository.findByRoles_RoleName(role);
        return ApiResponse.success(mapper.toDtoWithSitterProfile(users));
    }

    //update status user
    @Override
    public ApiResponse<Void> updateStatus(UUID id, Integer status) {
        int result = repository.updateStatusById(status, id);
        if (result == 0) {
            if (repository.findById(id).isEmpty()) {
                throw new ApiException(ApiStatus.NOT_FOUND, "User not found");
            }
            throw new ApiException(ApiStatus.UPDATE_ERROR, "Update status failed");
        }
        return ApiResponse.updated();
    }

    @Override
    public ApiResponse<Void> changePassword(ChangePasswordRequest request) {
        User user = repository.findById(UserUtils.getCurrentUserId()).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));

        // Step 2: Verify the current password
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Current password is incorrect");
        }

        // Step 3: Validate the new password (e.g., length, complexity)

        // Step 4: Encrypt the new password
        String encryptedPassword = passwordEncoder.encode(request.getNewPassword());

        // Step 5: Update the user's password
        user.setPassword(encryptedPassword);
        repository.save(user);

        // Step 6: Return a success response
        return ApiResponse.updated();
    }

    @SuppressWarnings("unused")
    private void validatePassword(String password) {
        if (password.length() < 8) {
            throw new ApiException(ApiStatus.VALIDATION_ERROR, "Password must be at least 8 characters long");
        }
//        if (!password.matches(".*[A-Z].*")) {
//            throw new ValidationException("Password must contain at least one uppercase letter");
//        }
//        if (!password.matches(".*[a-z].*")) {
//            throw new ValidationException("Password must contain at least one lowercase letter");
//        }
//        if (!password.matches(".*\\d.*")) {
//            throw new ValidationException("Password must contain at least one number");
//        }
//        if (!password.matches(".*[!@#$%^&*()].*")) {
//            throw new ValidationException("Password must contain at least one special character (e.g. !@#$%^&*)");
//        }

        if (!password.matches(".*[A-Z].*")) {
            throw new ApiException(ApiStatus.VALIDATION_ERROR, "Password must contain at least one uppercase letter");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new ApiException(ApiStatus.VALIDATION_ERROR, "Password must contain at least one lowercase letter");
        }
        if (!password.matches(".*\\d.*")) {
            throw new ApiException(ApiStatus.VALIDATION_ERROR, "Password must contain at least one number");
        }
        if (!password.matches(".*[!@#$%^&*()].*")) {
            throw new ApiException(ApiStatus.VALIDATION_ERROR, "Password must contain at least one special character (e.g. !@#$%^&*)");
        }
    }
}
