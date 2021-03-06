package at.lost_inc.ugandaknucklesbot.Listeners;

import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandHandler;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public final class SlashCommandEventListener extends ListenerAdapter {
    private final CommandHandler handler = CommandHandler.get();

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        handler.handle(event);
    }
}
