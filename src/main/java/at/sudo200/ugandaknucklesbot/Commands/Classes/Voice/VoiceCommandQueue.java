package at.sudo200.ugandaknucklesbot.Commands.Classes.Voice;

import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandCategories;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Queue;

public class VoiceCommandQueue extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();

    @Override
    protected String @Nullable [] getAliases() {
        return new String[] {
                "q"
        };
    }

    @Override
    protected String @NotNull [] getCategories() {
        return new String[] {
                CommandCategories.VOICE,
                CommandCategories.UTIL
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
        if(queue == null || queue.isEmpty())
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
        utilsChat.sendInfo(
                param.message.getChannel(),
                "```java\n" +
                        queueString.toString() +
                        "```"
                );
    }
}
