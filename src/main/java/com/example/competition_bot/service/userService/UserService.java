package com.example.competition_bot.service.userService;

import com.example.competition_bot.entity.UserEntity;
import com.example.competition_bot.entity.UserState;

import java.util.List;

public interface UserService {
    UserEntity getByChatId(Long chatId);
    void  updateState(Long userId, UserState userState);

    UserEntity save(UserEntity currentUser);

    void updatePhone(Long chatId, String phoneNumber);


    void updateFullName(Long chatId, String fullName);


    List<UserEntity> getALL();
}
