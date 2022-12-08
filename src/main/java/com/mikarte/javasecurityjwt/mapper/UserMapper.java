package com.mikarte.javasecurityjwt.mapper;

import com.mikarte.javasecurityjwt.dto.UserDto;
import com.mikarte.javasecurityjwt.model.User;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        return userDto;
    }
}
