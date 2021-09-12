package at.sudo200.ugandaknucklesbot.Commands.Classes.Chat;

import at.sudo200.ugandaknucklesbot.Commands.Core.BotCommand;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandCategories;
import at.sudo200.ugandaknucklesbot.Commands.Core.CommandParameter;
import at.sudo200.ugandaknucklesbot.Util.UtilsChat;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ChatCommandQRCode extends BotCommand {
    private final Random random = new Random();

    private final UtilsChat utilsChat = new UtilsChat();

    @Override
    protected String @Nullable [] getAliases() {
        return null;
    }

    @Override
    protected String @NotNull [] getCategories() {
        return new String[] {
                CommandCategories.IMAGE,
                CommandCategories.CHAT, CommandCategories.UTIL, CommandCategories.FUN
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
        if(param.args.length == 0) {
            utilsChat.sendInfo(param.message.getChannel(), "Please mate, tell me __what__ to encode!\n\nLosin' my hope....");
            return;
        }

        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix;

        try {
            matrix = writer.encode(String.join(" ", param.args), BarcodeFormat.QR_CODE, 512, 512);
        }
        catch (WriterException e) {
            utilsChat.sendInfo(param.message.getChannel(), "Oof!\n\nThe QR code factory blew up!");
            return;
        }
        BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);
        File temp;

        try {
            temp = File.createTempFile(Integer.toString(random.nextInt(899) + 100), ".png");
            ImageIO.write(image, "png", temp);
        }
        catch (IOException e) {
            utilsChat.sendInfo(param.message.getChannel(), "Oof!\n\nCould not package your QR code into a file!");
            return;
        }

        param.message.getChannel().sendFile(temp).queue(message -> temp.delete());
    }
}
