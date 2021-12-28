package at.lost_inc.ugandaknucklesbot.Service.Audio;

import at.lost_inc.ugandaknucklesbot.Commands.Core.Audio.Handler.VoiceAudioSendHandler;
import at.lost_inc.ugandaknucklesbot.Commands.Core.Audio.TrackScheduler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public interface AudioPlayerService {
    /**
     * Get's the {@link AudioPlayer} for the specific {@link Guild};
     * if there isn't one, it creates one
     * @param guild the Guild
     * @return Audioplayer
     */
    @NotNull AtomicReference<AudioPlayer> getPlayer(@NotNull Guild guild);

    /**
     * Get's the {@link TrackScheduler} for the specific {@link Guild};
     * if there isn't one, it creates one
     * @param guild the Guild
     * @return TrackScheduler
     */
    @NotNull AtomicReference<TrackScheduler> getScheduler(@NotNull Guild guild);

    @NotNull Optional<AtomicReference<AudioSendHandler>> getAudioHandler(@NotNull Guild guild);
}
