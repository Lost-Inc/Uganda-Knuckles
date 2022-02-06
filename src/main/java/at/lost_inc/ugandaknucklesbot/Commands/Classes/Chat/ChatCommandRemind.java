package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.Command;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.TimerTaskRunnable;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Timer;


@Command(
        name = "remind",
        help = "Reminds you after a certain time.\n " +
                "Usage:\n" +
                "```\n" +
                "@<Botname> remind <number><s | m | h> [text] [mentions...]\n" +
                "```",
        categories = {
                BotCommand.ICategories.UTIL,
                BotCommand.ICategories.CHAT
        }
)
public final class ChatCommandRemind extends BotCommand {
    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);

    @Override
    protected void execute(@NotNull CommandParameter param) {
        if (param.args.length == 0) {
            utilsChat.sendInfo(param.message.getChannel(), "Have you seen my usage?\n\"" + this.getClass().getAnnotation(Command.class).help() + '"');
            return;
        }

        long time;
        String intime;
        intime = param.args[0];
        intime = intime.substring(0, intime.length() - 1);
        if (param.args[0].endsWith("s"))
            time = Long.parseLong(intime) * 1000;
        else if (param.args[0].endsWith("m"))
            time = Long.parseLong(intime) * 1000 * 60;
        else if (param.args[0].endsWith("h"))
            time = Long.parseLong(intime) * 1000 * 60 * 60;
        else {
            utilsChat.sendInfo(param.message.getChannel(), "Can you give me a real time?");
            return;
        }

        List<User> users = param.message.getMentionedUsers();
        final String delay = param.args[0];

        new Timer(true).schedule(new TimerTaskRunnable(() -> {
            String[] remindtext = param.args;
            remindtext[0] = "";
            EmbedBuilder builder = utilsChat.getDefaultEmbed();
            builder.setTitle("Reminder");

            if (param.args.length == 1)
                utilsChat.send(param.message.getChannel(), "Reminder: " + param.message.getAuthor().getAsMention());

            else {
                for (int i = 1; i < param.args.length; i++) {
                    for (int k = 1; k < users.size(); k++) {
                        remindtext[i] = remindtext[i].replaceAll("(<@!?|&?[0-9]{18}>)", "");
                    }
                }
                builder.setDescription(String.join(" ", remindtext).substring(1));
                utilsChat.send(param.message.getChannel(), param.message.getAuthor().getAsMention());
                utilsChat.sendInfo(
                        param.message.getChannel(),
                        String.format(
                                "Reminder for \n\n```\n%s\n```\nafter `%s`  ",
                                String.join(" ", remindtext).substring(1),
                                delay
                        ));
            }
            User user = param.message.getAuthor();
            user.openPrivateChannel()
                    .flatMap(privateChannel -> privateChannel.sendMessage(builder.build()))
                    .queue();

            for (int i = 1; i < users.size(); i++) {
                utilsChat.send(param.message.getChannel(), users.get(i).getAsMention());

                if (users.get(i).getIdLong() == param.message.getJDA().getSelfUser().getIdLong())
                    continue;

                users.get(i).openPrivateChannel()
                        .flatMap(privateChannel -> privateChannel.sendMessage(builder.build()))
                        .queue();
            }
        }), time);

        utilsChat.sendInfo(param.message.getChannel(), "Reminder saved! :white_check_mark:");
    }
}
