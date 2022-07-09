package at.lost_inc.ugandaknucklesbot.Service.Audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public interface AudioPlayerManagerService extends Supplier<AudioPlayerManager> {
    @Override
    @NotNull AudioPlayerManager get();
}
