package at.lost_inc.ugandaknucklesbot.Service.Audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import org.jetbrains.annotations.NotNull;

public final class SimpleAudioPlayerManagerService implements AudioPlayerManagerService {
    private static final AudioPlayerManager manager = new DefaultAudioPlayerManager();

    static {
        AudioSourceManagers.registerRemoteSources(manager);
        manager.setTrackStuckThreshold(5000);
        manager.setPlayerCleanupThreshold(60000);
        manager.enableGcMonitoring();
    }

    @Override
    public @NotNull AudioPlayerManager get() {
        return manager;
    }
}
