package at.lost_inc.ugandaknucklesbot;

import at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat.*;
import at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice.*;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandHandler;
import at.lost_inc.ugandaknucklesbot.Listeners.GuildVoiceListener;
import at.lost_inc.ugandaknucklesbot.Listeners.MessageReceiveListener;
import at.lost_inc.ugandaknucklesbot.Listeners.SlashCommandEventListener;
import at.lost_inc.ugandaknucklesbot.Service.Audio.AudioPlayerManagerService;
import at.lost_inc.ugandaknucklesbot.Service.Audio.AudioPlayerService;
import at.lost_inc.ugandaknucklesbot.Service.Audio.SimpleAudioPlayerManagerService;
import at.lost_inc.ugandaknucklesbot.Service.Audio.SimpleAudioPlayerService;
import at.lost_inc.ugandaknucklesbot.Service.Event.EventBusService;
import at.lost_inc.ugandaknucklesbot.Service.Event.SimpleEventBusService;
import at.lost_inc.ugandaknucklesbot.Service.Games.GameService;
import at.lost_inc.ugandaknucklesbot.Service.Games.SimpleGameService;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Commands.Core.Plugins.*;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import at.lost_inc.ugandaknucklesbot.Util.UtilsVoice;
import com.google.gson.Gson;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.slf4j.impl.SimpleLoggerConfiguration;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public final class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private final Collection<GatewayIntent> gatewayIntents = new ArrayList<>();
    private final JDA jda;
    private final CommandHandler handler;

    static {
        // Register Standard Services
        ServiceManager.setProvider(Random.class, new Random());
        ServiceManager.setProvider(UtilsChat.class, new UtilsChat());
        ServiceManager.setProvider(UtilsVoice.class, new UtilsVoice());
        ServiceManager.setProvider(Gson.class, new Gson());

        ServiceManager.setProvider(AudioPlayerManagerService.class, new SimpleAudioPlayerManagerService());
        ServiceManager.setProvider(AudioPlayerService.class, new SimpleAudioPlayerService());
        ServiceManager.setProvider(GameService.class, new SimpleGameService());
        ServiceManager.setProvider(EventBusService.class, new SimpleEventBusService());
        logger.debug("Injected standard services");
    }

    private Main() throws LoginException { // Token retrieved from Environment
        this.gatewayIntents.add(GatewayIntent.GUILD_MEMBERS);
        this.jda = JDABuilder.createDefault(System.getenv("DISCORDBOTTOKEN"))
                .enableIntents(this.gatewayIntents)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .build();
        logger.debug("Built JDA");
        this.handler = CommandHandler.get();
    }

    public static void main(String[] args) throws IOException {
        Main main = null;
        try {
            main = new Main();
            logger.debug("Constructed Main class");
        } catch (LoginException e) {
            logger.error("Couldn't log in!", e);
            System.exit(3);
        }

        // Listeners
        main.jda.addEventListener(
                new MessageReceiveListener(),
                new SlashCommandEventListener(),
                new GuildVoiceListener()
                );
        logger.debug("Added event listeners");

        // Set presence
        main.jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.listening("@" + main.jda.getSelfUser().getName()), false);
        logger.debug("Set presence");

        // Register your Commands here
        logger.debug("Registering static commands...");
        main.handler.register(
                // Chat commands
                new ChatCommandInator(),
                new ChatCommandLenny(),
                new ChatCommandAvatar(),
                new ChatCommandResolve(),
                new ChatCommandUrban(),
                new ChatCommandRandomCat(),
                new ChatCommandPoop(),
                new ChatCommandDice(),
                new ChatCommandRandomDog(),
                new ChatCommandFood(),
                new ChatCommandQRCode(),
                new ChatCommandPing(),
                new ChatCommandLeet(),
                new ChatCommandOwO(),
                new ChatCommandMCSkin(),
                new ChatCommandUselessWeb(),
                new ChatCommandWiki(),
                new ChatCommandClear(),
                new ChatCommandKick(),
                new ChatCommandBan(),
                new ChatCommandVersion(),
                new ChatCommandRemind(),
                new ChatCommandReddit(),
                new ChatCommand8Ball(),
                new ChatCommandServerStats(),
                new ChatCommandKill(),
                new ChatCommandHangman(),
                new ChatCommandGuess(),
                new ChatCommandPoll(),
                new ChatCommandInsult(),
                // Voice commands
                new VoiceCommandPlay(),
                new VoiceCommandPause(),
                new VoiceCommandQueue(),
                new VoiceCommandResume(),
                new VoiceCommandRemove(),
                new VoiceCommandSkip(),
                new VoiceCommandSync(),
                new VoiceCommandStop(),
                new VoiceCommandLoop(),
                new VoiceCommandLeave()
        );
        logger.debug("Registering dynamic commands...");

        final File basePath = new File(System.getProperty("user.dir") + File.separator + "plugins");
        if(!basePath.exists())
            basePath.mkdirs();
        main.handler.register(
                // Dynamically loaded commands
                new PluginLoader(basePath.toPath()).getCommands()
        );
        logger.debug("Registration complete");
    }
}
