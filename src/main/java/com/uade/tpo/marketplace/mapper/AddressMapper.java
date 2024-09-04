package com.uade.tpo.marketplace.mapper;

import com.uade.tpo.marketplace.dto.CreateAddressDTO;
import com.uade.tpo.marketplace.dto.UserAddressDTO;
import com.uade.tpo.marketplace.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    List<UserAddressDTO> addressToCheckoutUserAddressDTO(List<Address> address);

    Address createAddressDTOToAddress(CreateAddressDTO createAddressDTO);
}
