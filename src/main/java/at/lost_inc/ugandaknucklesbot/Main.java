package at.lost_inc.ugandaknucklesbot;

import at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat.*;
import at.lost_inc.ugandaknucklesbot.Commands.Classes.Voice.*;
import at.lost_inc.ugandaknucklesbot.Startup.BootCamp;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) throws IOException {
        final Logger logger = LoggerFactory.getLogger(Main.class);
        final Collection<GatewayIntent> gatewayIntents = new ArrayList<>();
        gatewayIntents.add(GatewayIntent.GUILD_MEMBERS);
        try {
            final JDA jda = JDABuilder.createDefault(System.getenv("DISCORDBOTTOKEN"))
                    .enableIntents(gatewayIntents)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .build();

            BootCamp.registerStaticCommands(
                    // Chat commands
                    ChatCommandInator.class,
                    ChatCommandLenny.class,
                    ChatCommandAvatar.class,
                    ChatCommandResolve.class,
                    ChatCommandUrban.class,
                    ChatCommandRandomCat.class,
                    ChatCommandPoop.class,
                    ChatCommandDice.class,
                    ChatCommandRandomDog.class,
                    ChatCommandFood.class,
                    ChatCommandQRCode.class,
                    ChatCommandPing.class,
                    ChatCommandLeet.class,
                    ChatCommandOwO.class,
                    ChatCommandMCSkin.class,
                    ChatCommandUselessWeb.class,
                    ChatCommandWiki.class,
                    ChatCommandClear.class,
                    ChatCommandKick.class,
                    ChatCommandBan.class,
                    ChatCommandVersion.class,
                    ChatCommandRemind.class,
                    ChatCommandReddit.class,
                    ChatCommand8Ball.class,
                    ChatCommandServerStats.class,
                    ChatCommandKill.class,
                    ChatCommandHangman.class,
                    ChatCommandGuess.class,
                    ChatCommandPoll.class,
                    ChatCommandInsult.class,
                    ChatCommandCoinflip.class,
                    ChatCommandHash.class,
                    // Voice commands
                    VoiceCommandPlay.class,
                    VoiceCommandPause.class,
                    VoiceCommandQueue.class,
                    VoiceCommandResume.class,
                    VoiceCommandRemove.class,
                    VoiceCommandSkip.class,
                    VoiceCommandSync.class,
                    VoiceCommandStop.class,
                    VoiceCommandLoop.class,
                    VoiceCommandLeave.class,
                    VoiceCommandJump.class
            );

            BootCamp.initialize(jda);
        } catch (LoginException e) {
            logger.error("Couldn't log in!", e);
            System.exit(3);
        }
    }
}
