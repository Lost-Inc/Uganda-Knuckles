package at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Service.Temp.NotAvailable;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import at.lost_inc.ugandaknucklesbot.Util.UtilsVoice;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class VoiceCommandLeave extends BotCommand {
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
    private final UtilsVoice utilsVoice = ServiceManager.provideUnchecked(UtilsVoice.class);

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
    }
}
