package at.lost_inc.ugandaknucklesbot.Commands.Core.Audio.Handler;

import at.lost_inc.ugandaknucklesbot.Service.Audio.AudioPlayerManagerService;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.Author;
import com.sedmelluq.discord.lavaplayer.format.AudioDataFormat;
import com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

@Author("sudo200")
public final class VoiceAudioSendHandler implements AudioSendHandler {
    private final AudioDataFormat format = ServiceManager.provideUnchecked(AudioPlayerManagerService.class).get().getConfiguration().getOutputFormat();
    private final AudioPlayer player;
    private AudioFrame lastFrame;

    public VoiceAudioSendHandler(AudioPlayer player) {
        this.player = player;
    }

    @Override
    public boolean canProvide() {
        lastFrame = player.provide();
        return lastFrame != null;
    }

    @Contract(" -> new")
    @Override
    public @NotNull ByteBuffer provide20MsAudio() {
        return ByteBuffer.wrap(lastFrame.getData());
    }

    @Override
    public boolean isOpus() {
        return format.equals(StandardAudioDataFormats.DISCORD_OPUS);
    }
}
