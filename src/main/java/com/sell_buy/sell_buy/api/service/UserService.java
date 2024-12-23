package com.sell_buy.sell_buy.api.service;

public interface UserService {
    void createUser();
    void updateUser();
    void deleteUser();
    void getUserByUserId(Long userId);
    void getAllUsers();
}
