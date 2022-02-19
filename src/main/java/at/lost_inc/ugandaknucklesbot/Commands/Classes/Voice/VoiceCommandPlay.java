package at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Commands.Core.Audio.TrackScheduler;
import at.lost_inc.ugandaknucklesbot.Service.Audio.AudioPlayerManagerService;
import at.lost_inc.ugandaknucklesbot.Service.Audio.AudioPlayerService;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.Author;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import at.lost_inc.ugandaknucklesbot.Util.UtilsVoice;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.FunctionalResultHandler;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;

@Author("sudo200")
@Command(
        name = "play",
        help = "Plays a song right in your voice channel!\n||RIP [Groovy, Rhythm] 2021||",
        categories = {
                // Main category
                BotCommand.ICategories.VOICE,
                // Auxiliary categories
                BotCommand.ICategories.UTIL,
        },
        aliases = {
                "p"
        }
)
public final class VoiceCommandPlay extends BotCommand {
    private UtilsChat utilsChat;
    private UtilsVoice utilsVoice;
    private AudioPlayerManager playerManager;
    private AudioPlayerService playerService;

    @Override
    public void onPostInitialization() {
        playerService = ServiceManager.provideUnchecked(AudioPlayerService.class);
        playerManager = ServiceManager.provideUnchecked(AudioPlayerManagerService.class).get();
        utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
        utilsVoice = ServiceManager.provideUnchecked(UtilsVoice.class);
    }

    @Override
    public void execute(@NotNull CommandParameter param) {
        if(param.args.length == 0) {
            utilsChat.sendInfo(param.message.getChannel(), "Give me a link to what you want to be played...");
            return;
        }

        final Guild guild = param.message.getGuild();
        final AudioManager audioManager = guild.getAudioManager();
        final MessageChannel channel = param.message.getChannel();
        final GuildVoiceState voiceState = utilsVoice.getVoiceState(param.message.getAuthor(), guild);
        if(!voiceState.inVoiceChannel()) {
            utilsChat.sendInfo(channel, "Please join a voice channel first!");
            return;
        }
        final AtomicReference<TrackScheduler> scheduler = playerService.getScheduler(guild);
        final AtomicReference<AudioPlayer> player = playerService.getPlayer(guild);
        final AtomicReference<AudioSendHandler> sendHandler = playerService.getAudioHandler(guild).get();

        final Runnable cb = () -> {
            if(audioManager.getConnectedChannel() == null)
                audioManager.openAudioConnection(voiceState.getChannel());
            if(audioManager.getSendingHandler() == null)
                audioManager.setSendingHandler(sendHandler.get());

            if(player.get().getPlayingTrack() == null)
                scheduler.get().start();
        };

        playerManager.loadItem(String.join(" ", param.args), new FunctionalResultHandler(
                track -> {
                    scheduler.get().queue(track);
                    utilsChat.sendInfo(channel, String.format("Queued \"%s\" by \"%s\"", track.getInfo().title, track.getInfo().author));
                    cb.run();
                },
                audioPlaylist -> {
                    scheduler.get().queue(audioPlaylist.getTracks().toArray(new AudioTrack[0]));
                    utilsChat.sendInfo(channel, String.format("Queued playlist \"%s\"", audioPlaylist.getName()));
                    cb.run();
                },
                () -> utilsChat.sendInfo(channel, "**Nothing to hear here**"),
                e -> utilsChat.sendInfo(channel, e.getMessage())
        ));
    }
}
