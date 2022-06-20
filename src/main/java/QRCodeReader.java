import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.detector.Detector;

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
    // point 는 QR Code 의를 구분하는 finder pattern 의 위치를 의미한다.
    public static void getPoint(String path) throws IOException, NotFoundException, FormatException {
        Detector detector =
                new Detector(
                        new HybridBinarizer(
                                new BufferedImageLuminanceSource(
                                        ImageIO.read(new FileInputStream(path)))).getBlackMatrix());
        DetectorResult detectorResult = detector.detect();
        for (ResultPoint point : detectorResult.getPoints()) {
            System.out.println(point);
        }
    }

    // Driver code
    public static void main(String[] args) throws WriterException, IOException, NotFoundException, FormatException {

        // The data that the QR code will contain
        String data = "hello222";

        // The path where the image will get saved
        String path = "src/main/resources/qrcode-with-text.png";

        // Encoding charset
        String charset = "UTF-8";

        Map<EncodeHintType, ErrorCorrectionLevel> hint = new HashMap<>();
        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

//        createQR(data, path, charset, 185, 185, hint);
//
//        System.out.println(
//                "QRCode output: "
//                        + readQRCode(path));
//
        getPoint(path);
    }
}
