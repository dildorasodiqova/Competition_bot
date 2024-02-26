package com.example.competition_bot.config.bot;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "telegram")
public class BotVariables {
    private String token;
    private String username;
}
