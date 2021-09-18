package at.lost_inc.ugandaknucklesbot.Commands.Core.Audio;


import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

public class VoiceAudioTrackMessenger extends AudioEventAdapter {
    private final UtilsChat utilsChat = new UtilsChat();
    private final @NotNull Message msg;

    public VoiceAudioTrackMessenger(@NotNull Message msg) {
        this.msg = msg;
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        utilsChat.sendInfo(
                msg.getChannel(),
                String.format("Playing \"%s\" by \"%s\" [%s]", track.getInfo().title, track.getInfo().author, msg.getAuthor().getAsMention())
        );
    }

    @Override
    public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
        utilsChat.sendInfo(msg.getChannel(), exception.getMessage());
    }

    @Override
    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
        utilsChat.sendInfo(
                msg.getChannel(),
                String.format("The Track \"%s\" by %s [%s] seems to be stuck!", track.getInfo().title, track.getInfo().author, msg.getAuthor().getAsMention())
        );

    }
}
