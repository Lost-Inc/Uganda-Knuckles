package at.lost_inc.ugandaknucklesbot.Startup;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandHandler;
import at.lost_inc.ugandaknucklesbot.Commands.Core.Plugins.PluginLoader;
import at.lost_inc.ugandaknucklesbot.Listeners.GuildVoiceListener;
import at.lost_inc.ugandaknucklesbot.Listeners.MessageReceiveListener;
import at.lost_inc.ugandaknucklesbot.Listeners.SlashCommandEventListener;
import at.lost_inc.ugandaknucklesbot.Modes;
import at.lost_inc.ugandaknucklesbot.Service.Audio.AudioPlayerManagerService;
import at.lost_inc.ugandaknucklesbot.Service.Audio.AudioPlayerService;
import at.lost_inc.ugandaknucklesbot.Service.Audio.SimpleAudioPlayerManagerService;
import at.lost_inc.ugandaknucklesbot.Service.Audio.SimpleAudioPlayerService;
import at.lost_inc.ugandaknucklesbot.Service.Event.EventBusService;
import at.lost_inc.ugandaknucklesbot.Service.Event.EventListenerService;
import at.lost_inc.ugandaknucklesbot.Service.Event.SimpleEventBusService;
import at.lost_inc.ugandaknucklesbot.Service.Event.SimpleEventListenerService;
import at.lost_inc.ugandaknucklesbot.Service.Games.GameService;
import at.lost_inc.ugandaknucklesbot.Service.Games.SimpleGameService;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.Author;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import at.lost_inc.ugandaknucklesbot.Util.UtilsVoice;
import com.google.gson.Gson;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Author("sudo200")
public final class BootCamp {
    private static final CommandHandler handler = CommandHandler.get();
    private static final List<Class<? extends BotCommand>> commandClasses = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(BootCamp.class);
    private static Phases phase = Phases.CONSTRUCTION;

    private BootCamp() {
    }

    @SafeVarargs
    public static boolean registerStaticCommands(Class<? extends BotCommand> @NotNull ... cmds) {
        if (!phase.equals(Phases.CONSTRUCTION))
            throw new IllegalStateException("Registration of commands is only possible during the \"construction\" phase!");
        return commandClasses.addAll(Arrays.asList(cmds));
    }

    public static Phases getPhase() {
        return phase;
    }

    public static void initialize(@NotNull JDA jda, @NotNull Modes mode) {
        logger.info("Loading dynamic commands...");
        final File basePath = new File(System.getProperty("user.dir") + File.separator + "plugins");
        if (!basePath.exists())
            basePath.mkdirs();
        try {
            commandClasses.addAll(new PluginLoader(basePath.toPath()).getCommands());
        } catch (IOException e) {
            logger.warn("Couldn't load dynamic commands, plugins won't work!");
        } catch (IllegalStateException e) {
            logger.warn("PluginLoader didn't have a dir, aborting!");
            System.exit(3);
        }
        logger.info("Constructing commands...");
        final List<BotCommand> commands = new ArrayList<>();
        for (Class<? extends BotCommand> commandClass : commandClasses)
            try {
                commands.add(commandClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                logger.warn("Exception while constructing class {}: {}", commandClass.getName(), e.getLocalizedMessage());
            }
        logger.info("Construction complete");
        phase = Phases.INITIALIZATION;
        logger.info("Initializing core services...");
        ServiceManager.setProvider(Random.class, new Random());
        ServiceManager.setProvider(UtilsChat.class, new UtilsChat());
        ServiceManager.setProvider(UtilsVoice.class, new UtilsVoice());
        ServiceManager.setProvider(Gson.class, new Gson());

        ServiceManager.setProvider(EventListenerService.class, new SimpleEventListenerService());

        ServiceManager.setProvider(AudioPlayerManagerService.class, new SimpleAudioPlayerManagerService());
        ServiceManager.setProvider(AudioPlayerService.class, new SimpleAudioPlayerService());
        ServiceManager.setProvider(GameService.class, new SimpleGameService());
        ServiceManager.setProvider(EventBusService.class, new SimpleEventBusService());

        logger.info("Initializing commands...");
        for (BotCommand cmd : commands) {
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
        for (BotCommand cmd : commands) {
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
        jda.addEventListener(
                (Object[]) ServiceManager.provideUnchecked(EventListenerService.class).get()
        );
        logger.info("Waiting for JDA to finish its start-up...");
        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            logger.error("Interrupted while waiting, cannot proceed!");
            System.exit(3);
        }
        logger.info("Registering commands...");
        handler.register(commands.toArray(new BotCommand[0]));

        final List<CommandData> cmds = new ArrayList<>();
        for (final BotCommand cmd : commands) {
            final Command props = cmd.getClass().getAnnotation(Command.class);
            cmds.add(
                    new CommandData(
                            props.name(), props.help().substring(0, Math.min(props.help().length(), 97)) + "...")
                            .addOptions(new OptionData(
                                    OptionType.STRING,
                                    "args",
                                    "Arguments for the command"
                            ))
            );
        }

        if(mode.getStability() < Modes.PRODUCTION.getStability()) {// Test mode
            final Guild guild = jda.getGuildById(705433083729412128L);// Our test guild
            guild.updateCommands().addCommands(cmds).complete();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                guild.updateCommands().complete();
            }));
        } else { // Production mode
            jda.updateCommands().addCommands(cmds).complete();
        }

        logger.info("Registration complete!");
        phase = Phases.RUNNING;
        logger.info("Done! Bot running!");
    }
}
