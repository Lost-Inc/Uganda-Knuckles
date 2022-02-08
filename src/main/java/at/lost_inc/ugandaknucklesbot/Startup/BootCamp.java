package at.lost_inc.ugandaknucklesbot.Startup;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
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
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import at.lost_inc.ugandaknucklesbot.Util.UtilsVoice;
import com.google.gson.Gson;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class BootCamp {
    private static Phases phase = Phases.CONSTRUCTION;
    private static CommandHandler handler = CommandHandler.get();
    private static List<BotCommand> commands = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(BootCamp.class);

    private BootCamp() {
    }

    public static boolean registerCommands(BotCommand @NotNull ... cmds) {
        if(!phase.equals(Phases.CONSTRUCTION))
            throw new IllegalStateException("Registration of commands is only possible during the \"construction\" phase!");
        return commands.addAll(Arrays.asList(cmds));
    }

    public static Phases getPhase() {
        return phase;
    }

    public static void initialize(@NotNull JDA jda) {
        logger.info("Construction complete");
        phase = Phases.INITIALIZATION;
        logger.info("Initializing services...");
        ServiceManager.setProvider(Random.class, new Random());
        ServiceManager.setProvider(UtilsChat.class, new UtilsChat());
        ServiceManager.setProvider(UtilsVoice.class, new UtilsVoice());
        ServiceManager.setProvider(Gson.class, new Gson());

        ServiceManager.setProvider(AudioPlayerManagerService.class, new SimpleAudioPlayerManagerService());
        ServiceManager.setProvider(AudioPlayerService.class, new SimpleAudioPlayerService());
        ServiceManager.setProvider(GameService.class, new SimpleGameService());
        ServiceManager.setProvider(EventBusService.class, new SimpleEventBusService());

        logger.info("Initializing commands...");
        for(BotCommand cmd : commands) {
            try {
                cmd.onInitialization();
            } catch (Exception e) {
                logger.warn("Exception caught during initialization!", e);
            }
        }
        logger.info("Initialization complete!");
        phase = Phases.POST_INITIALIZATION;
        logger.info("Setting presence...");
        jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.listening("@" + jda.getSelfUser().getName()), false);

        logger.info("Post-Initializing commands...");
        for(BotCommand cmd : commands) {
            try {
                cmd.onPostInitialization();
            } catch (Exception e) {
                logger.warn("Exception caught during post-initialization!", e);
            }
        }
        logger.info("Post-Initialization complete!");
        phase = Phases.REGISTRATION;
        logger.info("Adding event listeners...");
        jda.addEventListener(
                new MessageReceiveListener(),
                new SlashCommandEventListener(),
                new GuildVoiceListener()
        );
        logger.info("Registering commands...");
        handler.register(commands.toArray(new BotCommand[0]));
        logger.info("Registration complete!");
        phase = Phases.RUNNING;
        logger.info("Bot running!");
    }
}
