package at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Commands.DI.Inject;
import at.lost_inc.ugandaknucklesbot.Commands.Core.Audio.TrackScheduler;
import at.lost_inc.ugandaknucklesbot.Service.Audio.AudioPlayerService;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.jetbrains.annotations.NotNull;

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
    @Inject
    private UtilsChat utilsChat;
    @Inject
    private AudioPlayerService playerService;

    @Override
    public void execute(@NotNull CommandParameter param) {
        final TrackScheduler scheduler = playerService.getScheduler(param.message.getGuild()).get();
        final TrackScheduler.PlayQueue queue = scheduler.getQueue();
        EmbedBuilder builder = getBuilder();
        StringBuilder stringBuilder = builder.getDescriptionBuilder();

        if (scheduler.getLooping())
            stringBuilder.append("**Looping enabled!**\n\n");

        stringBuilder.append("```haskell\n");
        if (queue.audioTracks.size() != 0)
            for (int i = 0; i < queue.audioTracks.size(); ++i) {
                final AudioTrackInfo info = queue.audioTracks.get(i).getInfo();
                if (i + 1 == queue.getTrackNum())
                    stringBuilder.append("\t\u2554Current Track\n");
                if ((i + 1 + ". " + info.author + " - \"" + info.title + '\"').length() + stringBuilder.length() > MessageEmbed.TEXT_MAX_LENGTH) {
                    stringBuilder.append("```");
                    utilsChat.send(param.message.getChannel(), builder.build());
                    builder = getBuilder();
                    stringBuilder = builder.getDescriptionBuilder();
                    if (scheduler.getLooping())
                        stringBuilder.append("**Looping enabled!**\n\n");
                    stringBuilder.append("```haskell\n");
                }
                stringBuilder.append(i + 1).append(". ").append(info.author).append(" - \"").append(info.title).append('\"');
                if (i + 1 == queue.getTrackNum())
                    stringBuilder.append("\n\t\u255ACurrent Track");
                stringBuilder.append('\n');
            }
        else
            stringBuilder.append("Nothing to see here!\n");

        stringBuilder.append("```");
        utilsChat.send(param.message.getChannel(), builder.build());
    }

    private @NotNull EmbedBuilder getBuilder() {
        return utilsChat.getDefaultEmbed().setTitle("Queue");
    }
}
