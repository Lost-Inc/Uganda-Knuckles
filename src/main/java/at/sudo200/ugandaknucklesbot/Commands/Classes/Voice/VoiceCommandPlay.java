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
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Random;

public class VoiceCommandPlay extends BotCommand {
    private final Random random = new Random();

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
        return "Plays a song right in your voice channel!\n\n||RIP Groovy 2021||";
    }

    @Override
    protected String @Nullable [] getAliases() {
        return null;
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        if(param.args.length == 0) {
            if(random.nextInt(10) == 0) {
                utilsChat.sendInfo(param.message.getChannel(), "...this is what you get!");
                param.args = new String[1];
                param.args[0] = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
            }
            else {
                utilsChat.sendInfo(param.message.getChannel(), "Please tell me what to play!\nOr else...");
                return;
            }
        }

        final Guild guild = param.message.getGuild();
        final User user = param.message.getAuthor();
        final AudioPlayerManager audioPlayerManager = VoicePlayerManager.get();
        final AudioPlayer player = audioPlayerManager.createPlayer();
        audioPlayerManager.loadItem(
                String.join(" ", param.args),
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
