package com.example.competition_bot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity(name = "users")
@Table(name = "users")
public class UserEntity {
    @Id
    private Long chatId;
    private Boolean isAdmin = false;
    private String NameSurname;
    private String phoneNumber;
    private String username;
    private String firstName;
    private UserState userState;

}
