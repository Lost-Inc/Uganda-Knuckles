package at.lost_inc.ugandaknucklesbot.Util;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.api.requests.restaction.pagination.ReactionPaginationAction;
import org.apache.commons.collections4.Bag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.OffsetDateTime;
import java.util.EnumSet;
import java.util.Formatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Author("sudo200")
public class SlashCommandMessageAdapter implements Message {
    protected final SlashCommandEvent event;

    public SlashCommandMessageAdapter(SlashCommandEvent event) {
        this.event = event;
    }

    @Nullable
    @Override
    public Message getReferencedMessage() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public List<User> getMentionedUsers() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public Bag<User> getMentionedUsersBag() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public List<TextChannel> getMentionedChannels() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public Bag<TextChannel> getMentionedChannelsBag() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public List<Role> getMentionedRoles() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public Bag<Role> getMentionedRolesBag() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public List<Member> getMentionedMembers(@NotNull Guild guild) {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public List<Member> getMentionedMembers() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public List<IMentionable> getMentions(@NotNull MentionType @NotNull ... types) {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @Override
    public boolean isMentioned(@NotNull IMentionable mentionable, @NotNull MentionType @NotNull ... types) {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @Override
    public boolean mentionsEveryone() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @Override
    public boolean isEdited() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @Nullable
    @Override
    public OffsetDateTime getTimeEdited() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public User getAuthor() {
        return event.getUser();
    }

    @Nullable
    @Override
    public Member getMember() {
        return event.getMember();
    }

    @NotNull
    @Override
    public String getJumpUrl() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public String getContentDisplay() {
        return getContentRaw();
    }

    @NotNull
    @Override
    public String getContentRaw() {
        return String.join(
                " ",
                event.getName(),
                event.getOptions().stream()
                        .map(OptionMapping::getAsString)
                        .collect(Collectors.joining(" "))
        );
    }

    @NotNull
    @Override
    public String getContentStripped() {
        return getContentRaw();
    }

    @NotNull
    @Override
    public List<String> getInvites() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @Nullable
    @Override
    public String getNonce() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @Override
    public boolean isFromType(@NotNull ChannelType type) {
        return event.getChannel().getType().equals(type);
    }

    @NotNull
    @Override
    public ChannelType getChannelType() {
        return event.getChannelType();
    }

    @Override
    public boolean isWebhookMessage() {
        return false;
    }

    @NotNull
    @Override
    public MessageChannel getChannel() {
        return event.getChannel();
    }

    @NotNull
    @Override
    public PrivateChannel getPrivateChannel() {
        return event.getPrivateChannel();
    }

    @NotNull
    @Override
    public TextChannel getTextChannel() {
        return event.getTextChannel();
    }

    @Nullable
    @Override
    public Category getCategory() {
        final TextChannel channel = event.getTextChannel();
        return Objects.requireNonNull(
                        event.getGuild()
                )
                .getCategories()
                .stream()
                .filter(
                        category -> category.getTextChannels()
                                .stream()
                                .anyMatch(textChannel -> textChannel.equals(channel))
                )
                .findFirst()
                .orElse(null);
    }


    @Override
    public @NotNull Guild getGuild() {
        return Objects.requireNonNull(event.getGuild());
    }

    @NotNull
    @Override
    public List<Attachment> getAttachments() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public List<MessageEmbed> getEmbeds() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public List<ActionRow> getActionRows() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public List<Emote> getEmotes() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public Bag<Emote> getEmotesBag() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public List<MessageReaction> getReactions() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public List<MessageSticker> getStickers() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @Override
    public boolean isTTS() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @Nullable
    @Override
    public MessageActivity getActivity() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public MessageAction editMessage(@NotNull CharSequence newContent) {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public MessageAction editMessage(@NotNull MessageEmbed newContent) {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public MessageAction editMessageFormat(@NotNull String format, @NotNull Object @NotNull ... args) {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public MessageAction editMessage(@NotNull Message newContent) {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public AuditableRestAction<Void> delete() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public JDA getJDA() {
        return event.getJDA();
    }

    @Override
    public boolean isPinned() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public RestAction<Void> pin() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public RestAction<Void> unpin() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public RestAction<Void> addReaction(@NotNull Emote emote) {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public RestAction<Void> addReaction(@NotNull String unicode) {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public RestAction<Void> clearReactions() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public RestAction<Void> clearReactions(@NotNull String unicode) {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public RestAction<Void> clearReactions(@NotNull Emote emote) {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public RestAction<Void> removeReaction(@NotNull Emote emote) {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public RestAction<Void> removeReaction(@NotNull Emote emote, @NotNull User user) {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public RestAction<Void> removeReaction(@NotNull String unicode) {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public RestAction<Void> removeReaction(@NotNull String unicode, @NotNull User user) {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public ReactionPaginationAction retrieveReactionUsers(@NotNull Emote emote) {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public ReactionPaginationAction retrieveReactionUsers(@NotNull String unicode) {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @Nullable
    @Override
    public MessageReaction.ReactionEmote getReactionByUnicode(@NotNull String unicode) {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @Nullable
    @Override
    public MessageReaction.ReactionEmote getReactionById(@NotNull String id) {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @Nullable
    @Override
    public MessageReaction.ReactionEmote getReactionById(long id) {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public AuditableRestAction<Void> suppressEmbeds(boolean suppressed) {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public RestAction<Message> crosspost() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @Override
    public boolean isSuppressedEmbeds() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public EnumSet<MessageFlag> getFlags() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @NotNull
    @Override
    public MessageType getType() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @Override
    public void formatTo(Formatter formatter, int i, int i1, int i2) {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }

    @Override
    public long getIdLong() {
        throw new UnsupportedOperationException("Message adapted from slash command!");
    }
}
