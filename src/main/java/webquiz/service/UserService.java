package webquiz.service;

import webquiz.dto.NewUserDto;

public interface UserService {
    void registerUser(NewUserDto newUserDto);
}
