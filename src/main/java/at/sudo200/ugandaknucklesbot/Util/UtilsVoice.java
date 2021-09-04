package at.sudo200.ugandaknucklesbot.Util;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class UtilsVoice {
    // Gets voicestate of user from guild
    public @NotNull GuildVoiceState getVoiceState(User user, @NotNull Guild guild) {
        @NotNull Member member = Objects.requireNonNull(guild.getMember(user));
        return Objects.requireNonNull(member.getVoiceState());
    }
}
