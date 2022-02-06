package at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice;

import at.lost_inc.ugandaknucklesbot.Commands.Core.Audio.TrackScheduler;
import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.Audio.AudioPlayerService;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class VoiceCommandRemove extends BotCommand {
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
    private final AudioPlayerService playerService = ServiceManager.provideUnchecked(AudioPlayerService.class);


    /**
     * Returns aliases for the command.
     * When no aliases are defined, null is returned instead
     *
     * @return Array of alias strings or null
     * @author sudo200
     */
    @Override
    protected String @Nullable [] getAliases() {
        return new String[] {
                "r"
        };
    }

    /**
     * Returns category strings
     * of the categories the command belongs to
     *
     * @return Array of category strings
     * @author sudo200
     * @see ICategories
     */
    @Override
    protected String @NotNull [] getCategories() {
        return new String[] {
                ICategories.VOICE,
                ICategories.UTIL
        };
    }

    /**
     * @return Command name
     * @author sudo200
     */
    @Override
    protected @NotNull String getName() {
        return "remove";
    }

    /**
     * @return Help page describing the command
     * @author sudo200
     */
    @Override
    protected @NotNull String getHelp() {
        return "Removes track at specified position";
    }

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
