package at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice;

import at.lost_inc.ugandaknucklesbot.Commands.Core.Audio.TrackScheduler;
import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.Command;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.Audio.AudioPlayerService;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;


@Command(
        name = "remove",
        help = "Removes track at specified position",
        categories = {
                BotCommand.ICategories.VOICE,
                BotCommand.ICategories.UTIL
        },
        aliases = {
                "r"
        }
)
public final class VoiceCommandRemove extends BotCommand {
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
    private final AudioPlayerService playerService = ServiceManager.provideUnchecked(AudioPlayerService.class);

    /**
     * Method, which contains the logic for this command
     *
     * @param param Object containing the command args and the message object
     * @author sudo200
     * @see CommandParameter
     */
    @Override
    protected void execute(@NotNull CommandParameter param) {
        if(param.args.length == 0) {
            utilsChat.sendInfo(param.message.getChannel(),
                    "Please tell me the index of the track to remove!\n" +
                    "You can get it using `@" +
                            Objects.requireNonNull(param.message.getGuild().getMemberById(param.message.getJDA().getSelfUser().getId())).getEffectiveName()
                            + " q`");
            return;
        }

        final TrackScheduler scheduler = playerService.getScheduler(param.message.getGuild()).get();
        try {
            final int number = Integer.parseUnsignedInt(String.join(" ", param.args));
            if(scheduler.getTrackNum() >= number) {
                utilsChat.sendInfo(param.message.getChannel(), "**History cannot be changed!**");
                return;
            }
            scheduler.remove(number - 1);
        }
        catch (NumberFormatException e) {
            utilsChat.sendInfo(param.message.getChannel(), "**Whatever that is, it's not a number**");
        }
    }
}
