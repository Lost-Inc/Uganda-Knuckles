package at.sudo200.ugandaknucklesbot.Util;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class UtilsVoice {
    public @NotNull GuildVoiceState getVoiceState(User user, Guild guild) {
        @NotNull Member member = Objects.requireNonNull(guild.getMember(user));
        return Objects.requireNonNull(member.getVoiceState());
    }
}
