package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.Event.EventListenerService;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.Author;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Author("sudo200")
@Command(
        name = "poll",
        help = "Makes a poll\n\n" +
                "Use ~ as a delimiting character between options",
        categories = {
                BotCommand.ICategories.FUN,
                BotCommand.ICategories.CHAT
        }
)

public final class ChatCommandPoll extends BotCommand {
    private UtilsChat utilsChat;

    @Override
    public void onPostInitialization() {
        utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);
        ServiceManager.provideUnchecked(EventListenerService.class).registerListener(new ReactionListener());
    }

    @Override
    public void execute(@NotNull CommandParameter param) {
        final String[] reactions = {
                "U+0031 U+FE0F U+20E3",
                "U+0032 U+FE0F U+20E3",
                "U+0033 U+FE0F U+20E3",
                "U+0034 U+FE0F U+20E3",
                "U+0035 U+FE0F U+20E3",
                "U+0036 U+FE0F U+20E3",
                "U+0037 U+FE0F U+20E3",
                "U+0038 U+FE0F U+20E3",
                "U+0039 U+FE0F U+20E3",
                "U+0030 U+FE0F U+20E3"
        };
        final String[] emojis = {
                ":one:",
                ":two:",
                ":three:",
                ":four:",
                ":five:",
                ":six:",
                ":seven:",
                ":eight:",
                ":nine:",
                ":zero:"
        };

        if (param.args.length == 0) {
            utilsChat.sendInfo(param.message.getChannel(), "What poll do you want to start?");
            return;
        }

        final AtomicInteger x = new AtomicInteger();
        int y = 1;
        for (int i = 0; i < param.args.length; i++) {
            if (param.args[i].contains("~"))
                x.set(++y);
        }
        if (x.get() > 10) {
            x.set(10);
            utilsChat.sendInfo(param.message.getChannel(), "Sorry, but you can only make up to ten options!");
        }
        EmbedBuilder pollembed = new EmbedBuilder()
                .setTitle("Poll");

        for (int i = 0; i < x.get(); i++) {
            if (i == 0)
                pollembed.appendDescription(emojis[i] + " " + String.join(" ", param.args).split("~")[i] + "\n");
            else
                pollembed.appendDescription(emojis[i] + String.join(" ", param.args).split("~")[i] + "\n");
        }

        final Message msg = utilsChat.send(param.message.getChannel(), pollembed.build());
        for (int i = 0; i < x.get(); i++)
            msg.addReaction(reactions[i]).complete();

        param.message.delete().complete();


    }

    private static class ReactionListener extends ListenerAdapter {
        @Override
        public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {

        }

        @Override
        public void onGuildMessageReactionRemove(@NotNull GuildMessageReactionRemoveEvent event) {

        }
    }
}
