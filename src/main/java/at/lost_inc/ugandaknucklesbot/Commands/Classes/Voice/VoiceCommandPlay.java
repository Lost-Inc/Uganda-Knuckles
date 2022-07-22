package at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Commands.Core.Audio.TrackScheduler;
import at.lost_inc.ugandaknucklesbot.Commands.DI.Inject;
import at.lost_inc.ugandaknucklesbot.Service.Audio.AudioPlayerManagerService;
import at.lost_inc.ugandaknucklesbot.Service.Audio.AudioPlayerService;
import at.lost_inc.ugandaknucklesbot.Util.FileExtentions;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import at.lost_inc.ugandaknucklesbot.Util.UtilsVoice;
import at.lost_inc.ugandaknucklesbot.Util.YoutubeSearcher;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.FunctionalResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

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
    private final Logger log = LoggerFactory.getLogger(VoiceCommandPlay.class);

    @Inject
    private UtilsChat utilsChat;
    @Inject
    private UtilsVoice utilsVoice;
    @Inject
    private AudioPlayerManagerService playerManagerService;
    @Inject
    private AudioPlayerService playerService;
    @Inject
    private YoutubeSearcher searcher;

    @Override
    public void execute(@NotNull CommandParameter param) {
        if (param.args.length == 0 && param.message.getAttachments().size() == 0) {
            utilsChat.sendInfo(param.message.getChannel(), "Give me a link to what you want to be played or send me files...");
            return;
        }

        final AudioPlayerManager playerManager = playerManagerService.get();

        final Guild guild = param.message.getGuild();
        final AudioManager audioManager = guild.getAudioManager();
        final MessageChannel channel = param.message.getChannel();
        final GuildVoiceState voiceState = utilsVoice.getVoiceState(param.message.getAuthor(), guild);
        if (!voiceState.inVoiceChannel()) {
            utilsChat.sendInfo(channel, "Please join a voice channel first!");
            return;
        }
        final AtomicReference<TrackScheduler> scheduler = playerService.getScheduler(guild);
        final AtomicReference<AudioPlayer> player = playerService.getPlayer(guild);
        final AtomicReference<AudioSendHandler> sendHandler = playerService.getAudioHandler(guild).get();


        final BiConsumer<String, Boolean> play = (item, shouldSearchYT) -> {
            final Runnable cb = () -> {
                if (audioManager.getConnectedChannel() == null)
                    audioManager.openAudioConnection(voiceState.getChannel());
                if (audioManager.getSendingHandler() == null)
                    audioManager.setSendingHandler(sendHandler.get());

                if (player.get().getPlayingTrack() == null)
                    scheduler.get().start();
            };
            final Consumer<AudioTrack> trackConsumer = track -> {
                scheduler.get().queue(track);
                utilsChat.sendInfo(channel, String.format("Queued \"%s\" by \"%s\"", track.getInfo().title, track.getInfo().author));
                cb.run();
            };
            final Consumer<AudioPlaylist> playlistConsumer = audioPlaylist -> {
                scheduler.get().queue(audioPlaylist.getTracks().toArray(new AudioTrack[0]));
                utilsChat.sendInfo(channel, String.format("Queued playlist \"%s\"", audioPlaylist.getName()));
                cb.run();
            };
            final Consumer<FriendlyException> exceptionConsumer = e -> {
                utilsChat.sendInfo(channel, e.getMessage());
                if (e.severity == FriendlyException.Severity.SUSPICIOUS)
                    log.info("Seems kind of sus:", e);
                else if (e.severity == FriendlyException.Severity.FAULT)
                    log.error("Error caught:", e);
            };
            final Runnable nothingHandler = () -> utilsChat.sendInfo(channel, "**Nothing to hear here**");
            final Runnable emptyHandler = () -> {
                final String[] IDs = searcher.search(item);
                if (IDs.length == 0) {
                    nothingHandler.run();
                    return;
                }
                playerManager.loadItem(IDs[0], new FunctionalResultHandler(
                        trackConsumer,
                        playlistConsumer,
                        nothingHandler,
                        exceptionConsumer
                ));
            };

            playerManager.loadItem(item, new FunctionalResultHandler(
                    trackConsumer,
                    playlistConsumer,
                    shouldSearchYT ? emptyHandler : nothingHandler,
                    exceptionConsumer
            ));
        };


        if (param.args.length != 0)
            play.accept(String.join(" ", param.args), true);
        for (final Message.Attachment attachment : param.message.getAttachments())
            if (attachment.isVideo() || FileExtentions.AUDIO_EXTENTIONS.contains(attachment.getFileExtension()))
                play.accept(attachment.getProxyUrl(), false);
    }
}
