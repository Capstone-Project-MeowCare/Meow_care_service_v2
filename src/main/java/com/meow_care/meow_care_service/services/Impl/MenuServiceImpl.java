package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.MenuDto;
import com.meow_care.meow_care_service.entities.Menu;
import com.meow_care.meow_care_service.mapper.MenuMapper;
import com.meow_care.meow_care_service.repositories.MenuRepository;
import com.meow_care.meow_care_service.services.MenuService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuDto, Menu, MenuRepository, MenuMapper>
        implements MenuService {
    public MenuServiceImpl(MenuRepository repository, MenuMapper mapper) {
        super(repository, mapper);
    }
}
