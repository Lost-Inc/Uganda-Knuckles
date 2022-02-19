package at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.Audio.AudioPlayerService;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.Author;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;

@Author("sudo200")
@Command(
        name = "resume",
        help = "Resumes a paused track",
        categories = {
                BotCommand.ICategories.VOICE,
                BotCommand.ICategories.UTIL
        },
        aliases = {
                "re"
        }
)
public final class VoiceCommandResume extends BotCommand {
    private UtilsChat utilsChat;
    private AudioPlayerService playerService;

    @Override
    public void onPostInitialization() {
        playerService = ServiceManager.provideUnchecked(AudioPlayerService.class);
        utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
    }

    @Override
    public void execute(@NotNull CommandParameter param) {
        final AtomicReference<AudioPlayer> player = playerService.getPlayer(param.message.getGuild());

        if (player.get().isPaused())
            player.get().setPaused(false);
    }
}
