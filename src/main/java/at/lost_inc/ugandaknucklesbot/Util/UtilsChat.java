package at.lost_inc.ugandaknucklesbot.Util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.utils.AttachmentOption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.File;
import java.util.function.Consumer;

// Class containing utility methods
public class UtilsChat {

    // Sends a string, embed or file into a channel
    public final void send(@NotNull MessageChannel channel, CharSequence message) {
        send(channel, message, null);
    }

    public final void send(@NotNull MessageChannel channel, CharSequence message, @Nullable Consumer<? super Message> onSuccess) {
        send(channel, message, onSuccess, null);
    }

    public final void send(@NotNull MessageChannel channel, CharSequence message, @Nullable Consumer<? super Message> onSuccess, @Nullable Consumer<? super Throwable> onFailure) {
        channel.sendMessage(message).queue(onSuccess, onFailure);
    }


    public final void send(@NotNull MessageChannel channel, MessageEmbed embed) {
        send(channel, embed, null);
    }

    public final void send(@NotNull MessageChannel channel, MessageEmbed embed, @Nullable Consumer<? super Message> onSuccess) {
        send(channel, embed, onSuccess, null);
    }

    public final void send(@NotNull MessageChannel channel, MessageEmbed embed, @Nullable Consumer<? super Message> onSuccess, @Nullable Consumer<? super Throwable> onFailure) {
        channel.sendMessage(embed).queue(onSuccess, onFailure);
    }


    public final void send(@NotNull MessageChannel channel, File file, @Nullable AttachmentOption... options) {
        send(null, channel, file, options);
    }

    public final void send(@Nullable Consumer<? super Message> onSuccess, @NotNull MessageChannel channel, File file, @Nullable AttachmentOption... options) {
        send(onSuccess, null, channel, file, options);
    }

    public final void send(@Nullable Consumer<? super Message> onSuccess, @Nullable Consumer<? super Throwable> onFailure, @NotNull MessageChannel channel, File file, @Nullable AttachmentOption... options) {
        channel.sendFile(file, options).queue(onSuccess, onFailure);
    }

    // Method for sending fancy replies
    public final void sendInfo(@NotNull MessageChannel channel, CharSequence message) {
        sendInfo(channel, message, null);
    }

    public final void sendInfo(@NotNull MessageChannel channel, CharSequence message, @Nullable Consumer<? super Message> onSuccess) {
        sendInfo(channel, message, onSuccess, null);
    }

    public final void sendInfo(@NotNull MessageChannel channel, CharSequence message, @Nullable Consumer<? super Message> onSuccess, @Nullable Consumer<? super Throwable> onFailure) {
        EmbedBuilder builder = getDefaultEmbed();
        builder.setDescription(message);
        send(channel, builder.build(), onSuccess, onFailure);
    }

    // Returns an embed with default properties already set
    public final @NotNull EmbedBuilder getDefaultEmbed() {
        return (new EmbedBuilder()).setColor(Color.RED);
    }

    // gets a member from a mention
    public final Member getMemberByMention(@NotNull String mention, @NotNull Guild guild) {
        String temp = mention.trim().substring(mention.contains("!") ? 3 : 2);
        String id = temp.substring(0, temp.length() - 1);
        return guild.getMemberById(id);
    }

    // returns true, if string is a valid user mention
    public final boolean isMention(@NotNull String q) {
        return q.matches("^<@!?[0-9]{18}>$");
    }
}
