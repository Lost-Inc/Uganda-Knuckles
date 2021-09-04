package at.sudo200.ugandaknucklesbot.Commands.Core;

import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class CommandHandler {
    private static final CommandHandler instance;
    private final Collection<BotCommand> commands = new ArrayList<>();
    private final UtilsChat utilsChat = new UtilsChat();

    private CommandHandler() {
    }

    static {
        instance = new CommandHandler();
    }

    public static CommandHandler get() {
        return instance;
    }

    // methods for registering commands
    public boolean register(@NotNull BotCommand command) {
        return this.commands.add(command);
    }
    public boolean register(@NotNull BotCommand[] commands) {
        boolean okay = true;
        for (BotCommand command : commands)
            if (!this.commands.add(command))
                okay = false;

            return okay;
    }

    // method called by MessageReceiveListener
    public void handle(@NotNull MessageReceivedEvent event) {
        // Object, that gets passed to the command classes
        CommandParameter param = new CommandParameter();
        String[] args = event
                .getMessage()
                .getContentRaw()
                .trim()
                .split(" ");


        // If not mentioned, ignore
        if(!utilsChat.isMention(args[0])) return;
        if(!utilsChat.getMemberByMention(
                args[0], event.getGuild()).equals(
                        event.getGuild().getMemberById(
                                event.getJDA().getSelfUser().getId()
                        )
                )
        ) return;
        if(args.length == 1) return;

        BotCommand[] commands = this.commands.toArray(new BotCommand[0]);

        if(args[1].equalsIgnoreCase("help")) {// help command
            EmbedBuilder builder = utilsChat.getDefaultEmbed();
            for(BotCommand cmd : commands)
                builder.addField("**" + cmd.getName() + "**", cmd.getHelp(), false);
            utilsChat.send(event.getChannel(), builder.build());
            return;
        }

        param.args = Arrays.copyOfRange(args, 2, args.length);
        param.message = event.getMessage();

        BotCommand cmd = search(commands, args[1].toLowerCase());

        if(cmd == null)
            return;

        Thread thread = new Thread(() -> {// Async thread
            try { // Execute the command; exceptions get caught, so the won't crash the bot (pls still catch 'em yourself)
                cmd.execute(param);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
        /* Thread priority is set lower than usual,
        *   because the main thread is important
        */
        thread.setPriority(Thread.NORM_PRIORITY - 1);
        thread.start();
    }

    // Gets a command from an array of commands based on its name
    private @Nullable BotCommand search(@NotNull BotCommand[] commands, String command) {
        for(BotCommand cmd : commands)
            if(cmd.getName().toLowerCase().equals(command))
                return cmd;
        return null;
    }
}
