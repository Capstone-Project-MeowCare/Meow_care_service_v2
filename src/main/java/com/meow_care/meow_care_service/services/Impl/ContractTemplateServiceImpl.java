package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.contract.ContractTemplateDto;
import com.meow_care.meow_care_service.dto.contract.ContractTemplateRequestDto;
import com.meow_care.meow_care_service.dto.contract.ContractTemplateResponseDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.ContractTemplate;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.ContractTemplateMapper;
import com.meow_care.meow_care_service.repositories.ContractTemplateRepository;
import com.meow_care.meow_care_service.services.ContractTemplateService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ContractTemplateServiceImpl extends BaseServiceImpl<ContractTemplateDto, ContractTemplate, ContractTemplateRepository, ContractTemplateMapper> implements ContractTemplateService {
    public ContractTemplateServiceImpl(ContractTemplateRepository repository, ContractTemplateMapper mapper) {
        super(repository, mapper);
    }



    @Override
    public ApiResponse<ContractTemplateResponseDto> getContractTemplateResponse(UUID id) {
        ContractTemplate contractTemplate = repository.findById(id).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Contract template not found")
        );
        return ApiResponse.success(mapper.toResponseDto(contractTemplate));
    }

    @Override
    public ApiResponse<ContractTemplateDto> findByName(String name) {
        ContractTemplate entity = repository.findByName(name).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Contract template not found")
        );
        return ApiResponse.success(mapper.toDto(entity));
    }

    @Override
    public ApiResponse<ContractTemplateDto> create(ContractTemplateRequestDto dto) {
        ContractTemplate entity = mapper.toEntity(dto);
        entity.setContractFields(extractContractFields(entity.getContent()));
        entity = repository.save(entity);
        return ApiResponse.success(mapper.toDto(entity));
    }

    @Override
    public ApiResponse<ContractTemplateDto> update(UUID id, ContractTemplateRequestDto dto) {
        ContractTemplate contractTemplate = repository.findById(id).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Contract template not found")
        );

        mapper.partialUpdate(dto, contractTemplate);

        if (dto.content() != null) {
            contractTemplate.setContractFields(extractContractFields(dto.content()));
        }

        contractTemplate = repository.save(contractTemplate);

        return ApiResponse.success(mapper.toDto(contractTemplate));
    }

    @PostConstruct
    public void  init() {
        String contractName = "HỢP ĐỒNG DỊCH VỤ GIỮ MÈO";
        ContractTemplate contractTemplate = repository.findByName(contractName).orElse(null);
        if (contractTemplate == null) {
            ContractTemplateRequestDto contractTemplateRequestDto = ContractTemplateRequestDto.builder()
                    .name(contractName)
                    .content("<p><strong>HỢP ĐỒNG DỊCH VỤ GIỮ MÈO</strong></p><p><strong>Hợp đồng số:</strong> [contract_number]<br/><strong>Ngày ký kết:</strong> [signing_date]</p><p><strong>1. Thông tin các bên</strong></p><p><strong>Bên A (Cat Owner):</strong></p><ul><li><strong>Họ và tên:</strong> [owner_full_name]</li><li><strong>Địa chỉ:</strong> [owner_address]</li><li><strong>Điện thoại:</strong> [owner_phone_number]</li><li><strong>Email:</strong> [owner_email]</li></ul><p><strong>Bên B (Cat Sitter):</strong></p><ul><li><strong>Họ và tên:</strong> [sitter_full_name]</li><li><strong>Địa chỉ:</strong> [sitter_address]</li><li><strong>Điện thoại:</strong> [sitter_phone_number]</li><li><strong>Email:</strong> [sitter_email]</li></ul>")
                    .build();
            create(contractTemplateRequestDto);
        }
    }

    /**
     * Extracts all placeholders (e.g., [field_name]) from the template content.
     *
     * @param templateContent The HTML content of the template.
     * @return A list of field names found in the template.
     */
    private static List<String> extractContractFields(String templateContent) {
        List<String> contractFields = new ArrayList<>();

        // Regular expression to find placeholders in the format [field_name]
        String regex = "\\[(\\w+)]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(templateContent);

        // Extract all matches and add them to the list
        while (matcher.find()) {
            contractFields.add(matcher.group(1)); // Extract the field name without brackets
        }

        return contractFields;
    }
}
