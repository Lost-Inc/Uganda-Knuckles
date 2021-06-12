package at.sudo200.ugandaknucklesbot.Commands.Core;

public abstract class BotCommand {
    protected abstract String setName();
    protected abstract void execute(CommandParameter param);
}
