package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.ServiceDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.ConfigService;
import com.meow_care.meow_care_service.entities.Service;
import com.meow_care.meow_care_service.entities.User;
import com.meow_care.meow_care_service.mapper.ServiceMapper;
import com.meow_care.meow_care_service.repositories.ServiceRepository;
import com.meow_care.meow_care_service.services.ServiceEntityService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import com.meow_care.meow_care_service.util.UserUtils;

import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service
public class ServiceEntityServiceImpl extends BaseServiceImpl<ServiceDto, Service, ServiceRepository, ServiceMapper>
        implements ServiceEntityService {
    public ServiceEntityServiceImpl(ServiceRepository repository, ServiceMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public ApiResponse<ServiceDto> create(ServiceDto dto) {
        Service service = mapper.toEntity(dto);
        service.setSitter(User.builder().id(UserUtils.getCurrentUserId()).build());
        dto = mapper.toDto(repository.save(service));
        return ApiResponse.success(dto);
    }

    @Override
    public ApiResponse<List<ServiceDto>> getByServiceTypeId(UUID id) {
        return ApiResponse.success(mapper.toDtoList(repository.findByConfigServiceServiceTypeId(id)));
    }

    @Override
    public ApiResponse<List<ServiceDto>> getBySitterId(UUID id) {
        List<Service> services = repository.findBySitterId(id);
        return ApiResponse.success(mapper.toDtoList(services));
    }

    @Override
    public ApiResponse<List<ServiceDto>> getBySitterId(UUID typeId, UUID id) {
        List<Service> services = repository.findBySitter_IdAndConfigService_ServiceType_Id(id, typeId);
        return ApiResponse.success(mapper.toDtoList(services));
    }

    @Override
    public void insertSampleData(UUID sitterId) {
        List<Service> services = List.of(
                Service.builder().duration(60).price(55000).startTime(11).status(0).configService(ConfigService.builder().id(UUID.fromString("97ad6b5d-d285-4440-86f1-e3cfd4a0c786")).build()).id(UUID.fromString("08ddd53b-56a6-47ea-a097-05d667838e6f")).sitter(User.builder().id(sitterId).build()).build(),
                Service.builder().duration(120).price(60000).startTime(7).status(0).configService(ConfigService.builder().id(UUID.fromString("b82e61ad-f0f5-466d-bd99-5dd80abe532d")).build()).id(UUID.fromString("fde28c37-bb92-4cd2-beac-3c550beab37b")).sitter(User.builder().id(sitterId).build()).build(),
                Service.builder().duration(120).price(45000).startTime(12).status(0).configService(ConfigService.builder().id(UUID.fromString("50e5626e-bbf8-4ae7-9279-770dbd1aa86e")).build()).id(UUID.fromString("98fe8b8a-1c5e-4ae1-b296-5cd1c1f8e565")).sitter(User.builder().id(sitterId).build()).build(),
                Service.builder().duration(120).price(40000).startTime(9).status(0).configService(ConfigService.builder().id(UUID.fromString("0291282b-89b8-44b0-bb43-c54d7dfdce49")).build()).id(UUID.fromString("085be7d5-51d2-475d-b2d3-e6127299c05a")).sitter(User.builder().id(sitterId).build()).build(),
                Service.builder().duration(60).price(50000).startTime(6).status(0).configService(ConfigService.builder().id(UUID.fromString("a8490033-0501-4ad6-acf7-8fc7ad8e2098")).build()).id(UUID.fromString("d38be8d8-acc1-45c1-918e-60899e52745d")).sitter(User.builder().id(sitterId).build()).build()
        );
        repository.saveAll(services);
    }
}
