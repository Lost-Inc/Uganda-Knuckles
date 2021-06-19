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

public class VoiceCommandLeave extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();
    
    @Override
    protected @NotNull String setName() {
        return "leave";
    }

    @Override
    protected void execute(CommandParameter param) {
        Guild guild = param.message.getGuild();
        AudioManager audioManager = guild.getAudioManager();
        @NotNull Member member = Objects.requireNonNull(guild.getMember(param.message.getAuthor()));
        @NotNull GuildVoiceState userVoiceState = Objects.requireNonNull(member.getVoiceState());
        @NotNull Member botMember = Objects.requireNonNull(guild.getMemberById(param.message.getJDA().getSelfUser().getId()));
        @NotNull GuildVoiceState botVoiceState = Objects.requireNonNull(botMember.getVoiceState());

        if(!userVoiceState.inVoiceChannel()) {
            utilsChat.sendInfo(param.message.getChannel(), "**Join a voice channel first!**");
            return;
        }

        if(!botVoiceState.inVoiceChannel()) {
            utilsChat.sendInfo(param.message.getChannel(), "**I already left!**");
            return;
        }

        audioManager.closeAudioConnection();
    }
}
