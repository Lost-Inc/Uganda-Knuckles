package at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.Command;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.Audio.AudioPlayerService;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@Command(
        name = "leave",
        help = "Leaves voice channel\n" +
                "(If in a voice channel)",
        categories = {
                // Main Category
                BotCommand.ICategories.VOICE,
                // Auxiliary Catergories
                BotCommand.ICategories.UTIL
        },
        aliases = {
                "disconnect",
                "dc",
                "l",
        }
)
public final class VoiceCommandLeave extends BotCommand {
    private final AudioPlayerService playerService = ServiceManager.provideUnchecked(AudioPlayerService.class);

    @Override
    protected void execute(@NotNull CommandParameter param) {
        param.message.getGuild().getAudioManager().closeAudioConnection();
        playerService.destroy(param.message.getGuild());
    }
}
