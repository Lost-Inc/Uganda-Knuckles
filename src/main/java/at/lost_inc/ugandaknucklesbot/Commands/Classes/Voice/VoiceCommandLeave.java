package at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandCategories;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import at.lost_inc.ugandaknucklesbot.Util.UtilsVoice;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VoiceCommandLeave extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();
    private final UtilsVoice utilsVoice = new UtilsVoice();

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
                        CommandCategories.VOICE,
                        // Auxiliary Catergories
                        CommandCategories.UTIL
                };
    }

    @Override
    protected String @Nullable [] getAliases() {
        return new String[]{
                "dc",
                "l"
        };
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        Guild guild = param.message.getGuild();
        AudioManager audioManager = guild.getAudioManager();
        JDA jda = param.message.getJDA();
        GuildVoiceState userVoiceState = utilsVoice.getVoiceState(param.message.getAuthor(), guild);
        GuildVoiceState botVoiceState = utilsVoice.getVoiceState(jda.getUserById(jda.getSelfUser().getId()), guild);

        if (!userVoiceState.inVoiceChannel()) {
            utilsChat.sendInfo(param.message.getChannel(), "**Join a voice channel first!**");
            return;
        }

        if (!botVoiceState.inVoiceChannel()) {
            utilsChat.sendInfo(param.message.getChannel(), "**I already left!**");
            return;
        }

        audioManager.closeAudioConnection();
    }
}
