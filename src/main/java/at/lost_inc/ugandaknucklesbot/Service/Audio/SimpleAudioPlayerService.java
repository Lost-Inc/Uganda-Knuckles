package at.lost_inc.ugandaknucklesbot.Service.Audio;

import at.lost_inc.ugandaknucklesbot.Commands.Core.Audio.Handler.VoiceAudioSendHandler;
import at.lost_inc.ugandaknucklesbot.Commands.Core.Audio.TrackScheduler;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public final class SimpleAudioPlayerService implements AudioPlayerService {
    private final AudioPlayerManager playerManager = ServiceManager.provideUnchecked(AudioPlayerManagerService.class).get();
    private final Map<Guild, Map.Entry<AtomicReference<AudioPlayer>, Map.Entry<AtomicReference<TrackScheduler>, AtomicReference<AudioSendHandler>>>> map = new HashMap<>();

    /**
     * Get's the {@link AudioPlayer} for the specific {@link Guild};
     * if there isn't one, it creates one
     *
     * @param guild the Guild
     * @return Audioplayer
     */
    @Override
    public @NotNull AtomicReference<AudioPlayer> getPlayer(@NotNull Guild guild) {
        if(map.containsKey(guild))
            return map.get(guild).getKey();
        final Map.@NotNull Entry<AtomicReference<AudioPlayer>, Map.Entry<AtomicReference<TrackScheduler>, AtomicReference<AudioSendHandler>>> newEntry = buildEntry();
        map.put(guild, newEntry);
        return newEntry.getKey();
    }

    /**
     * Get's the {@link TrackScheduler} for the specific {@link Guild};
     * if there isn't one, it returns {@link Optional#empty()}
     *
     * @param guild the Guild
     * @return TrackScheduler
     */
    @Override
    public @NotNull AtomicReference<TrackScheduler> getScheduler(@NotNull Guild guild) {
        if(map.containsKey(guild))
            return map.get(guild).getValue().getKey();
        final Map.@NotNull Entry<AtomicReference<AudioPlayer>, Map.Entry<AtomicReference<TrackScheduler>, AtomicReference<AudioSendHandler>>> newEntry = buildEntry();
        map.put(guild, newEntry);
        return newEntry.getValue().getKey();
    }

    @Override
    public @NotNull Optional<AtomicReference<AudioSendHandler>> getAudioHandler(@NotNull Guild guild) {
        if(!map.containsKey(guild))
            return Optional.empty();
        return Optional.of(map.get(guild).getValue().getValue());
    }

    /**
     * Destroys the {@link AudioPlayer}, {@link TrackScheduler} and {@link AudioSendHandler} for the
     * specific {@link Guild}
     *
     * @param guild the Guild
     * @return {@link Boolean#TRUE} if destroyed
     */
    @Override
    public boolean destroy(@NotNull Guild guild) {
        final boolean removed = map.containsKey(guild);
        final Map.Entry<AtomicReference<AudioPlayer>, Map.Entry<AtomicReference<TrackScheduler>, AtomicReference<AudioSendHandler>>> oldEntry = map.remove(guild);
        if(removed) {
            oldEntry.getKey().get().destroy();
            oldEntry.getValue().getKey().get().destroy();
            guild.getAudioManager().setSendingHandler(null);
        }
        return removed;
    }


    private @NotNull Map.Entry<AtomicReference<AudioPlayer>, Map.Entry<AtomicReference<TrackScheduler>, AtomicReference<AudioSendHandler>>> buildEntry() {
        final AtomicReference<AudioPlayer> newPlayer = new AtomicReference<>(playerManager.createPlayer());
        final AtomicReference<TrackScheduler> newTrackScheduler = new AtomicReference<>(new TrackScheduler(newPlayer.get()));
        final AtomicReference<AudioSendHandler> newSendHandler = new AtomicReference<>(new VoiceAudioSendHandler(newPlayer.get()));
        newPlayer.get().addListener(newTrackScheduler.get());

        newPlayer.get().addListener(new AudioEventAdapter() {
            private final Logger logger = LoggerFactory.getLogger(getClass());

            @Override
            public void onPlayerPause(AudioPlayer player) {
                logger.info("Player paused!");
            }

            @Override
            public void onPlayerResume(AudioPlayer player) {
                logger.info("Player resumed!");
            }

            @Override
            public void onTrackStart(AudioPlayer player, AudioTrack track) {
                logger.info(String.format("Track \"%s\" started!", track.getInfo().title));
            }

            @Override
            public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
                logger.info(String.format("Track \"%s\" ended! Reason: %s", track.getInfo().title, endReason.toString()));
            }

            @Override
            public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
                logger.info(String.format("Exception during track \"%s\":", track.getInfo().title), exception);
            }

            @Override
            public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
                logger.info(String.format("Track \"%s\" stuck!", track.getInfo().title));
            }
        });

        return new SimpleEntry<>(newPlayer, new SimpleEntry<>(newTrackScheduler, newSendHandler));
    }
}
