package at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Commands.Core.Audio.TrackScheduler;
import at.lost_inc.ugandaknucklesbot.Service.Audio.AudioPlayerService;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.Author;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Author("sudo200")
@Command(
        name = "jump",
        help = "Jumps to track at specified position",
        categories = {
                BotCommand.ICategories.UTIL,
                BotCommand.ICategories.VOICE
        },
        aliases = {
                "j"
        }
)
public final class VoiceCommandJump extends BotCommand {
    private UtilsChat utilsChat;
    private AudioPlayerService playerService;

    @Override
    public void onPostInitialization() {
        utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
        playerService = ServiceManager.provideUnchecked(AudioPlayerService.class);
    }

    @Override
    public void execute(@NotNull CommandParameter param) {
        if(param.args.length == 0) {
            utilsChat.sendInfo(param.message.getChannel(),
                    "Please tell me the index of the track to jump to!\n" +
                            "You can get it using `@" +
                            Objects.requireNonNull(param.message.getGuild().getMemberById(param.message.getJDA().getSelfUser().getId())).getEffectiveName()
                            + " q`");
            return;
        }

        final TrackScheduler scheduler = playerService.getScheduler(param.message.getGuild()).get();
        try {
            scheduler.setTrackNum(Integer.parseInt(String.join(" ", param.args)) - 1);
            scheduler.start();
        } catch (NumberFormatException e) {
            utilsChat.sendInfo(param.message.getChannel(), "**Whatever that is, it's not a number**");
        }
    }
}
