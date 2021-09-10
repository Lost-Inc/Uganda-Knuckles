package at.sudo200.ugandaknucklesbot.Commands.Core.Audio;

import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.audio.AudioReceiveHandler;
import net.dv8tion.jda.api.audio.CombinedAudio;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class VoiceAudioRecorder implements AudioReceiveHandler {
    private final UtilsChat utilsChat = new UtilsChat();
    private final Message msg;
    private final List<byte[]> temp = new ArrayList<>();
    private boolean canReceive = true;

    public VoiceAudioRecorder(Message msg) {
        this.msg = msg;
    }

    @Override
    public boolean canReceiveCombined() {
        return canReceive;
    }

    @Override
    public void handleCombinedAudio(@NotNull CombinedAudio combinedAudio) {
        canReceive = temp.size() < 1073741824;
        if(!canReceive)
            Disconnect();

        try {
            temp.add(combinedAudio.getAudioData(1.0f));
        }
        catch (OutOfMemoryError e) { Disconnect(); }
    }

    private void Disconnect() {
        utilsChat.sendInfo(msg.getChannel(), "Sry mate, I can't take any more Audio!");
        msg.getGuild().getAudioManager().closeAudioConnection();
    }
}

