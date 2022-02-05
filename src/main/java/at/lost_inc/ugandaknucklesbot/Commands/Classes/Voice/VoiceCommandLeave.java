package at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.Audio.AudioPlayerService;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class VoiceCommandLeave extends BotCommand {
    private final AudioPlayerService playerService = ServiceManager.provideUnchecked(AudioPlayerService.class);

    @Override
    protected @NotNull String getName() {
        return "leave";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Leaves voice channel\n" +
                "(If in a voice channel)";
    }

    @Override
    protected String @NotNull [] getCategories() {
        return new String[]
                {
                        // Main Category
                        BotCommand.ICategories.VOICE,
                        // Auxiliary Catergories
                        BotCommand.ICategories.UTIL
                };
    }

    @Override
    protected String @Nullable [] getAliases() {
        return new String[]{
                "disconnect",
                "dc",
                "l",
        };
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        param.message.getGuild().getAudioManager().closeAudioConnection();
        playerService.destroy(param.message.getGuild());
    }
}
