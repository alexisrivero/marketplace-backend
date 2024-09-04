package com.uade.tpo.marketplace.mapper;

import com.uade.tpo.marketplace.dto.UserDTO;
import com.uade.tpo.marketplace.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);

}
