package at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand.ICategories;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Queue;

public class VoiceCommandQueue extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();

    @Override
    protected String @Nullable [] getAliases() {
        return new String[]{
                "q"
        };
    }

    @Override
    protected String @NotNull [] getCategories() {
        return new String[]{
                BotCommand.ICategories.VOICE,
                BotCommand.ICategories.UTIL
        };
    }

    @Override
    protected @NotNull String getName() {
        return "queue";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Shows the current queue";
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        StringBuilder queueString = new StringBuilder();
        Queue<AudioTrack> queue = VoiceCommandPlay.getQueueByGuildID(param.message.getGuild().getIdLong());
        AudioPlayer player = VoiceCommandPlay.getPlayerByGuildID(param.message.getGuild().getIdLong());
        AudioTrack track = player == null ? null : player.getPlayingTrack();

        if (track != null) {// If currently playing track
            queueString.append(String.format(
                    "__Currently Playing__: **%s**\n\n",
                    track.getInfo().title
            ));
        }

        queueString.append("```java\n");

        if (queue == null || queue.isEmpty())
            queueString.append("Nothing to see here (O.O)");
        else {
            final AudioTrack[] tracks = queue.toArray(new AudioTrack[0]);
            for (int i = 0; i < queue.size(); i++)
                queueString.append(String.format(
                        "%d) %s\n",
                        i + 1,
                        tracks[i].getInfo().title
                ));
        }
        queueString.append("```");
        utilsChat.sendInfo(
                param.message.getChannel(),
                queueString.toString()
        );
    }
}
