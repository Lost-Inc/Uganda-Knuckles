package at.lost_inc.ugandaknucklesbot;

import at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat.*;
import at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice.*;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandHandler;
import at.lost_inc.ugandaknucklesbot.Listeners.MessageReceiveListener;
import at.lost_inc.ugandaknucklesbot.Listeners.SlashCommandEventListener;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
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

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public final class Main {
    private final Collection<GatewayIntent> gatewayIntents = new ArrayList<>();
    private final JDA jda;
    private final CommandHandler handler;

    static {
        // Register Standard Services
        ServiceManager.setProvider(Random.class, new Random());
        ServiceManager.setProvider(UtilsChat.class, new UtilsChat());
        ServiceManager.setProvider(UtilsVoice.class, new UtilsVoice());
        ServiceManager.setProvider(Gson.class, new Gson());
    }

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
        Main main = null;
        try {
            main = new Main();
        } catch (LoginException e) {
            e.printStackTrace();
            System.exit(3);
        }
        // Listeners
        main.jda.addEventListener(new MessageReceiveListener());
        main.jda.addEventListener(new SlashCommandEventListener());

        // Set presence
        main.jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.listening("@" + main.jda.getSelfUser().getName()), false);

        // Register your Commands here
        main.handler.register(
                // Chat commands
                new ChatCommandInator(),
                new ChatCommandLenny(),
                new ChatCommandAvatar(),
                new ChatCommandDNSLookup(),
                new ChatCommandUrban(),
                new ChatCommandRandomCat(),
                new ChatCommandPoop(),
                new ChatCommandDice(),
                new ChatCommandInvite(),
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
                // Voice commands
                new VoiceCommandPlay(),
                new VoiceCommandPause(),
                new VoiceCommandQueue(),
                new VoiceCommandResume(),
                new VoiceCommandSkip(),
                new VoiceCommandSync(),
                new VoiceCommandStop(),
                new VoiceCommandLeave()
        );
    }
}
