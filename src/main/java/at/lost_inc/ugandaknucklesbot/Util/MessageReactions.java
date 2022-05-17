package at.lost_inc.ugandaknucklesbot.Util;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class MessageReactions extends ListenerAdapter{
        @Override
        public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
            final Message retrmsg = event.retrieveMessage().complete();
            if (event.getReactionEmote().getEmoji().equals("📌")) {
                if(!retrmsg.isPinned())
                    retrmsg.pin().complete();
                else
                    retrmsg.unpin().complete();
                event.getReaction().removeReaction(event.getUser()).complete();
                return;
            }
            if (event.getReactionEmote().getEmoji().equals("\uD83D\uDDD1️")){
                if(event.getMember().hasPermission(Permission.MESSAGE_MANAGE)){
                    retrmsg.clearReactions().complete();
                    //retrmsg.delete().complete();
                }
                return;
            }
            if (event.getReactionEmote().getEmoji().equals("\u2705")){
                retrmsg.addReaction("\u274C").complete();
                retrmsg.addReaction("\u2705").complete();
                return;
            }
            if ((event.getMember().getIdLong() == event.getJDA().getSelfUser().getIdLong()) || (retrmsg.getReactions().size() > 1))
                return;

            String[] numbers = {"1\uFE0F\u20E3", "2\uFE0F\u20E3", "3\uFE0F\u20E3", "4\uFE0F\u20E3", "5\uFE0F\u20E3",
                                "6\uFE0F\u20E3", "7\uFE0F\u20E3", "8\uFE0F\u20E3", "9\uFE0F\u20E3"};
            if (!Arrays.toString(numbers).contains(event.getReactionEmote().getEmoji()))
                return;
            event.getReaction().removeReaction(event.getUser()).complete();
            for (String number : numbers) {
                retrmsg.addReaction(number).complete();
                if (event.getReactionEmote().getEmoji().equals(number)) {
                    return;
                }
            }
        }

        @Override
        public void onGuildMessageReactionRemove(@NotNull GuildMessageReactionRemoveEvent event) {

        }
}

