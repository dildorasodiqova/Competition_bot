package com.example.competition_bot.service.channelService;

import com.example.competition_bot.entity.ChannelEntity;
import com.example.competition_bot.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberBanned;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberLeft;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberRestricted;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {
    private final ChannelRepository channelRepository;
    public boolean isUserFollowingChannel(Long userId) {
        boolean isReturn = false;
        for (ChannelEntity entity : getAll()) {
            String userName = entity.getUsername();

            GetChatMember getChatMember = new GetChatMember();
            getChatMember.setChatId("@" + userName);
            getChatMember.setUserId(userId);

            try {
                ChatMember chatMember = myTelegramBot.execute(getChatMember);
                isReturn = chatMember instanceof ChatMemberLeft ||
                        chatMember instanceof ChatMemberRestricted ||
                        chatMember instanceof ChatMemberBanned;
            } catch (TelegramApiException e) {
                System.out.println(e.getMessage());
            }
            if (isReturn) {
                return false;
            }
        }
        return true; // Error occurred or user not found in the channel
    }

    @Override
    public List<ChannelEntity> getALl() {
        return channelRepository.findAll();
    }

    private List<ChannelEntity> getAll() {
        return channelRepository.findAll();
    }

    private boolean check(Long userId, String username) {
        GetChatMember getChatMember = new GetChatMember();
        getChatMember.setChatId("@" + username);
        getChatMember.setUserId(userId);

        try {
            ChatMember chatMember = myTelegramBot.execute(getChatMember);
            return !(chatMember instanceof ChatMemberLeft ||
                    chatMember instanceof ChatMemberRestricted ||
                    chatMember instanceof ChatMemberBanned);
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

}
