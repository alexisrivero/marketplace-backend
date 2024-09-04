package com.uade.tpo.marketplace.mapper;

import com.uade.tpo.marketplace.dto.CreatePaymentMethodDTO;
import com.uade.tpo.marketplace.dto.PaymentMethodDTO;
import com.uade.tpo.marketplace.entity.PaymentMethod;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface PaymentMethodMapper {
    PaymentMethodMapper INSTANCE = Mappers.getMapper(PaymentMethodMapper.class);

    List<PaymentMethodDTO> paymentMethodToPaymentMethodDTO(List<PaymentMethod> paymentMethod);

    PaymentMethod createPaymentMethodDTOToPaymentMethod(CreatePaymentMethodDTO createPaymentMethodDTO);
}
