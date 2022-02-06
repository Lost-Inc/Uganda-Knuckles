package at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice;

import at.lost_inc.ugandaknucklesbot.Commands.Core.Audio.TrackScheduler;
import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.Command;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.Audio.AudioPlayerService;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@Command(
        name = "queue",
        help = "Shows the current queue",
        categories = {
                BotCommand.ICategories.VOICE,
                BotCommand.ICategories.UTIL
        },
        aliases = {
                "q"
        }
)
public final class VoiceCommandQueue extends BotCommand {
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
    private final AudioPlayerService playerService = ServiceManager.provideUnchecked(AudioPlayerService.class);

    @Override
    protected void execute(@NotNull CommandParameter param) {
        final TrackScheduler scheduler = playerService.getScheduler(param.message.getGuild()).get();
        final TrackScheduler.PlayQueue queue = scheduler.getQueue();
        final EmbedBuilder builder = utilsChat.getDefaultEmbed().setTitle("Queue");
        final StringBuilder stringBuilder = new StringBuilder();

        if(scheduler.getLooping())
            stringBuilder.append("**Looping enabled!**\n\n");

        stringBuilder.append("```haskell\n");
        if(queue.audioTracks.size() != 0)
            for(int i = 0; i < queue.audioTracks.size(); ++i) {
                final AudioTrackInfo info = queue.audioTracks.get(i).getInfo();
                if(i+1 == queue.getTrackNum())
                    stringBuilder.append("\t\u2554Current Track\n");
                stringBuilder.append(i+1).append(". ").append(info.author).append(" - \"").append(info.title).append('\"');
                if(i+1 == queue.getTrackNum())
                    stringBuilder.append("\n\t\u255ACurrent Track");
                stringBuilder.append('\n');
            }
        else
            stringBuilder.append("Nothing to see here!\n");

        builder.setDescription(stringBuilder.append("```"));

        try {
            utilsChat.send(param.message.getChannel(), builder.build());
        } catch (Throwable e) {
            utilsChat.sendInfo(param.message.getChannel(), "Sorry, your queue is to big for Discord!");
        }
    }
}
