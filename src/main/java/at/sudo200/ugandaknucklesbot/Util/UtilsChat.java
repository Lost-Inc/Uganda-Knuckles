package at.sudo200.ugandaknucklesbot.Util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

// Class containing utility methods
public class UtilsChat {

    // Sends a string or embed into a channel
    public void send(MessageChannel channel, String message) {
        channel.sendMessage(message).queue();
    }
    public void send(MessageChannel channel, MessageEmbed embed) {
        channel.sendMessage(embed).queue();
    }

    // Returns an embed with default properties already set
    public EmbedBuilder getDefaultEmbed() {
        return (new EmbedBuilder()).setColor(Color.RED);
    }
}
