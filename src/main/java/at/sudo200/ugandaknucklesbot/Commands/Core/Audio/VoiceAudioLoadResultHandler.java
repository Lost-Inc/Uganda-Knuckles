package at.sudo200.ugandaknucklesbot.Commands.Core.Audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class VoiceAudioLoadResultHandler implements AudioLoadResultHandler {
    private final AudioPlayer player;

    public VoiceAudioLoadResultHandler(AudioPlayer player) {
        this.player = player;
    }

    @Override
    public void trackLoaded(AudioTrack track) {
        player.playTrack(track);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        for (AudioTrack track : playlist.getTracks())
            player.playTrack(track);
    }

    @Override
    public void noMatches() {
    }

    @Override
    public void loadFailed(FriendlyException exception) {
        exception.printStackTrace();
    }
}
