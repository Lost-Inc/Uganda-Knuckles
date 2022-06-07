package at.lost_inc.ugandaknucklesbot.Service.Audio;

import at.lost_inc.ugandaknucklesbot.Util.Author;
import com.github.topislavalinkplugins.topissourcemanagers.applemusic.AppleMusicSourceManager;
import com.github.topislavalinkplugins.topissourcemanagers.spotify.SpotifyConfig;
import com.github.topislavalinkplugins.topissourcemanagers.spotify.SpotifySourceManager;
import com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import org.jetbrains.annotations.NotNull;

@Author("sudo200")
public final class SimpleAudioPlayerManagerService implements AudioPlayerManagerService {
    private static final AudioPlayerManager manager = new DefaultAudioPlayerManager();

    static {
        AudioSourceManagers.registerRemoteSources(manager);
        manager.enableGcMonitoring();
        manager.getConfiguration().setOutputFormat(StandardAudioDataFormats.DISCORD_OPUS);
        manager.registerSourceManager(new AppleMusicSourceManager(null, "AT", manager));

        final SpotifyConfig spotifyConfig = new SpotifyConfig();
        spotifyConfig.setClientId(System.getenv("SPOTIFYCLIENTID"));
        spotifyConfig.setClientSecret(System.getenv("SPOTIFYCLIENTSECRET"));
        spotifyConfig.setCountryCode("AT");
        manager.registerSourceManager(new SpotifySourceManager(null, spotifyConfig, manager));
    }

    @Override
    public @NotNull AudioPlayerManager get() {
        return manager;
    }
}
