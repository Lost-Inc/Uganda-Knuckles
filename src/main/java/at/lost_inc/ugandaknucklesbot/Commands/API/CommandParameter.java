package at.lost_inc.ugandaknucklesbot.Commands.API;

import at.lost_inc.ugandaknucklesbot.Util.Author;
import net.dv8tion.jda.api.entities.Message;

/**
 * Object containing the command args and message object
 *
 * @author sudo200
 */
@Author("sudo200")
public final class CommandParameter {
    public Message message;
    public String[] args;
}
