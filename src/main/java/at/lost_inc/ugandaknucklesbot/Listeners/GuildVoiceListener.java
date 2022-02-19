package at.lost_inc.ugandaknucklesbot.Listeners;

import at.lost_inc.ugandaknucklesbot.Service.Audio.AudioPlayerService;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.Author;
import at.lost_inc.ugandaknucklesbot.Util.TimerTaskRunnable;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.voice.GenericGuildVoiceUpdateEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Timer;

@Author("sudo200")
public final class GuildVoiceListener extends ListenerAdapter {
    private final AudioPlayerService playerService = ServiceManager.provideUnchecked(AudioPlayerService.class);
    private final HashMap<Guild, Timer> timers = new HashMap<>();

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
        onJoin(event);
    }

    @Override
    public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {
        if(event.getNewValue().getMembers().size() < event.getOldValue().getMembers().size()) // Someone left
            onLeave(event);
        else
            onJoin(event);
    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        onLeave(event);
    }

    private void onLeave(@NotNull GenericGuildVoiceUpdateEvent event) {
        final List<Member> members = Objects.requireNonNull(event.getChannelLeft()).getMembers();
        if(!(members.size() == 1 && members.contains(event.getGuild().getMemberById(event.getJDA().getSelfUser().getId()))))
            return;

        final Timer timer = new Timer(true);
        timer.schedule(new TimerTaskRunnable(
                () -> {
                    event.getGuild().getAudioManager().closeAudioConnection();
                    playerService.destroy(event.getGuild());
                }
        ), 60 * 1000);
        timers.put(event.getGuild(), timer);
    }

    private void onJoin(@NotNull GenericGuildVoiceUpdateEvent event) {
        final List<Member> members = Objects.requireNonNull(event.getChannelJoined()).getMembers();
        if(members.size() > 1 && timers.containsKey(event.getGuild()))
            timers.remove(event.getGuild()).cancel();
    }
}
