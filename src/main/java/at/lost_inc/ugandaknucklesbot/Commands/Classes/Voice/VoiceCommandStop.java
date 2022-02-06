package at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.Command;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.Audio.AudioPlayerService;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@Command(
        name = "stop",
        help = "Stops playing audio (if playing that is) __and deletes queue__",
        categories = {
                BotCommand.ICategories.VOICE,
                BotCommand.ICategories.UTIL,
        },
        aliases = {
                "s"
        }
)
public final class VoiceCommandStop extends BotCommand {
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
    private final AudioPlayerService playerService = ServiceManager.provideUnchecked(AudioPlayerService.class);

    @Override
    protected void execute(@NotNull CommandParameter param) {
        playerService.destroy(param.message.getGuild());
    }
}
