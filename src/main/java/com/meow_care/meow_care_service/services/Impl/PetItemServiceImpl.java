package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.PetItemDto;
import com.meow_care.meow_care_service.entities.PetItem;
import com.meow_care.meow_care_service.mapper.PetItemMapper;
import com.meow_care.meow_care_service.repositories.PetItemRepository;
import com.meow_care.meow_care_service.services.PetItemService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PetItemServiceImpl extends BaseServiceImpl<PetItemDto, PetItem, PetItemRepository, PetItemMapper>
        implements PetItemService {
    public PetItemServiceImpl(PetItemRepository repository, PetItemMapper mapper) {
        super(repository, mapper);
    }
}
