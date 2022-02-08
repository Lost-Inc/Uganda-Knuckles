package at.lost_inc.ugandaknucklesbot;

import at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat.*;
import at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice.*;
import at.lost_inc.ugandaknucklesbot.Commands.Core.Plugins.PluginLoader;
import at.lost_inc.ugandaknucklesbot.Startup.BootCamp;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public final class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private final Collection<GatewayIntent> gatewayIntents = new ArrayList<>();
    private final JDA jda;

    private Main() throws LoginException { // Token retrieved from Environment
        this.gatewayIntents.add(GatewayIntent.GUILD_MEMBERS);
        this.jda = JDABuilder.createDefault(System.getenv("DISCORDBOTTOKEN"))
                .enableIntents(this.gatewayIntents)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .build();
    }

    public static void main(String[] args) throws IOException {
        Main main = null;
        try {
            main = new Main();
        } catch (LoginException e) {
            logger.error("Couldn't log in!", e);
            System.exit(3);
        }

        BootCamp.registerCommands(
                // Chat commands
                new ChatCommandInator(),
                new ChatCommandLenny(),
                new ChatCommandAvatar(),
                new ChatCommandResolve(),
                new ChatCommandUrban(),
                new ChatCommandRandomCat(),
                new ChatCommandPoop(),
                new ChatCommandDice(),
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
                new ChatCommandReddit(),
                new ChatCommand8Ball(),
                new ChatCommandServerStats(),
                new ChatCommandKill(),
                new ChatCommandHangman(),
                new ChatCommandGuess(),
                new ChatCommandPoll(),
                new ChatCommandInsult(),
                new ChatCommandCoinflip(),
                new ChatCommandHash(),
                // Voice commands
                new VoiceCommandPlay(),
                new VoiceCommandPause(),
                new VoiceCommandQueue(),
                new VoiceCommandResume(),
                new VoiceCommandRemove(),
                new VoiceCommandSkip(),
                new VoiceCommandSync(),
                new VoiceCommandStop(),
                new VoiceCommandLoop(),
                new VoiceCommandLeave()
        );

        final File basePath = new File(System.getProperty("user.dir") + File.separator + "plugins");
        if(!basePath.exists())
            basePath.mkdirs();
        BootCamp.registerCommands(
                // Dynamically loaded commands
                new PluginLoader(basePath.toPath()).getCommands()
        );

        BootCamp.initialize(main.jda);
    }
}
