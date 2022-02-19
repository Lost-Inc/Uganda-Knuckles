package at.lost_inc.ugandaknucklesbot.Util;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Author("sudo200")
public class UtilsVoice {
    /**
     * Gets voicestate of user from guild
     * @param user user to get voice state from
     * @param guild guild to get voice state from
     * @return user voice state
     */
    public final @NotNull GuildVoiceState getVoiceState(User user, @NotNull Guild guild) {
        @NotNull Member member = Objects.requireNonNull(guild.getMember(user));
        return Objects.requireNonNull(member.getVoiceState());
    }
}
