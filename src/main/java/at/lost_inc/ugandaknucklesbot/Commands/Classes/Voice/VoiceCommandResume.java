package at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VoiceCommandResume extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();

    @Override
    protected String @Nullable [] getAliases() {
        return new String[]{
                "re"
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
        return "resume";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Resumes a paused track";
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        AudioPlayer player = VoiceCommandPlay.getPlayerByGuildID(param.message.getGuild().getIdLong());

        if (player == null) {
            utilsChat.sendInfo(param.message.getChannel(), "**I cannot resume, what has never started!**");
            return;
        }

        if (player.isPaused())
            player.setPaused(false);
        else
            utilsChat.sendInfo(param.message.getChannel(), "**I see, you think it gets better than this, right?**");
    }
}
