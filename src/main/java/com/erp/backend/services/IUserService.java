package com.erp.backend.services;

import com.erp.backend.dtos.UserDto;

import java.util.List;

public interface IUserService {
    List<UserDto> getAll();
    UserDto getUserById(Long id);
}
