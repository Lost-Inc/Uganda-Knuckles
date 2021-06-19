package at.sudo200.ugandaknucklesbot.Commands.Core;

import org.jetbrains.annotations.NotNull;

// abstract class which gets inherited
// by every command class
public abstract class BotCommand {
    protected abstract @NotNull String getName();
    protected abstract @NotNull String getHelp();
    protected abstract void execute(CommandParameter param);
}
