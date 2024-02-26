package com.example.competition_bot.service.channelService;

import com.example.competition_bot.entity.ChannelEntity;

import java.util.List;

public interface ChannelService {
    boolean isUserFollowingChannel(Long userId);

    List<ChannelEntity> getAll();

}
