package at.sudo200.ugandaknucklesbot.Commands.Classes.Voice;

import at.sudo200.ugandaknucklesbot.Commands.Core.Audio.VoiceAudioSendHandler;
import at.sudo200.ugandaknucklesbot.Commands.Core.Audio.VoiceAudioTrackMessenger;
import at.sudo200.ugandaknucklesbot.Commands.Core.Audio.VoiceAudioTrackScheduler;
import at.sudo200.ugandaknucklesbot.Commands.Core.Audio.VoicePlayerManager;
import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandCategories;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import at.sudo200.ugandaknucklesbot.Util.UtilsVoice;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Random;

public class VoiceCommandPlay extends BotCommand {
    public static final HashMap<Long, AudioPlayer> players = new HashMap<>();
    private static final HashMap<Long, VoiceAudioTrackScheduler> trackSchedulers = new HashMap<>();
    private final Random random = new Random();
    private final UtilsChat utilsChat = new UtilsChat();
    private final UtilsVoice utilsVoice = new UtilsVoice();

    @Override
    protected String @NotNull [] getCategories() {
        return new String[]{
                // Main category
                CommandCategories.VOICE,
                // Auxiliary categories
                CommandCategories.UTIL,
        };
    }

    @Override
    protected @NotNull String getName() {
        return "play";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Plays a song right in your voice channel!\n||RIP Groovy 2021||";
    }

    @Override
    protected String @Nullable [] getAliases() {
        return null;
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        final Guild guild = param.message.getGuild();
        final User user = param.message.getAuthor();

        if(utilsVoice.getVoiceState(user, guild).getChannel() == null) {
            utilsChat.sendInfo(param.message.getChannel(), "**Please join a voice channel first!**\n\nWhen will you learn!");
            return;
        }

        if (param.args.length == 0) {
            if (random.nextInt(10) == 0) {
                utilsChat.sendInfo(param.message.getChannel(), "...this is what you get! :smiling_imp:");
                param.args = new String[1];
                param.args[0] = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
            } else {
                utilsChat.sendInfo(param.message.getChannel(), "**Please tell me what to play!**\nOr else...");
                return;
            }
        }

        final AudioPlayerManager audioPlayerManager = VoicePlayerManager.get();
        final AudioPlayer player = players.containsKey(guild.getIdLong()) ?
                players.get(guild.getIdLong()) :
                audioPlayerManager.createPlayer();
        final VoiceAudioTrackScheduler trackScheduler = trackSchedulers.containsKey(guild.getIdLong()) ?
                trackSchedulers.get(guild.getIdLong()) :
                new VoiceAudioTrackScheduler(player);
        if (!trackSchedulers.containsKey(guild.getIdLong()))
            trackSchedulers.put(guild.getIdLong(), trackScheduler);
        if (!players.containsKey(guild.getIdLong())) {
            players.put(guild.getIdLong(), player);
            player.addListener(new VoiceAudioTrackMessenger(param.message));
            player.addListener(trackScheduler);
            player.setVolume(50);
        }

        audioPlayerManager.loadItem(
                String.join(" ", param.args),
                new AudioLoadResultHandler() {
                    @Override
                    public void trackLoaded(AudioTrack track) {
                        trackScheduler.queue(track);
                    }

                    @Override
                    public void playlistLoaded(AudioPlaylist playlist) {
                        trackScheduler.queue(playlist.getTracks().toArray(new AudioTrack[0]));
                    }

                    @Override
                    public void noMatches() {
                        utilsChat.sendInfo(param.message.getChannel(), "Whatever this is, it's not playable!\n\nFor me at least...");
                    }

                    @Override
                    public void loadFailed(FriendlyException exception) {
                    }
                }
        );

        final VoiceChannel channel = utilsVoice.getVoiceState(user, guild).getChannel();
        final AudioManager manager = guild.getAudioManager();

        manager.setAutoReconnect(true);
        manager.setSendingHandler(new VoiceAudioSendHandler(player));
        manager.openAudioConnection(channel);
    }
}
