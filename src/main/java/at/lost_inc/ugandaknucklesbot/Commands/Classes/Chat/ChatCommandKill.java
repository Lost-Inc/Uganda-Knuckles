package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Random;


@Command(
        name = "kill",
        help = "Kills a person (why is the FBI here?!?)",
        categories = {
                BotCommand.ICategories.FUN,
                BotCommand.ICategories.CHAT,
        }
)
public final class ChatCommandKill extends BotCommand {
    private UtilsChat utilsChat;
    private Random rand;

    @Override
    public void onPostInitialization() {
        utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
        rand = ServiceManager.provideUnchecked(Random.class);
    }

    private final String[] sentences = new String[] {
            "%s died by getting killed, cringe!",
            "%s was to christian",
            "%s expired, because they were an anti-vaxxer",
            "%s was accused of anti-soviet behavior, and sentenced by the court to be shot",
            "%s was a WW1-Soldier in Verdun",
            "%s thinks, justin bieber is good, and got killed by people with a brain",
            "%s was a software tester for testing bombs",
            "%s was a jedi during Order 66",
            "%s got Rick-Rolled",
            "%s danced 'till their dead",
            "%s watched to many pool-streams",
            "%s got caught by the anti-horny department",
            "%s subscribed to r/dankmemes",
            "%s logged on to 2b2t",
            "%s downloaded free nitro for discord",
            "%s existed on Twitter",
            "%s visited 4chan",
            "%s used the internet after 25 years",
            "%s downloaded free_vbucks.exe",
            "%s downloaded free_robucks_" + OffsetDateTime.now().getYear() + ".exe",
            "%s used his brain against himself",
            "%s got stoned in the middleages",
            "%s found a dragon",
            "%s was killed by ur mum",
            "%s got so toxic, he poisoned himself",
            "%s was called by his step-dad",
            "%s tried out the fitnessgram pacer test",
            "%s hated his life",
            "%s got the ride of his life",
            "%s was revolutionized by the French",
            "%s hated on someone's taste",
            "%s hated minorities and was canceled on Twitter",
    };

    @Override
    public void execute(@NotNull CommandParameter param) {
        final MessageChannel channel = param.message.getChannel();

        if(param.args.length == 0 || !utilsChat.isMention(param.args[0])) {
            utilsChat.sendInfo(channel, String.format(
                    sentences[rand.nextInt(sentences.length)],
                    Objects.requireNonNull(param.message.getMember()).getEffectiveName()
            ));
            return;
        }
        final Member member = utilsChat.getMemberByMention(param.args[0], param.message.getGuild());

        if(member == null) {
            utilsChat.sendInfo(channel, "This user does not exist, like your dad!");
            return;
        }

        utilsChat.sendInfo(channel, String.format(
                sentences[rand.nextInt(sentences.length)],
                member.getEffectiveName()
        ));
    }
}
