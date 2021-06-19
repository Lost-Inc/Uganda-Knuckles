package at.sudo200.ugandaknucklesbot;

import at.sudo200.ugandaknucklesbot.Commands.Classes.Chat.ChatCommandAvatar;
import at.sudo200.ugandaknucklesbot.Commands.Classes.Chat.ChatCommandInator;
import at.sudo200.ugandaknucklesbot.Commands.Classes.Chat.ChatCommandLenny;
import at.sudo200.ugandaknucklesbot.Commands.Classes.Chat.ChatCommandUrban;
import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandHandler;
import at.sudo200.ugandaknucklesbot.Commands.Classes.Voice.VoiceCommandLeave;
import at.sudo200.ugandaknucklesbot.listeners.MessageReceiveListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Collection;

public class Main {
    private final Collection<GatewayIntent> gatewayIntents = new ArrayList<>();
    private final JDA jda;
    private final CommandHandler handler;

    private Main() throws LoginException { // Token retrieved from Environment
        this.gatewayIntents.add(GatewayIntent.GUILD_MEMBERS);
        this.jda  = JDABuilder.createDefault(System.getenv("DISCORDBOTTOKEN"))
                .enableIntents(this.gatewayIntents)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .build();
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
                new ChatCommandAvatar(),
                new ChatCommandUrban(),
                new VoiceCommandLeave(),
        };

        main.handler.register(commands);
    }
}
