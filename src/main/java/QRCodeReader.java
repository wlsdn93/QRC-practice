import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QRCodeReader {

    public static void createQR(String data, String path, String charset,
                                int height, int width, Map<EncodeHintType, ErrorCorrectionLevel> hint)
            throws WriterException, IOException {

        BitMatrix matrix = new MultiFormatWriter()
                .encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height, hint);

        MatrixToImageWriter.writeToPath(matrix, "PNG", new File(path).toPath());
    }

    // Function to read the QR file
    public static String readQRCode(String path) throws IOException, NotFoundException {
        BinaryBitmap binaryBitmap
                = new BinaryBitmap(
                        new HybridBinarizer(
                                new BufferedImageLuminanceSource(
                                        ImageIO.read(new FileInputStream(path)))));

        return new MultiFormatReader().decode(binaryBitmap).getText();
    }

    // Driver code
    public static void main(String[] args) throws WriterException, IOException, NotFoundException {

        // The data that the QR code will contain
        String data = "hello222";

        // The path where the image will get saved
        String path = "src/main/resources/sample.png";

        // Encoding charset
        String charset = "UTF-8";

        Map<EncodeHintType, ErrorCorrectionLevel> hint = new HashMap<>();
        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        createQR(data, path, charset, 185, 185, hint);

        System.out.println(
                "QRCode output: "
                        + readQRCode(path));
    }
}
