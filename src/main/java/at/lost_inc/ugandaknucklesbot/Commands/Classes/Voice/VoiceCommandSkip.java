package at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.Audio.AudioPlayerService;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class VoiceCommandSkip extends BotCommand {
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
    private final AudioPlayerService playerService = ServiceManager.provideUnchecked(AudioPlayerService.class);

    @Override
    protected String @Nullable [] getAliases() {
        return new String[]{
                "s"
        };
    }

    @Override
    protected String @NotNull [] getCategories() {
        return new String[] {
                BotCommand.ICategories.VOICE,
                BotCommand.ICategories.UTIL,
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
        playerService.getScheduler(param.message.getGuild()).get().start();
    }
}
