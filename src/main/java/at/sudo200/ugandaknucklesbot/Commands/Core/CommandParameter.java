package at.sudo200.ugandaknucklesbot.Commands.Core;

import net.dv8tion.jda.api.entities.Message;

// This object gets passed to the command classes.
// The params are wrapped in an object
// because additions of new params
// won't break stuff :).
// Make sure, that they are public
public class CommandParameter {
    public Message message;
    public String[] args;
}
