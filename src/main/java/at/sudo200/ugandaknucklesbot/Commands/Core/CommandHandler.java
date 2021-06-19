package at.sudo200.ugandaknucklesbot.Commands.Core;

import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

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

    public void handle(MessageReceivedEvent event) {
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

        param.args = Arrays.copyOfRange(args, 2, args.length);
        param.message = event.getMessage();

        BotCommand[] commands = this.commands.toArray(new BotCommand[0]);
        BotCommand cmd = search(commands, args[1].toLowerCase());

        if(cmd == null)
            return;

        try { // Execute the command; exceptions get caught, so the won't crash the bot (pls still catch 'em yourself)
            cmd.execute(param);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Gets a command from an array of commands based on its name
    private BotCommand search(@NotNull BotCommand[] commands, String command) {
        for(BotCommand cmd : commands)
            if(cmd.setName().toLowerCase().equals(command))
                return cmd;
        return null;
    }
}
