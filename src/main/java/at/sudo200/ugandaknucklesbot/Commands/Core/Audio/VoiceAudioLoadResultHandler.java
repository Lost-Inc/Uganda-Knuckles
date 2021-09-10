package at.sudo200.ugandaknucklesbot.Commands.Core.Audio;

import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class VoiceAudioLoadResultHandler implements AudioLoadResultHandler {
    private final UtilsChat utilsChat = new UtilsChat();

    private final AudioPlayer player;
    private final Message msg;

    public VoiceAudioLoadResultHandler(AudioPlayer player, Message msg) {
        this.player = player;
        this.msg = msg;
    }

    @Override
    public void trackLoaded(AudioTrack track) {
        player.playTrack(track);
        utilsChat.sendInfo(
                msg.getChannel(),
                String.format("Playing \"%s\" by \"%s\" [%s]", track.getInfo().title, track.getInfo().author, msg.getAuthor().getAsMention())
        );
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {

    }

    @Override
    public void noMatches() {
    }

    @Override
    public void loadFailed(@NotNull FriendlyException exception) {
        utilsChat.sendInfo(msg.getChannel(), exception.getMessage());
    }
}
