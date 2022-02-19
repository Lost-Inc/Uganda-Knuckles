package at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.Audio.AudioPlayerService;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.Author;
import org.jetbrains.annotations.NotNull;

@Author("sudo200")
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
    private AudioPlayerService playerService;

    @Override
    public void onPostInitialization() {
        playerService = ServiceManager.provideUnchecked(AudioPlayerService.class);
    }

    @Override
    public void execute(@NotNull CommandParameter param) {
        param.message.getGuild().getAudioManager().closeAudioConnection();
        playerService.destroy(param.message.getGuild());
    }
}
