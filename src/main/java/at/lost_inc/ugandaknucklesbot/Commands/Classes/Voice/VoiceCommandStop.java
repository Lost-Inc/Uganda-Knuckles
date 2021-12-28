package at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.Audio.AudioPlayerService;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Service.Temp.NotAvailable;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicReference;

public final class VoiceCommandStop extends BotCommand {
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
    private final AudioPlayerService playerService = ServiceManager.provideUnchecked(AudioPlayerService.class);

    @Override
    protected String @Nullable [] getAliases() {
        return new String[]{
                "s"
        };
    }

    @Override
    protected String @NotNull [] getCategories() {
        return new String[]{
                BotCommand.ICategories.VOICE,
                BotCommand.ICategories.UTIL,
        };
    }

    @Override
    protected @NotNull String getName() {
        return "stop";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Stops playing audio (if playing that is)";
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        final AtomicReference<AudioPlayer> player = playerService.getPlayer(param.message.getGuild());

        player.get().stopTrack();
    }
}
