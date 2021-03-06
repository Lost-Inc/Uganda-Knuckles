package at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Commands.DI.Inject;
import at.lost_inc.ugandaknucklesbot.Service.Audio.AudioPlayerService;
import org.jetbrains.annotations.NotNull;

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
    @Inject
    private AudioPlayerService playerService;

    @Override
    public void execute(@NotNull CommandParameter param) {
        param.message.getGuild().getAudioManager().closeAudioConnection();
        playerService.destroy(param.message.getGuild());
    }
}
