package com.example.competition_bot.bot;


import com.example.competition_bot.config.bot.BotVariables;
import com.example.competition_bot.entity.UserEntity;
import com.example.competition_bot.entity.UserState;
import com.example.competition_bot.service.userService.UserService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.List;
import java.util.Objects;


@Component
public class CompetitionBot extends TelegramLongPollingBot {
    private final UserService userService;
    private final BotService botService;


    CompetitionBot(BotVariables botVariables, TelegramBotsApi telegramBotsApi, UserService userService, BotService botService) throws TelegramApiException {
        super(botVariables.getToken());
        this.userService = userService;
        this.botService = botService;
        telegramBotsApi.registerBot(this);
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();

        UserState userState = UserState.START;

        UserEntity user = new UserEntity();

        UserEntity currentUser = userService.getByChatId(chatId);
        if (currentUser == null) {
            currentUser = userService.save(new UserEntity(chatId, false, null, null, message.getFrom().getUserName(), message.getFrom().getFirstName(), UserState.START));
        }

        if (currentUser.getIsAdmin()) {
            execute(botService.getFile(chatId));
            if (update.hasMessage()) {
                if (message.hasText()) {
                    String text = message.getText();
                    if (Objects.equals(text, "Foydalanuvchilar ro'yhatini yuklab olish")) {
                        execute(allUsers(chatId));

                    }else if (Objects.equals(text, "/start")){
                        execute(botService.getFile(chatId));
                    }
                }
            }

        }
        userState = currentUser.getUserState();

        switch (userState) {
            case START -> {
                execute(botService.joinChannel(chatId.toString()));


            }case JOIN_CHANNEL -> {
                execute(botService.getPhoneNumber(chatId.toString()));

                userService.updateState(chatId, UserState.ENTER_PHONE);
            }
            case ENTER_PHONE -> {
                if (message.hasContact()) {
                    user.setPhoneNumber(message.getContact().getPhoneNumber());
                }
                execute(botService.getFullName(chatId.toString()));

                userService.updateState(chatId, UserState.ENTER_FULL_NAME);
            }
            case ENTER_FULL_NAME -> {
                if (message.hasText()) {
                    user.setNameSurname(message.getText());
                    SendPhoto post = botService.getPost(chatId);
                    execute(post);

                }

            }

        }
    }


    public SendDocument allUsers(Long chatId) {
        List<UserEntity> all = userService.getALL();
        ExcelGenerator.generateExcel(all, "foydalanuvchilar.xlsx");
        SendDocument sendDocument = new SendDocument(chatId.toString(), new InputFile(new File("foydalanuvchilar.xlsx")));

        sendDocument.setCaption("Userlar ro'yhati. Soni -> " + all.size());
        return sendDocument;
    }


    @Override
    public String getBotUsername() {
        return "Express_parvoz_konkurs_bot";
    }


}
