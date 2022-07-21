package at.lost_inc.ugandaknucklesbot.Util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.utils.AttachmentOption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.File;

/**
 * Contains utility methods for interacting through text
 */
public class UtilsChat {
    private final JDA jda;

    public UtilsChat(@NotNull JDA jda) {
        this.jda = jda;
    }

    /**
     * Sends a string into a text channel
     *
     * @param channel {@link MessageChannel} to send message into
     * @param message {@link CharSequence} to send
     * @return The newly created {@link Message}
     * @throws RuntimeException if any error occurs
     */
    public final @NotNull Message send(@NotNull MessageChannel channel, CharSequence message) throws RuntimeException {
        return channel.sendMessage(message).complete();
    }

    /**
     * Sends an embed into a text channel
     *
     * @param channel {@link MessageChannel} to send embed into
     * @param embed   {@link MessageEmbed} to send
     * @return The newly created {@link Message}
     * @throws RuntimeException if any error occurs
     */
    public final @NotNull Message send(@NotNull MessageChannel channel, MessageEmbed embed) throws RuntimeException {
        return channel.sendMessage(embed).complete();
    }

    /**
     * Sends a file with optional attachment options into a text channel
     *
     * @param channel {@link MessageChannel} to send file into
     * @param file    {@link File} to send
     * @param options {@link AttachmentOption}s, can be omitted, if not used
     * @return The newly created {@link Message}
     * @throws RuntimeException if any error occurs
     */
    public final @NotNull Message send(@NotNull MessageChannel channel, File file, @Nullable AttachmentOption... options) throws RuntimeException {
        return channel.sendFile(file, options).complete();
    }

    /**
     * Sends a fancy reply embed into a text channel
     *
     * @param channel {@link MessageChannel} to send embed into
     * @param message {@link CharSequence} to embed
     * @return The newly created {@link Message}
     * @throws RuntimeException if any error occurs
     */
    public final @NotNull Message sendInfo(@NotNull MessageChannel channel, CharSequence message) throws RuntimeException {
        EmbedBuilder builder = getDefaultEmbed();
        builder.setDescription(message);
        return send(channel, builder.build());
    }

    // Returns an embed with default properties already set
    public final @NotNull EmbedBuilder getDefaultEmbed() {
        return (new EmbedBuilder()).setColor(Color.RED);
    }

    // gets a member from a mention
    public final Member getMemberByMention(@NotNull String mention, @NotNull Guild guild) {
        if (!isUserMention(mention))
            throw new UnsupportedOperationException("Not a user mention!");

        String temp = mention.trim().substring(mention.contains("!") ? 3 : 2);
        String id = temp.substring(0, temp.length() - 1);
        return guild.getMemberById(id);
    }

    public final TextChannel getChannelByMention(@NotNull String mention, @NotNull Guild guild) {
        if (!isChannelMention(mention))
            throw new UnsupportedOperationException("Not a channel mention!");

        String temp = mention.trim().substring(mention.contains("!") ? 3 : 2);
        String id = temp.substring(0, temp.length() - 1);
        return guild.getTextChannelById(id);
    }

    // returns true, if string is a valid user mention
    public final boolean isUserMention(@NotNull String q) {
        return q.matches("^<@!?[0-9]{18}>$");
    }

    // returns true, if string is a valid channel mention
    public final boolean isChannelMention(@NotNull String q) {
        return q.matches("^<#!?[0-9]{18}>$");
    }

    public final boolean sameUser(User user1, User user2) {
        return user1 == user2 || (user1 != null && user2 != null && user1.getIdLong() == user2.getIdLong());
    }

    public final boolean isSelf(User user) {
        return user != null && jda.getSelfUser().getIdLong() == user.getIdLong();
    }
}
