package at.lost_inc.ugandaknucklesbot.Commands.Core.Audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

public class VoicePlayerManager {
    private static final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();

    static {
        AudioSourceManagers.registerRemoteSources(playerManager);
    }

    /**
     * Returns the instance of our AudioPlayerManager
     *
     * @return PlayerManager
     * @author sudo200
     * @see AudioPlayerManager
     */
    public static AudioPlayerManager get() {
        return playerManager;
    }
}
