package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.PetItemDto;
import com.mewcare.meow_care_service.entities.PetItem;
import com.mewcare.meow_care_service.mapper.PetItemMapper;
import com.mewcare.meow_care_service.repositories.PetItemRepository;
import com.mewcare.meow_care_service.services.PetItemService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;

public class PetItemServiceImpl extends BaseServiceImpl<PetItemDto, PetItem, PetItemRepository, PetItemMapper>
        implements PetItemService {
    public PetItemServiceImpl(PetItemRepository repository, PetItemMapper mapper) {
        super(repository, mapper);
    }
}
