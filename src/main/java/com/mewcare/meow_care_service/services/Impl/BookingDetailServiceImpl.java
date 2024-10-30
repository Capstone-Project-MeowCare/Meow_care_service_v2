package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.BookingDetailDto;
import com.mewcare.meow_care_service.entities.BookingDetail;
import com.mewcare.meow_care_service.mapper.BookingDetailMapper;
import com.mewcare.meow_care_service.repositories.BookingDetailRepository;
import com.mewcare.meow_care_service.services.BookingDetailService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BookingDetailServiceImpl extends BaseServiceImpl<BookingDetailDto, BookingDetail, BookingDetailRepository, BookingDetailMapper>
        implements BookingDetailService {
    public BookingDetailServiceImpl(BookingDetailRepository repository, BookingDetailMapper mapper) {
        super(repository, mapper);
    }
}
