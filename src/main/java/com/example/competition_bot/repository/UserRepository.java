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
    @Query(value = "update users set userState  = ?2 where chatId = ?1")
    int updateState(Long chatId, UserState userState);

}
