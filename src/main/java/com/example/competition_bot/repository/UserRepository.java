package com.example.competition_bot.repository;

import com.example.competition_bot.entity.UserEntity;
import com.example.competition_bot.entity.UserState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
    @Modifying
    @Transactional
    @Query(value = "update users u set u.userState  = ?2 where u.chatId = ?1")
    int updateState(Long chatId, UserState userState);


    @Modifying
    @Transactional
    @Query(value = "update users u set u.phoneNumber = ?2 where u.chatId = ?1")
    void updatePhone(Long chatId, String phoneNumber);

    @Modifying
    @Transactional
    @Query(value = "update users u set u.NameSurname = ?2 where u.chatId = ?1")
    void updateFullName(Long chatId, String fullName);
}
