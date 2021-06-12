package at.sudo200.ugandaknucklesbot.listeners;

import at.sudo200.ugandaknucklesbot.Commands.Core.CommandHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageReceiveListener extends ListenerAdapter {
    private final CommandHandler handler = CommandHandler.get();

    @Override// Pass all events to the command handler
    public void onMessageReceived(@NotNull MessageReceivedEvent event) { handler.handle(event); }
    }
