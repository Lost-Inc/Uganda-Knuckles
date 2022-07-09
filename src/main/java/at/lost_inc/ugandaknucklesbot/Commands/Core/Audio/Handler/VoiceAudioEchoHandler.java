package at.lost_inc.ugandaknucklesbot.Commands.Core.Audio.Handler;

import net.dv8tion.jda.api.audio.AudioReceiveHandler;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.audio.CombinedAudio;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Class for transmitting sound from one vc to another
 *
 * @author sudo200
 */
public final class VoiceAudioEchoHandler implements AudioSendHandler, AudioReceiveHandler {
    private final Queue<byte[]> queue = new ConcurrentLinkedQueue<>();

    @Override
    public boolean canProvide() {
        return !queue.isEmpty();
    }

    @Override
    public ByteBuffer provide20MsAudio() {
        byte[] data = queue.poll();
        return data == null ? null : ByteBuffer.wrap(data);
    }

    @Override
    public boolean canReceiveCombined() {
        return queue.size() < 10;
    }

    @Override
    public void handleCombinedAudio(@NotNull CombinedAudio combinedAudio) {
        if (combinedAudio.getUsers().isEmpty())
            return;

        byte[] data = combinedAudio.getAudioData(1.0);
        queue.add(data);
    }

    @Override
    public boolean isOpus() {
        return false;
    }
}
