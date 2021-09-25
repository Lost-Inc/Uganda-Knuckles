package at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandCategories;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VoiceCommandStop extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();

    @Override
    protected String @Nullable [] getAliases() {
        return new String[]{
                "s"
        };
    }

    @Override
    protected String @NotNull [] getCategories() {
        return new String[]{
                CommandCategories.VOICE,
                CommandCategories.UTIL,
        };
    }

    @Override
    protected @NotNull String getName() {
        return "stop";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Stops playing audio (if playing that is)";
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        final AudioPlayer player = VoiceCommandPlay.getPlayerByGuildID(param.message.getGuild().getIdLong());

        if (player != null)
            player.destroy();
        else
            utilsChat.sendInfo(param.message.getChannel(), "**I cannot stop, what's already stopped!**");
    }
}
