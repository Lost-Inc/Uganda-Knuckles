package at.sudo200.ugandaknucklesbot.Commands;

import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class VoiceCommandSync extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();

    @Override
    protected @NotNull String setName() {
        return "sync";
    }

    @Override
    protected void execute(CommandParameter param) {// TODO: Finish
        Guild guild = param.message.getGuild();
        AudioManager audioManager = guild.getAudioManager();
        @NotNull Member member = Objects.requireNonNull(guild.getMember(param.message.getAuthor()));
        @NotNull GuildVoiceState voiceState = Objects.requireNonNull(member.getVoiceState());

        if(!voiceState.inVoiceChannel()) {
            utilsChat.sendInfo(param.message.getChannel(), "Join a voice channel first!");
            return;
        }

        audioManager.openAudioConnection(voiceState.getChannel());
    }
}
