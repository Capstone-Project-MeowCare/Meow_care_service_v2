package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.MedicalConditionDto;
import com.meow_care.meow_care_service.entities.MedicalCondition;
import com.meow_care.meow_care_service.mapper.MedicalConditionMapper;
import com.meow_care.meow_care_service.repositories.MedicalConditionRepository;
import com.meow_care.meow_care_service.services.MedicalConditionService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class MedicalConditionServiceImpl extends BaseServiceImpl<MedicalConditionDto, MedicalCondition, MedicalConditionRepository, MedicalConditionMapper>
        implements MedicalConditionService {
    public MedicalConditionServiceImpl(MedicalConditionRepository repository, MedicalConditionMapper mapper) {
        super(repository, mapper);
    }
}
