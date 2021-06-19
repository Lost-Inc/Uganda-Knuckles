package at.sudo200.ugandaknucklesbot.Commands.Classes.Voice;

import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import at.sudo200.ugandaknucklesbot.Util.UtilsVoice;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

public class VoiceCommandSync extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();
    private final UtilsVoice utilsVoice = new UtilsVoice();

    @Override
    protected @NotNull String getName() {
        return "sync";
    }

    @Override
    protected @NotNull String getHelp() {
        return "";
    }

    @Override
    protected void execute(CommandParameter param) {// TODO: Finish
        Guild guild = param.message.getGuild();
        AudioManager audioManager = guild.getAudioManager();
        @NotNull GuildVoiceState voiceState = utilsVoice.getVoiceState(param.message.getAuthor(), guild);

        if(!voiceState.inVoiceChannel()) {
            utilsChat.sendInfo(param.message.getChannel(), "Join a voice channel first!");
            return;
        }

        audioManager.openAudioConnection(voiceState.getChannel());
    }
}
