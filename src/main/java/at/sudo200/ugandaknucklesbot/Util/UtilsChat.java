package at.sudo200.ugandaknucklesbot.Util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.utils.AttachmentOption;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;

// Class containing utility methods
public class UtilsChat {

    // Sends a string, embed or file into a channel
    public void send(@NotNull MessageChannel channel, String message) {
        channel.sendMessage(message).queue();
    }
    public void send(@NotNull MessageChannel channel, MessageEmbed embed) {
        channel.sendMessage(embed).queue();
    }
    public void send(@NotNull MessageChannel channel, File file, AttachmentOption ...options) {
        channel.sendFile(file, options).queue();
    }

    // Method for sending fancy replies
    public void sendInfo(MessageChannel channel, String message) {
        EmbedBuilder builder = this.getDefaultEmbed();
        builder.setDescription(message);
        channel.sendMessage(builder.build()).queue();
    }

    // Returns an embed with default properties already set
    public EmbedBuilder getDefaultEmbed() {
        return (new EmbedBuilder()).setColor(Color.RED);
    }

    // gets a member from a mention
    public Member getMemberByMention(@NotNull String mention, @NotNull Guild guild) {
        String temp = mention.trim().substring(mention.contains("!") ? 3 : 2);
        String id = temp.substring(0, temp.length() - 1);
        return guild.getMemberById(id);
    }

    // returns true, if string is a valid user mention
    public boolean isMention(@NotNull String q) {
        return q.matches("^<@!?[0-9]{18}>$");
    }
}
