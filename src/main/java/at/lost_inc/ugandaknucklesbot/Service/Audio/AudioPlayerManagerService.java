package at.lost_inc.ugandaknucklesbot.Service.Audio;

import at.lost_inc.ugandaknucklesbot.Util.Author;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

@Author("sudo200")
public interface AudioPlayerManagerService extends Supplier<AudioPlayerManager> {
    @Override
    @NotNull AudioPlayerManager get();
}
