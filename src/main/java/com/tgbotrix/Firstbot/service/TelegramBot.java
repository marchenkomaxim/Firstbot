package com.tgbotrix.Firstbot.service;

import com.tgbotrix.Firstbot.config.BotConfig;
import com.tgbotrix.Firstbot.model.User;
import com.tgbotrix.Firstbot.model.UserRepository;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.http.util.TimeStamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    public final String HELP_INFO = "This bot is created for develop my spring and SQL abilities \n" +
            "You can use commands from the main menu \n" +
            "Type /mydata to see data stored about yourself \n\n" +
            "Type /help to see this message again \n";

    @Autowired
    private UserRepository userRepository;

    final BotConfig config;

    public TelegramBot(BotConfig config) {
        this.config = config;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "get a welcome message"));
        listOfCommands.add(new BotCommand("/mydata", "shows info about you"));
        listOfCommands.add(new BotCommand("/deletedata", "delete your personal data"));
        listOfCommands.add(new BotCommand("/help", "info how to use this bot"));
        listOfCommands.add(new BotCommand("/settings", "set your preferences"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot`s command list : " + e.getMessage());
        }
    }
    @Override
    public String getBotToken() {
        return config.getToken();
    }


    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start" -> {
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    registerUser(update.getMessage());
                }
                case "/help" -> sendMessage(chatId, HELP_INFO);
                default -> sendMessage(chatId, "Something goes wrong.");
            }
        }

    }

    private void registerUser(Message message) {
        if(userRepository.findById(message.getChatId()).isEmpty()) {
            var chatId = message.getChatId();
            var chat = message.getChat();

            User user = new User();

            user.setChatId(chatId);
            user.setFirstName(chat.getFirstName());
            user.setLastName(chat.getLastName());
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new TimeStamp());

            userRepository.save(user);
            sendMessage(chatId, user.getFirstName());
            log.info("user saved: " + user);
            //getUserById(chatId, user.getId());
        }
    }

//    private Optional<User> getIdByChatId(long chatId) {
//        try {
//            Optional<User> user = userRepository.findById(id);
//            sendMessage(chatId,
//                    user.toString());
//            return user;
//        } catch (Exception e) {
//            sendMessage(chatId, "Some problem, happens");
//        }
//        return null;
//    }

    private void startCommandReceived(long chatId, String name) {

        //String answer = "Hi, " + name + " nice to meet you!";
        String answer = EmojiParser.parseToUnicode("Hi, " + name + " nice to meet you!" + ":wave:");
        sendMessage(chatId, answer);

        log.info("User received his name: " + name);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        try{
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }


}
