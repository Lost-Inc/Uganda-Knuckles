package at.sudo200.ugandaknucklesbot.Commands.Classes.Voice;

import at.sudo200.ugandaknucklesbot.Commands.Core.Audio.VoiceAudioTrackScheduler;
import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandCategories;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VoiceCommandSkip extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();

    @Override
    protected String @Nullable [] getAliases() {
        return new String[] {
                "s"
        };
    }

    @Override
    protected String @NotNull [] getCategories() {
        return new String[] {
                CommandCategories.VOICE,
                CommandCategories.UTIL,
        };
    }

    @Override
    protected @NotNull String getName() {
        return "skip";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Skips the current (probably 10 hour) track";
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        VoiceAudioTrackScheduler scheduler = VoiceCommandPlay.getTrackSchedulerByGuildID(param.message.getGuild().getIdLong());

        if(scheduler != null)
            scheduler.skip();
        else
            utilsChat.sendInfo(param.message.getChannel(), "**I cannot skip, what has never started!**");
    }
}
