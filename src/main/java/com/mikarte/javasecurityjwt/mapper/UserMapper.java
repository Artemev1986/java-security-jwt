package com.mikarte.javasecurityjwt.mapper;

import com.mikarte.javasecurityjwt.dto.UserDto;
import com.mikarte.javasecurityjwt.model.User;

/**
 * User Mapper {@link UserMapper}
 */
public class UserMapper {

    /**
     * Mapper User to UserDto
     * @param user {@link User}
     * @return {@link UserDto}
     */
    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        return userDto;
    }
}
