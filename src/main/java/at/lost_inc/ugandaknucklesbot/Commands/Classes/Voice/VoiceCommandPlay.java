package at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice;

import at.lost_inc.ugandaknucklesbot.Commands.Core.Audio.VoiceAudioSendHandler;
import at.lost_inc.ugandaknucklesbot.Commands.Core.Audio.VoiceAudioTrackMessenger;
import at.lost_inc.ugandaknucklesbot.Commands.Core.Audio.VoiceAudioTrackScheduler;
import at.lost_inc.ugandaknucklesbot.Commands.Core.Audio.VoicePlayerManager;
import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandCategories;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import at.lost_inc.ugandaknucklesbot.Util.UtilsVoice;
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
import java.util.Queue;
import java.util.Random;

public class VoiceCommandPlay extends BotCommand {
    private static final HashMap<Long, AudioPlayer> players = new HashMap<>();
    private static final HashMap<Long, VoiceAudioTrackScheduler> trackSchedulers = new HashMap<>();
    private final Random random = new Random();
    private final UtilsChat utilsChat = new UtilsChat();
    private final UtilsVoice utilsVoice = new UtilsVoice();

    public static @Nullable Queue<AudioTrack> getQueueByGuildID(long id) {
        return trackSchedulers.get(id) == null ? null : trackSchedulers.get(id).getQueue();
    }

    public static @Nullable VoiceAudioTrackScheduler getTrackSchedulerByGuildID(long id) {
        return trackSchedulers.get(id);
    }

    public static @Nullable AudioPlayer getPlayerByGuildID(long id) {
        return players.get(id);
    }

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
        return new String[]{
                "p"
        };
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        final Guild guild = param.message.getGuild();
        final User user = param.message.getAuthor();

        if (utilsVoice.getVoiceState(user, guild).getChannel() == null) {// If not in a voice channel
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
        final AudioPlayer player = players.containsKey(guild.getIdLong()) ?// If a player exists for the guild,
                players.get(guild.getIdLong()) ://                            it gets used,
                audioPlayerManager.createPlayer();//                          else a new one gets created
        final VoiceAudioTrackScheduler trackScheduler = trackSchedulers.containsKey(guild.getIdLong()) ?// basically the same procedure as for the player
                trackSchedulers.get(guild.getIdLong()) :
                new VoiceAudioTrackScheduler(player);
        if (!trackSchedulers.containsKey(guild.getIdLong()))// Save new TrackScheduler
            trackSchedulers.put(guild.getIdLong(), trackScheduler);
        if (!players.containsKey(guild.getIdLong())) {// Set params and save new AudioPlayer
            player.addListener(new VoiceAudioTrackMessenger(param.message));
            player.addListener(trackScheduler);
            player.setVolume(50);
            players.put(guild.getIdLong(), player);
        }

        audioPlayerManager.loadItem(// Load song
                String.join(" ", param.args),
                new AudioLoadResultHandler() {
                    @Override
                    public void trackLoaded(AudioTrack track) {
                        trackScheduler.queue(track);
                        utilsChat.sendInfo(
                                param.message.getChannel(),
                                String.format("Queued \"%s\" by \"%s\" [%s]", track.getInfo().title, track.getInfo().author, user.getAsMention())
                        );
                    }

                    @Override
                    public void playlistLoaded(AudioPlaylist playlist) {
                        trackScheduler.queue(playlist.getTracks().toArray(new AudioTrack[0]));
                        utilsChat.sendInfo(
                                param.message.getChannel(),
                                String.format("Queued Playlist \"%s\" [%s]", playlist.getName(), user.getAsMention())
                        );
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

        if (!manager.isConnected()) {
            manager.setAutoReconnect(true);
            manager.setSendingHandler(new VoiceAudioSendHandler(player));
            manager.openAudioConnection(channel);
        }
    }
}
