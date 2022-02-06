package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.Core.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.function.Consumer;

public final class ChatCommandQRCode extends BotCommand {
    private final Random random = ServiceManager.provideUnchecked(Random.class);

    private final UtilsChat utilsChat = ServiceManager.provideUnchecked(UtilsChat.class);

    @Override
    protected String @NotNull [] getCategories() {
        return new String[]{
                BotCommand.ICategories.IMAGE,
                BotCommand.ICategories.CHAT, BotCommand.ICategories.UTIL, BotCommand.ICategories.FUN
        };
    }

    @Override
    protected @NotNull String getName() {
        return "qr";
    }

    @Override
    protected @NotNull String getHelp() {
        return "Generates a QR code with your string inside it!\n\nWhy? Because links are boring!";
    }

    @Override
    protected void execute(@NotNull CommandParameter param) {
        if (param.args.length == 0) {
            utilsChat.sendInfo(param.message.getChannel(), "Please mate, tell me __what__ to encode!\n\nLosin' my hope....");
            return;
        }

        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix;

        try {
            matrix = writer.encode(String.join(" ", param.args), BarcodeFormat.QR_CODE, 512, 512);
        } catch (WriterException e) {
            utilsChat.sendInfo(param.message.getChannel(), "Oof!\n\nThe QR code factory blew up!");
            return;
        }
        BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);
        File temp;

        try {
            temp = File.createTempFile(Integer.toString(random.nextInt(899) + 100), ".png");
            ImageIO.write(image, "png", temp);
        } catch (IOException e) {
            utilsChat.sendInfo(param.message.getChannel(), "Oof!\n\nCould not package your QR code into a file!");
            return;
        }

        Consumer<?> cb = t -> temp.delete();
        utilsChat.send((Consumer<? super Message>) cb, (Consumer<? super Throwable>) cb, param.message.getChannel(), temp);
    }
}
