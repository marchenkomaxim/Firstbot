package com.tgbotrix.Firstbot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
@Data //автоматически создает конструксторы для класса
public class BotConfig {
    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String token;


}
