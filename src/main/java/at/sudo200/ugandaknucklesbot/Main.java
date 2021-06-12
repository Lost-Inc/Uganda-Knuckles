package at.sudo200.ugandaknucklesbot;

import at.sudo200.ugandaknucklesbot.Commands.ChatCommandInator;
import at.sudo200.ugandaknucklesbot.Commands.ChatCommandLenny;
import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandHandler;
import at.sudo200.ugandaknucklesbot.listeners.MessageReceiveListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {
    private final JDA jda;
    private final CommandHandler handler;

    private Main() throws LoginException { // Token retrieved from Environment
        this.jda  = JDABuilder.createDefault(System.getenv("DISCORDBOTTOKEN")).build();
        this.handler = CommandHandler.get();
    }

    public static void main(String[] args) {
        Main main;
        try {
            main = new Main();
        }

        catch (LoginException e) {
            e.printStackTrace();
            return;
        }
        // Listeners
        main.jda.addEventListener(new MessageReceiveListener());

        // Register your Commands here
        BotCommand[] commands = {
                new ChatCommandInator(),
                new ChatCommandLenny(),
        };

        main.handler.register(commands);
    }
}
