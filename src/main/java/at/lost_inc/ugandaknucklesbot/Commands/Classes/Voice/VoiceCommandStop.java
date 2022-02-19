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
    private AudioPlayerService playerService;

    @Override
    public void onPostInitialization() {
        playerService = ServiceManager.provideUnchecked(AudioPlayerService.class);
    }

    @Override
    public void execute(@NotNull CommandParameter param) {
        playerService.destroy(param.message.getGuild());
    }
}
