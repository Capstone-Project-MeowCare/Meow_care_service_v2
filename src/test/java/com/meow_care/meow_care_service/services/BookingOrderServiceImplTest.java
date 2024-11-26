package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.entities.AppSaveConfig;
import com.meow_care.meow_care_service.entities.BookingOrder;
import com.meow_care.meow_care_service.entities.User;
import com.meow_care.meow_care_service.enums.BookingOrderStatus;
import com.meow_care.meow_care_service.enums.ConfigKey;
import com.meow_care.meow_care_service.repositories.BookingOrderRepository;
import com.meow_care.meow_care_service.services.Impl.BookingOrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookingOrderServiceImplTest {
    @Mock
    private BookingOrderRepository bookingOrderRepository;

    @Mock
    private TransactionService transactionService;

    @Mock
    private AppSaveConfigService appSaveConfigService;

    @InjectMocks
    private BookingOrderServiceImpl bookingOrderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateStatusCompleted() {
        UUID bookingOrderId = UUID.randomUUID();
        BookingOrder bookingOrder = new BookingOrder();
        bookingOrder.setId(bookingOrderId);
        bookingOrder.setStartDate(Instant.now());
        bookingOrder.setEndDate(Instant.now().plus(1, ChronoUnit.DAYS));
        bookingOrder.setSitter(new User());
        bookingOrder.getSitter().setId(UUID.randomUUID());

        when(bookingOrderRepository.existsById(bookingOrderId)).thenReturn(true);
        when(bookingOrderRepository.updateStatusById(BookingOrderStatus.COMPLETED, bookingOrderId)).thenReturn(1);
        when(bookingOrderRepository.getReferenceById(bookingOrderId)).thenReturn(bookingOrder);
        when(appSaveConfigService.findByConfigKey(ConfigKey.APP_COMMISSION_SETTING)).thenReturn(AppSaveConfig.builder().configKey(ConfigKey.APP_COMMISSION_SETTING).configValue("0.10").build());

        bookingOrderService.updateStatus(bookingOrderId, BookingOrderStatus.COMPLETED);

        verify(transactionService).completeService(eq(bookingOrderId), any(BigDecimal.class));
        verify(transactionService).createCommissionTransaction(eq(bookingOrder.getSitter().getId()), eq(bookingOrderId), any(BigDecimal.class));
    }

    //app config null
    @Test
    void testUpdateStatusCompletedAppConfigNull() {
        UUID bookingOrderId = UUID.randomUUID();
        BookingOrder bookingOrder = new BookingOrder();
        bookingOrder.setId(bookingOrderId);
        bookingOrder.setStartDate(Instant.now());
        bookingOrder.setEndDate(Instant.now().plus(1, ChronoUnit.DAYS));
        bookingOrder.setSitter(new User());
        bookingOrder.getSitter().setId(UUID.randomUUID());

        when(bookingOrderRepository.existsById(bookingOrderId)).thenReturn(true);
        when(bookingOrderRepository.updateStatusById(BookingOrderStatus.COMPLETED, bookingOrderId)).thenReturn(1);
        when(bookingOrderRepository.getReferenceById(bookingOrderId)).thenReturn(bookingOrder);
        when(appSaveConfigService.findByConfigKey(ConfigKey.APP_COMMISSION_SETTING)).thenReturn(null);

        bookingOrderService.updateStatus(bookingOrderId, BookingOrderStatus.COMPLETED);

        verify(transactionService).completeService(eq(bookingOrderId), any(BigDecimal.class));
        verify(transactionService).createCommissionTransaction(eq(bookingOrder.getSitter().getId()), eq(bookingOrderId), any(BigDecimal.class));
    }
}
