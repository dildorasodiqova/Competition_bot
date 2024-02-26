package com.example.competition_bot.bot;

import com.example.competition_bot.entity.ChannelEntity;
import com.example.competition_bot.service.channelService.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
@Component
public class BotService {
    @Autowired
    private  ChannelService channelService;
    public SendMessage getPhoneNumber(String chatId){
        SendMessage sendMessage = new SendMessage(
                chatId, "Iltimos , telefon raqamingizni yuboring"
        );
        sendMessage.setReplyMarkup(shareContact());
        return sendMessage;
    }

    public ReplyKeyboardMarkup shareContact(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();


        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setRequestContact(true);
        keyboardButton.setText("Telefon raqamni ulashish");

        keyboardRow.add(keyboardButton);

        replyKeyboardMarkup.setKeyboard(List.of(keyboardRow));
        return replyKeyboardMarkup;
    }

    public SendMessage getFullName(String chatId){
        return new SendMessage(
                chatId, "Ism familiyangizni kiriting : \n Masalan : Abdullayev Abduqodir "
        );

    }


    public SendPhoto getPost(Long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("Do'stlarga yuborish -> ");

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setCaption("");
        sendPhoto.setPhoto(new InputFile(""));
        sendPhoto.setChatId(chatId);

        return sendPhoto;
    }


    public SendMessage getFile(Long chatId) {
        SendMessage sendMessage =  new SendMessage(
                chatId.toString(), "Hush kelibsiz admin!"
        );
        sendMessage.setReplyMarkup(getUsers());
    }

    public ReplyKeyboardMarkup getUsers(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();


        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("Foydalanuvchilar ro'yhatini yuklab olish");

        keyboardRow.add(keyboardButton);

        replyKeyboardMarkup.setKeyboard(List.of(keyboardRow));
        return replyKeyboardMarkup;
    }

    public SendMessage joinChannel(String string) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("✅Tanlovda ishtirok etish uchun quyidagi kanallarga a'zo bo'ling. \n Keyin \"A'zo bo'ldim\" tugmasini bosing.");

        sendMessage.setReplyMarkup(getChannel());
        return sendMessage;
    }


    private InlineKeyboardMarkup getChannel() {
        List<ChannelEntity> allChannels = channelService.getAll();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (ChannelEntity channelEntity : allChannels) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();

            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(channelEntity.getName());

            inlineKeyboardButton.setCallbackData("CHANNEL_" + channelEntity.getId());

            rowInline.add(inlineKeyboardButton);
            rowsInline.add(rowInline);
        }

        inlineKeyboardMarkup.setKeyboard(rowsInline);

        return inlineKeyboardMarkup;
    }


}
