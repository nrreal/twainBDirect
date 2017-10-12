package nrreal.projects.bosh.twainbdirect;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import javax.imageio.ImageIO;
import uk.co.mmscomputing.device.scanner.ScannerDevice;
import uk.co.mmscomputing.device.scanner.ScannerIOMetadata;
import uk.co.mmscomputing.device.scanner.ScannerListener;

/**
 *
 * @author theboshy
 */
public class BoshScanListener implements ScannerListener {

    //--
    private static BufferedImage image;
    private static final String PATH = "C:\\Users\\SOFTWARE1\\Desktop\\boshtwain4JImages\\";
    /**
     *
     */
    private static final String OUT_PUT_TYPE = "jpg";
    private static final String OUT_PUT_FILE_NAME = "twain4jOut";
    public static boolean FINISH_ALL_SCAN_PROCCES = false;

    public BoshScanListener() {
    }

    @Override
    public void update(ScannerIOMetadata.Type type, ScannerIOMetadata metadata) {
        if (type.equals(ScannerIOMetadata.ACQUIRED)) {
            System.out.println("Acquired ejec");
            try {
                File fileOutputFolder = new File(PATH + OUT_PUT_FILE_NAME + generateNewId() + "." + OUT_PUT_TYPE);
                //--
                Path path;
                path = Paths.get(fileOutputFolder.getCanonicalPath());
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }
                //--
                image = metadata.getImage();
                System.out.println("image type : " + image.getType());
                System.out.println("Have an image now!");

                //TYPE_INT_RGB = jpg
                image = convertBuferredImageType(image, BufferedImage.TYPE_INT_RGB);
                //--

                System.out.println("trying to write the image");
                if (!ImageIO.write(image, OUT_PUT_TYPE, fileOutputFolder)) {
                    throw new RuntimeException("Unexpected error writing image");
                }
                System.out.println("image save " + PATH);
                FINISH_ALL_SCAN_PROCCES = true;
            } catch (IOException e) {
                System.out.println("Error : " + e.getMessage());
            }
        } else if (type.equals(ScannerIOMetadata.NEGOTIATE)) {
            //--
            System.out.println("Negotiate ejec");
            //--
            ScannerDevice device = metadata.getDevice();
            try {
//        device.setShowUserInterface(true);
//        device.setShowProgressBar(true);
//        device.setResolution(100);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (type.equals(ScannerIOMetadata.STATECHANGE)) {
            //--
            System.out.println("State Change ejec");
            //--
            System.err.println("State : " + metadata.getStateStr());
            if (metadata.isFinished()) {
                System.out.println("Metadata is finished");
                System.exit(0);
            }
        } else if (type.equals(ScannerIOMetadata.EXCEPTION)) {
            System.out.println(metadata.getException().getMessage());
            System.exit(0);
        }
    }

    private static BufferedImage convertBuferredImageType(BufferedImage src, int bufImgType) {
        System.out.println("try to convert file to jpg");
        BufferedImage img = new BufferedImage(src.getWidth(), src.getHeight(), bufImgType);
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(src, 0, 0, null);
        g2d.dispose();
        System.out.println("convert finish");
        return img;
    }

    private String generateNewId() {
        UUID uniqueId = UUID.randomUUID();
        return String.valueOf(uniqueId);
    }
}
