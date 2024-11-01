package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.MenuDto;
import com.mewcare.meow_care_service.entities.Menu;
import com.mewcare.meow_care_service.mapper.MenuMapper;
import com.mewcare.meow_care_service.repositories.MenuRepository;
import com.mewcare.meow_care_service.services.MenuService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;

public class MenuServiceImpl extends BaseServiceImpl<MenuDto, Menu, MenuRepository, MenuMapper>
        implements MenuService {
    public MenuServiceImpl(MenuRepository repository, MenuMapper mapper) {
        super(repository, mapper);
    }
}
