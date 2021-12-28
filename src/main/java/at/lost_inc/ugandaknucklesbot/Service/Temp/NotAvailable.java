package at.lost_inc.ugandaknucklesbot.Service.Temp;

import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.function.Consumer;

public final class NotAvailable implements Consumer<MessageChannel> {
    private static final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);

    @Override
    public void accept(MessageChannel channel) {
        utilsChat.sendInfo(channel, "Sorry, but this command is currently not working :(\nSee ya!");
    }
}
