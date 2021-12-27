package at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice;

import at.lost_inc.ugandaknucklesbot.Commands.Core.Audio.VoiceAudioEchoHandler;
import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.TimerTaskRunnable;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import at.lost_inc.ugandaknucklesbot.Util.UtilsVoice;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;

public final class VoiceCommandSync extends BotCommand {
    private static final HashMap<String, Guild> guilds = new HashMap<>();
    private static final HashMap<Guild, VoiceChannel> vcs = new HashMap<>();
    private final Random random = ServiceManager.provideUnchecked(Random.class);
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
    private final UtilsVoice utilsVoice = ServiceManager.provideUnchecked(UtilsVoice.class);

    @Override
    protected String @Nullable [] getAliases() {
        return null;
    }

    @Override
    protected String @NotNull [] getCategories() {
        return new String[]{
                // Main Category
                BotCommand.ICategories.UTIL,
                // Auxiliary categories
                BotCommand.ICategories.VOICE,
        };
    }

    @Override
    protected @NotNull String getName() {
        return "sync";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Syncs two voicechannels on two different servers.\n\n**Why? Because we can!**";
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        final Guild guild = param.message.getGuild();
        final MessageChannel channel = param.message.getChannel();
        final GuildVoiceState voiceState = utilsVoice.getVoiceState(param.message.getAuthor(), guild);

        if (param.args.length == 0) {// Called without token
            if (guilds.containsValue(guild)) {// Check for already existing token
                utilsChat.sendInfo(channel, String.format(
                        "**Somebody already generated a token for this server!**\n\nToken: `%s`",
                        guilds.entrySet().stream().filter(entry -> entry.getValue().equals(guild))
                                .findFirst().orElse(null)
                ));
                return;
            }

            if (voiceState.getChannel() == null) {// Check for voice channel
                utilsChat.sendInfo(channel, "**Please join the voice channel you wanna sync mate!**\n\nWhen will you learn!");
                return;
            }
            // Generate token
            final String hash = getMD5Hash(Integer.toString(random.nextInt(Integer.MAX_VALUE))).substring(0, 5);
            guilds.put(// Store token
                    hash,
                    guild
            );
            vcs.put(// Store voice channel id
                    guild,
                    voiceState.getChannel()
            );

            new Timer(true).schedule(new TimerTaskRunnable(() -> {// Schedule task for token expiration
                guilds.remove(hash);
                vcs.remove(guild);
            }), 5 * 60 * 1000);

            utilsChat.sendInfo(
                    channel,
                    String.format(
                            "Execute \"%s sync %s\" on another guild to sync 2 voice channels together",
                            guild.getJDA().getSelfUser().getAsMention(),
                            hash
                    )
            );
        } else {
            if (!guilds.containsKey(param.args[0])) {
                utilsChat.sendInfo(channel, "This is not a valid token!\n\nMaybe it has expired like your free trial of WinRAR?");
                return;
            }

            if (voiceState.getChannel() == null) {
                utilsChat.sendInfo(channel, "**Please join the voice channel you wanna sync mate!**\n\nWhen will you learn!");
                return;
            }

            final AudioManager priManager = guild.getAudioManager();

            final Guild secGuild = guilds.get(param.args[0]);
            final VoiceChannel secVoiceChannel = vcs.get(secGuild);
            final AudioManager secManager = secGuild.getAudioManager();

            final VoiceAudioEchoHandler
                    priToSec = new VoiceAudioEchoHandler(),
                    secToPri = new VoiceAudioEchoHandler();

            priManager.setAutoReconnect(true);
            secManager.setAutoReconnect(true);

            priManager.setReceivingHandler(priToSec);
            secManager.setSendingHandler(priToSec);

            priManager.setSendingHandler(secToPri);
            secManager.setReceivingHandler(secToPri);

            priManager.openAudioConnection(voiceState.getChannel());
            secManager.openAudioConnection(secVoiceChannel);
        }
    }

    private @NotNull String getMD5Hash(@NotNull String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : array) {
                stringBuilder.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
            }
            return stringBuilder.toString();
        } catch (java.security.NoSuchAlgorithmException ignored) {
        }
        return "";
    }
}
