package com.erp.backend.dtos.mappers;

import com.erp.backend.dtos.UserDto;
import com.erp.backend.entities.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserDto> {
    @Override
    public UserDto apply(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail());
    }
}
