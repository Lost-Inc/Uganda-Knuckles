package at.sudo200.ugandaknucklesbot.Commands.Classes.Voice;

import at.sudo200.ugandaknucklesbot.Commands.Core.Audio.VoiceAudioLoadResultHandler;
import at.sudo200.ugandaknucklesbot.Commands.Core.Audio.VoiceAudioSendHandler;
import at.sudo200.ugandaknucklesbot.Commands.Core.Audio.VoicePlayerManager;
import at.sudo200.ugandaknucklesbot.Commands.Core.Audio.VoiceTrackScheduler;
import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandCategories;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import at.sudo200.ugandaknucklesbot.Util.UtilsVoice;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class VoiceCommandPlay extends BotCommand {
    private final UtilsChat utilsChat = new UtilsChat();
    private final UtilsVoice utilsVoice = new UtilsVoice();
    private final HashMap<Long, VoiceAudioSendHandler> voiceAudioSendHandler = new HashMap<>();

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
        return "Start playing a song (lol)";
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        final Guild guild = param.message.getGuild();
        final User user = param.message.getAuthor();
        final AudioPlayerManager audioPlayerManager = VoicePlayerManager.get();
        final AudioPlayer player = audioPlayerManager.createPlayer();
        audioPlayerManager.loadItem(
                "https://sensibass.radioca.st/stream",
                new VoiceAudioLoadResultHandler(player)
        );
        voiceAudioSendHandler.put(guild.getIdLong(), new VoiceAudioSendHandler(player));

        player.addListener(new VoiceTrackScheduler());

        final VoiceChannel channel = utilsVoice.getVoiceState(user, guild).getChannel();
        final AudioManager manager = guild.getAudioManager();

        manager.setAutoReconnect(true);
        manager.setSendingHandler(voiceAudioSendHandler.get(guild.getIdLong()));
        manager.openAudioConnection(channel);
    }
}
