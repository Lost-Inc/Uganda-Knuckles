package at.sudo200.ugandaknucklesbot.Commands.Core.Audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class VoiceAudioTrackScheduler extends AudioEventAdapter {
    private final Queue<AudioTrack> tracks = new ConcurrentLinkedQueue<>();
    private final AudioPlayer player;

    public VoiceAudioTrackScheduler(AudioPlayer player) {
        this.player = player;
    }

    /**
     * Queues songs
     * @param tracks Track(s) to be queued
     */
    public void queue(AudioTrack @NotNull ... tracks) {
        this.tracks.addAll(Arrays.asList(tracks));
        if (player.startTrack(this.tracks.peek(), true))
            this.tracks.poll();
    }

    /**
     * Getter for the queue
     * @return Copy of the queue
     */
    public Queue<AudioTrack> getQueue() {
        return new ConcurrentLinkedQueue<>(tracks);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, @NotNull AudioTrackEndReason endReason) {
        if (endReason.mayStartNext && !tracks.isEmpty())
            player.playTrack(tracks.poll());
    }

    @Override
    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
        if (!tracks.isEmpty())
            player.playTrack(tracks.poll());
    }
}
