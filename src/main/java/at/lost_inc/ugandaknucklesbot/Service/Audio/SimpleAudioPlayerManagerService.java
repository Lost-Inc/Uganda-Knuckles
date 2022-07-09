package at.lost_inc.ugandaknucklesbot.Service.Audio;

import com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import org.jetbrains.annotations.NotNull;

public final class SimpleAudioPlayerManagerService implements AudioPlayerManagerService {
    private static final AudioPlayerManager manager = new DefaultAudioPlayerManager();

    static {
        AudioSourceManagers.registerRemoteSources(manager);
        manager.enableGcMonitoring();
        manager.getConfiguration().setOutputFormat(StandardAudioDataFormats.DISCORD_OPUS);
    }

    @Override
    public @NotNull AudioPlayerManager get() {
        return manager;
    }
}
