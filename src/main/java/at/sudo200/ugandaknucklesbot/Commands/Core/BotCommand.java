package at.sudo200.ugandaknucklesbot.Commands.Core;

// abstract class which gets inherited
// by every command class
public abstract class BotCommand {
    protected abstract String setName();
    protected abstract void execute(CommandParameter param);
}
