package at.lost_inc.ugandaknucklesbot.Commands.Core.Audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public final class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private final PlayQueue queue = new PlayQueue(new ArrayList<>(), 0);

    public TrackScheduler(@NotNull AudioPlayer player) {
        this.player = player;
    }

    public void queue(AudioTrack @NotNull ...tracks) {
        queue.audioTracks.addAll(Arrays.asList(tracks));
    }

    public void start() {
        final AudioTrack track = queue.poll();
        if(track != null)
            player.playTrack(track);
    }

    public AudioTrack remove(int i) {
        return queue.audioTracks.remove(i);
    }

    @Contract(value = " -> new", pure = true)
    public @NotNull PlayQueue getQueue() {
        return new PlayQueue(queue);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, @NotNull AudioTrackEndReason endReason) {
        if(endReason.mayStartNext)
            start();
    }

    @Override
    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
        start();
    }

    public static final class PlayQueue {
        public final List<AudioTrack> audioTracks;
        private int trackNum;

        @Contract(pure = true)
        public PlayQueue(@NotNull PlayQueue queue) {
            audioTracks = new ArrayList<>(queue.audioTracks);
            trackNum = queue.trackNum;
        }

        public PlayQueue(List<AudioTrack> tracks, int trackNum) {
            audioTracks = tracks;
            this.trackNum = trackNum;
        }

        public int getTrackNum() {
            return trackNum;
        }

        public @Nullable AudioTrack peek() {
            return trackNum < audioTracks.size() ? audioTracks.get(trackNum) : null;
        }

        public @Nullable AudioTrack poll() {
            final AudioTrack track = peek();
            if(track == null)
                return null;
            trackNum++;
            return track;
        }
    }
}
