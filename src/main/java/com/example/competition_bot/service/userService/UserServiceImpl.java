package com.example.competition_bot.service.userService;

import com.example.competition_bot.entity.UserEntity;
import com.example.competition_bot.entity.UserState;
import com.example.competition_bot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
   private final UserRepository userRepository;

   @Override
    public UserEntity getByChatId(Long chatId) {
        return userRepository.findById(chatId).orElse(null);
    }

    @Override
    public void updateState(Long chatId, UserState userState) {
        int i = userRepository.updateState(chatId, userState);
    }

    @Override
    public UserEntity save(UserEntity currentUser) {
       return userRepository.save(currentUser);
    }

    @Override
    public List<UserEntity> getALL() {
        return userRepository.findAll();
    }


}
