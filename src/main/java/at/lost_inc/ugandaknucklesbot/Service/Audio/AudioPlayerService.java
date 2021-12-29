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
     * Gets the {@link AudioPlayer} for the specific {@link Guild};
     * if there isn't one, it creates one
     * @param guild the Guild
     * @return {@link AudioPlayer}
     */
    @NotNull AtomicReference<AudioPlayer> getPlayer(@NotNull Guild guild);

    /**
     * Gets the {@link TrackScheduler} for the specific {@link Guild};
     * if there isn't one, it creates one
     * @param guild the Guild
     * @return {@link TrackScheduler}
     */
    @NotNull AtomicReference<TrackScheduler> getScheduler(@NotNull Guild guild);

    /**
     * Gets the {@link AudioSendHandler} for the specific {@link Guild};
     * if there isn't one, it returns an empty {@link Optional}
     * @param guild the Guild
     * @return {@link AudioSendHandler}
     */
    @NotNull Optional<AtomicReference<AudioSendHandler>> getAudioHandler(@NotNull Guild guild);

    /**
     * Destroys the {@link AudioPlayer}, {@link TrackScheduler} and {@link AudioSendHandler} for the
     * specific {@link Guild}
     * @param guild the Guild
     * @return {@link Boolean#TRUE} if destroyed
     */
    boolean destroy(@NotNull Guild guild);
}
