package at.lost_inc.ugandaknucklesbot.Commands.Core;

import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class containing the logic making the bot tick
 *
 * @author sudo200
 */

public final class CommandHandler {
    private static final CommandHandler instance;

    static {
        instance = new CommandHandler();
    }

    private final Collection<BotCommand> commands = new ArrayList<>();
    private final Map<String, Collection<BotCommand>> categories = new HashMap<>();
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);

    private CommandHandler() {
    }

    public static CommandHandler get() {
        return instance;
    }


    private boolean register(@NotNull BotCommand command) {
        for (String categoryName : command.getCategories()) {
            if (!categories.containsKey(categoryName)) {
                categories.put(categoryName, new ArrayList<>());
            }
            Collection<BotCommand> com = categories.get(categoryName);
            if (!com.contains(command))
                com.add(command);
        }
        return this.commands.add(command);
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

    // method called by MessageReceiveListener
    public void handle(@NotNull Message event) {
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

        if (args[1].equalsIgnoreCase("help")) {// help command
            Thread helpThread = new Thread(() -> {
                final EmbedBuilder builder = utilsChat.getDefaultEmbed();
                if (args.length != 3) {// show categories
                    builder.setTitle(":book: Help categories");
                    for (String name : categories.keySet())
                        builder.addField(name, "\t", true);
                    builder.addField("All", "\t", true);
                } else if (args[2].equalsIgnoreCase("all")) {// show all commands
                    builder.setTitle("All commands");
                    for (BotCommand command : commands)
                        builder.addField(
                                command.getName(),
                                command.getHelp() + (command.getAliases() != null ? "\n\n*Aliases:* " + String.join(",", command.getAliases()) : ""),
                                false
                        );
                } else {
                    final String key = categories.keySet().stream().filter(k -> k.matches("(?i).*" + args[2] + ".*")).findFirst().orElse(null);
                    if (key != null) {// show commands from category
                        builder.setTitle(key);
                        for (BotCommand command : categories.get(key))
                            builder.addField(
                                    command.getName(),
                                    command.getHelp() + (command.getAliases() != null ? "\n\n*Aliases:* " + String.join(",", command.getAliases()) : ""),
                                    false
                            );
                    } else {
                        final BotCommand command = commands.stream().filter(c -> c.getName().matches("(?i).*" + args[2] + ".*")).findFirst().orElse(null);
                        if (command != null) {// show help of specific command
                            builder.setTitle("Help for \"" + command.getName() + "\" command");
                            builder.addField(
                                    command.getName(),
                                    command.getHelp() + (command.getAliases() != null ? "\n\n*Aliases:* " + String.join(",", command.getAliases()) : ""),
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

        BotCommand[] commands = this.commands.toArray(new BotCommand[0]);
        BotCommand cmd = search(commands, args[1]);

        if (cmd == null)
            return;

        Thread thread = new Thread(() -> {// Async thread
            // Execute the command; exceptions are thrown in seperate thread, so they won't crash the bot (pls still catch 'em yourself)
            cmd.execute(param);
        }, "CommandThread-" + cmd.getName() + '-' + cmd.hashCode());
        /* Thread priority is set lower than usual,
         *   because the main thread is important
         */
        thread.setPriority(Thread.NORM_PRIORITY - 1);
        thread.start();
    }

    // Gets a command from an array of commands based on its name
    private @Nullable BotCommand search(BotCommand @NotNull [] commands, String command) {
        return Arrays.stream(commands)
                .filter(
                        c -> c.getName()
                                .equalsIgnoreCase(command) || (
                                c.getAliases() != null && Arrays.stream(c.getAliases()).anyMatch(a -> a.equalsIgnoreCase(command))
                        )
                )
                .findFirst()
                .orElse(null);
    }
}
