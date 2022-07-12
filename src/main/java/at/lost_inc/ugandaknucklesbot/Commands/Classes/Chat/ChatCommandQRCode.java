package at.lost_inc.ugandaknucklesbot.Commands.Classes.Chat;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import at.lost_inc.ugandaknucklesbot.Commands.API.CommandParameter;
import at.lost_inc.ugandaknucklesbot.Commands.API.Inject;
import at.lost_inc.ugandaknucklesbot.Service.ServiceManager;
import at.lost_inc.ugandaknucklesbot.Util.UtilsChat;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

@Command(
        name = "qr",
        help = "Generates a QR code with your string inside it!",
        categories = {
                BotCommand.ICategories.IMAGE,
                BotCommand.ICategories.CHAT, BotCommand.ICategories.UTIL, BotCommand.ICategories.FUN
        }
)
public final class ChatCommandQRCode extends BotCommand {
    @Inject
    private Random random;
    @Inject
    private UtilsChat utilsChat;


    @Override
    public void execute(@NotNull CommandParameter param) {
        if (param.args.length == 0) {
            utilsChat.sendInfo(param.message.getChannel(), "Please append some text for me to encode...");
            return;
        }

        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix;

        try {
            matrix = writer.encode(String.join(" ", param.args), BarcodeFormat.QR_CODE, 512, 512);
        } catch (WriterException e) {
            utilsChat.sendInfo(param.message.getChannel(), "The QR code factory blew up!");
            return;
        }
        BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);
        File temp;

        try {
            temp = File.createTempFile(Integer.toString(random.nextInt(899) + 100), ".png");
            ImageIO.write(image, "png", temp);
        } catch (IOException e) {
            utilsChat.sendInfo(param.message.getChannel(), "Could not package your QR code into a file!");
            return;
        }

        try {
            utilsChat.send(param.message.getChannel(), temp);
        } catch (Throwable ignored) {
        } finally {
            temp.delete();
        }
    }
}
