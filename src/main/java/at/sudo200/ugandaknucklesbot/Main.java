package at.sudo200.ugandaknucklesbot;

import at.sudo200.ugandaknucklesbot.Commands.Classes.Chat.*;
import at.sudo200.ugandaknucklesbot.Commands.Classes.Voice.VoiceCommandLeave;
import at.sudo200.ugandaknucklesbot.Commands.Classes.Voice.VoiceCommandPlay;
import at.sudo200.ugandaknucklesbot.Commands.Classes.Voice.VoiceCommandSync;
import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandHandler;
import at.sudo200.ugandaknucklesbot.listeners.MessageReceiveListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Collection;

/*
 *  TODO:
 *  - [x] Add moderation category
 *  - [ ] Add permission restriction to moderation commands
 *  - [ ] Add QR command (embed string in QR code)
 *  - [ ] Add version command (print out package- and jvm version)
 *  - [ ] Add queue command (print out current audio track queue)
 *  - [ ] Add stop command (destroy audio player, leave bot in channel)
 *  - [ ] Add pause command (pause audio player)
 *  - [ ] Add resume command (resume audio player)
 *  - [ ] Fix typos and similar
 */

public class Main {
    private final Collection<GatewayIntent> gatewayIntents = new ArrayList<>();
    private final JDA jda;
    private final CommandHandler handler;

    private Main() throws LoginException { // Token retrieved from Environment
        this.gatewayIntents.add(GatewayIntent.GUILD_MEMBERS);
        this.jda = JDABuilder.createDefault(System.getenv("DISCORDBOTTOKEN"))
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
        } catch (LoginException e) {
            e.printStackTrace();
            return;
        }
        // Listeners
        main.jda.addEventListener(new MessageReceiveListener());

        // Set presence
        main.jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.listening("@" + main.jda.getSelfUser().getName()), false);

        // Register your Commands here
        BotCommand[] commands = {
                // Chat commands
                new ChatCommandInator(),
                new ChatCommandLenny(),
                new ChatCommandAvatar(),
                new ChatCommandDNSLookup(),
                new ChatCommandUrban(),
                new ChatCommandRandomCat(),
                new ChatCommandPoop(),
                new ChatCommandDice(),
                new ChatCommandRandomDog(),
                new ChatCommandFood(),
                new ChatCommandPing(),
                new ChatCommandGetMinecraftSkin(),
                new ChatCommandUselessWeb(),
                new ChatCommandWiki(),
                new ChatCommandClear(),
                new ChatCommandKick(),
                new ChatCommandBan(),
                // Voice commands
                new VoiceCommandPlay(),
                new VoiceCommandSync(),
                new VoiceCommandLeave(),
        };

        main.handler.register(commands);
    }
}
