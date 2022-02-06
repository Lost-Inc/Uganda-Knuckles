package at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice;

import at.lost_inc.ugandaknucklesbot.Commands.Core.Audio.TrackScheduler;
import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.Audio.AudioPlayerService;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class VoiceCommandLoop extends BotCommand {
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
    private final AudioPlayerService playerService = ServiceManager.provideUnchecked(AudioPlayerService.class);

    @Override
    protected String @Nullable [] getAliases() {
        return new String[] {
                "lp"
        };
    }

    @Override
    protected String @NotNull [] getCategories() {
        return new String[] {
                ICategories.VOICE,
                ICategories.UTIL,
        };
    }

    @Override
    protected @NotNull String getName() {
        return "loop";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Loops current queue indefinitely\nor disables looping";
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        final TrackScheduler scheduler = playerService.getScheduler(param.message.getGuild()).get();
        scheduler.setLooping(!scheduler.getLooping());
        utilsChat.sendInfo(param.message.getChannel(), scheduler.getLooping() ? "Now looping the queue!" : "Looping disabled!");
    }
}
