package at.lost_inc.ugandaknucklesbot.Commands.Core;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Util.Author;
import at.lost_inc.ugandaknucklesbot.Util.SlashCommandMessageAdapter;
import at.lost_inc.ugandaknucklesbot.Util.TimerTaskRunnable;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Class containing the logic making the bot tick
 *
 * @author sudo200
 */
@Author("sudo200")
public final class CommandHandler {
    private static final CommandHandler instance;

    static {
        instance = new CommandHandler();
    }

    private final ThreadGroup threadGroup = new ThreadGroup("bot commands");
    private final Collection<Cmd> commands = new ArrayList<>();
    private final Map<String, Collection<Cmd>> categories = new HashMap<>();
    private final Set<Long> userCooldown = new HashSet<>();
    private final Set<Long> guildCooldown = new HashSet<>();
    private final UtilsChat utilsChat = new UtilsChat();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private CommandHandler() {
    }

    public static CommandHandler get() {
        return instance;
    }

    private boolean register(@NotNull BotCommand command) {
        if (!command.getClass().isAnnotationPresent(Command.class)) {
            logger.warn(String.format(
                    "Command annotation was not found in %s! Command will be discarded!",
                    command.getClass())
            );
            return false;
        }
        final Cmd cmd = new Cmd(
                command,
                command.getClass().getAnnotation(Command.class)
        );

        for (String categoryName : cmd.props.categories()) {
            if (!categories.containsKey(categoryName))
                categories.put(categoryName, new ArrayList<>());

            Collection<Cmd> com = categories.get(categoryName);
            if (!com.contains(cmd))
                com.add(cmd);
        }
        return this.commands.add(cmd);
    }

    // methods for registering commands
    public boolean register(BotCommand @NotNull ... commands) {
        boolean okay = true;
        for (BotCommand command : commands)
            if (!register(command))
                okay = false;
        System.gc();
        return okay;
    }

    public void handle(@NotNull SlashCommandEvent event) {
        handle(new SlashCommandMessageAdapter(event) {
            @Override
            public @NotNull String getContentRaw() {
                if(!event.isAcknowledged()) {
                    final InteractionHook action = event.reply(EmbedBuilder.ZERO_WIDTH_SPACE).complete();
                    action.deleteOriginal().queueAfter(100, TimeUnit.MILLISECONDS);
                }
                return getJDA().getSelfUser().getAsMention() + " " + super.getContentRaw();
            }
        });
    }

    // method called by MessageReceiveListener
    public void handle(@NotNull Message event) {
        logger.trace("Message from guild {}: \"{}\"", event.getGuild().getId(), event.getContentRaw().trim());

        // Object, that gets passed to the command classes
        CommandParameter param = new CommandParameter();
        String[] args = event
                .getContentRaw()
                .trim()
                .split(" +");// "+" makes it work with more spaces


        // If not mentioned, ignore
        if (!utilsChat.isMention(args[0])) return;
        if (!utilsChat.getMemberByMention(
                args[0], event.getGuild()).equals(
                event.getGuild().getMemberById(
                        event.getJDA().getSelfUser().getId()
                )
        )
        ) return;
        if (args.length == 1) return;

        if (userCooldown.contains(event.getAuthor().getIdLong())) {
            utilsChat.send(event.getChannel(), event.getAuthor().getAsMention());
            utilsChat.sendInfo(
                    event.getChannel(),
                    "Woah! You exceeded the per-user cooldown!\n\nPlease take a chill pill!"
            );
            return;
        }

        if (guildCooldown.contains(event.getGuild().getIdLong())) {
            utilsChat.send(event.getChannel(), "<#" + event.getChannel().getId() + '>');
            utilsChat.sendInfo(
                    event.getChannel(),
                    "Y'all together exceeded the guild rate limit!\n\nPlease calm down!"
            );
            return;
        }

        userCooldown.add(event.getAuthor().getIdLong());
        guildCooldown.add(event.getGuild().getIdLong());

        new Timer(true).schedule(new TimerTaskRunnable(
                        () -> userCooldown.remove(event.getAuthor().getIdLong())
                ),
                500
        );
        new Timer(true).schedule(new TimerTaskRunnable(
                        () -> guildCooldown.remove(event.getGuild().getIdLong())
                ),
                100
        );

        if (args[1].equalsIgnoreCase("help")) {// help command
            Thread helpThread = new Thread(threadGroup, () -> {
                final EmbedBuilder builder = utilsChat.getDefaultEmbed();
                if (args.length != 3) {// show categories
                    builder.setTitle(":book: Help categories");
                    for (String name : categories.keySet())
                        builder.addField(name, "\t", true);
                    builder.addField("All", "\t", true);
                } else if (args[2].equalsIgnoreCase("all")) {// show all commands
                    builder.setTitle("All commands");
                    for (Cmd command : commands)
                        builder.addField(
                                command.props.name(),
                                command.props.help() + (command.props.aliases().length != 0 ? "\n\n*Aliases:* " + String.join(",", command.props.aliases()) : ""),
                                false
                        );
                } else {
                    final String key = categories.keySet().stream().filter(k -> k.matches("(?i).*" + args[2] + ".*")).findFirst().orElse(null);
                    if (key != null) {// show commands from category
                        builder.setTitle(key);
                        for (Cmd command : categories.get(key))
                            builder.addField(
                                    command.props.name(),
                                    command.props.help() + (command.props.aliases().length != 0 ? "\n\n*Aliases:* " + String.join(",", command.props.aliases()) : ""),
                                    false
                            );
                    } else {
                        final Cmd command = commands.stream().filter(c -> c.props.name().matches("(?i).*" + args[2] + ".*")).findFirst().orElse(null);
                        if (command != null) {// show help of specific command
                            builder.setTitle("Help for \"" + command.props.name() + "\" command");
                            builder.addField(
                                    command.props.name(),
                                    command.props.help() + (command.props.aliases().length != 0 ? "\n\n*Aliases:* " + String.join(",", command.props.aliases()) : ""),
                                    false
                            );
                        } else // command does not exist
                            builder.setDescription("**There is nothing called \"" + args[2] + "\"!**\nTry \"all\"");
                    }
                }
                utilsChat.send(event.getChannel(), builder.build());
            });
            helpThread.setName("HelpThread-" + helpThread.hashCode());
            helpThread.setPriority(Thread.NORM_PRIORITY - 1);
            helpThread.start();
            return;
        }

        param.args = Arrays.copyOfRange(args, 2, args.length);
        param.message = event;

        Cmd[] commands = this.commands.toArray(new Cmd[0]);
        Cmd cmd = search(commands, args[1]);

        if (cmd == null)
            return;

        Thread thread = new Thread(threadGroup, () -> {// Async thread
            // Execute the command; exceptions are thrown in separate thread, so they won't crash the bot (pls still catch 'em yourself)
            cmd.cmd.execute(param);
        }, "CommandThread-" + cmd.props.name() + '-' + cmd.cmd.hashCode());
        /* Thread priority is set lower than usual,
         *   because the main thread is important
         */
        thread.setPriority(Thread.NORM_PRIORITY - 1);
        thread.start();
    }

    // Gets a command from an array of commands based on its name
    private @Nullable Cmd search(Cmd @NotNull [] commands, String command) {
        return Arrays.stream(commands)
                .filter(
                        c -> c.props.name()
                                .equalsIgnoreCase(command) || (
                                c.props.aliases().length != 0 && Arrays.stream(c.props.aliases()).anyMatch(a -> a.equalsIgnoreCase(command))
                        )
                )
                .findFirst()
                .orElse(null);
    }

    private static class Cmd {
        public final BotCommand cmd;
        public final Command props;

        public Cmd(@NotNull BotCommand cmd, @NotNull Command props) {
            this.cmd = cmd;
            this.props = props;
        }
    }
}
