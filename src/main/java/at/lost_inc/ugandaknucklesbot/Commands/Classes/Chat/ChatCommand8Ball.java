package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public final class ChatCommand8Ball extends BotCommand {
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
    private final Random rand = ServiceManager.provideUnchecked(Random.class);
    private static final String[] answers = new String[] {
            "Why not??",
            "Dude, hell no!",
            "Sorry, the 8ball is currently not in reach!\n" +
                    "Call back later!",
            "yeeeeeeeeee!!!",
            "nooooooooooo!!!!",
            "I dunno, why ask me something like that?",
            "You know the answer yourself, do you?",
            "F@*~ no!!!!",
            "yeeeesssss, of courseeee!",
            "I mean, why not? Yes!",
            "uganda.knuckles.8BallException: NOOOOO!!!!!!!",
            "Leave me alone!",
            "When you are stupid, no",
            "I'm an 8Ball, not a trash ball!",
            "The Illuminauti are pleased, yes",
            "Senpai says no",
            "Senpai says yes",
            "Yes",
            "No",
            "Not based, so, yes",
            "Is it from facebook? nooo!!!",
            "NOOOOOOOOOOOOOOOOOOOO:registered:",
            "Cancel culture says no",
            "Wut? I dunno, let's go with .... ||yes||",
            "Wut? I dunno, let's go with .... ||  no||",
            "java.lang.NullPointerException: Caused by:\nYEEEEEEEEEEEEEEEEEEEEEEEEEEEE!!!!",
            "java.lang.NullPointerException: Caused by:\nNOOOOOOOOOOOOOOOOOO!!!!",
            "The real question is, do we live in a communist society?",
            "The real question is, do we live in the matrix?",
            "The real question is, why are you asking me this shit?!?",
            "No front, but, yes",
            "No front, but, no",
    };

    @Override
    protected String @Nullable [] getAliases() {
        return null;
    }

    @Override
    protected String @NotNull [] getCategories() {
        return new String[] {
                ICategories.FUN,
                ICategories.CHAT
        };
    }

    @Override
    protected @NotNull String getName() {
        return "8ball";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Ask the magic 8ball a question\n\nif you dare";
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        if(param.args.length == 0) {
            utilsChat.sendInfo(param.message.getChannel(), "**Whats up? Wanna ask somethin'?**");
            return;
        }

        String q = String.join(" ", param.args);
        if(q.length() > 255) {
            utilsChat.sendInfo(param.message.getChannel(), "Sry, the 8ball has no interest in questions longer than 255 characters!");
            return;
        }

        if(!q.endsWith("?"))
            q += '?';

        utilsChat.send(
                param.message.getChannel(),
                utilsChat.getDefaultEmbed()
                        .setTitle(q)
                        .setDescription(answers[rand.nextInt(answers.length)])
                        .build()
                );
    }
}
