package at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Commands.DI.Inject;
import at.lost_inc.ugandaknucklesbot.Commands.Core.Audio.TrackScheduler;
import at.lost_inc.ugandaknucklesbot.Service.Audio.AudioPlayerService;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import org.jetbrains.annotations.NotNull;

@Command(
        name = "loop",
        help = "Loops current queue indefinitely\nor disables looping",
        categories = {
                BotCommand.ICategories.VOICE,
                BotCommand.ICategories.UTIL,
        },
        aliases = {
                "lp"
        }
)
public final class VoiceCommandLoop extends BotCommand {
    @Inject
    private UtilsChat utilsChat;
    @Inject
    private AudioPlayerService playerService;

    @Override
    public void execute(@NotNull CommandParameter param) {
        final TrackScheduler scheduler = playerService.getScheduler(param.message.getGuild()).get();
        scheduler.setLooping(!scheduler.getLooping());
        utilsChat.sendInfo(param.message.getChannel(), scheduler.getLooping() ? "Now looping the queue!" : "Looping disabled!");
    }
}
