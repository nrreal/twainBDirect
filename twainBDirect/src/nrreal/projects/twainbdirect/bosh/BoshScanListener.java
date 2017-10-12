package nrreal.projects.twainbdirect.bosh;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JOptionPane;
import nrreal.projects.bosh.utils.FatherTree;
import uk.co.mmscomputing.device.scanner.ScannerIOMetadata;
import uk.co.mmscomputing.device.scanner.ScannerListener;

/**
 *
 * @author theboshy
 */
public class BoshScanListener implements ScannerListener {

    private final FatherTree fatherTree;
    //--
    private static BufferedImage image;
    private static final String PATH = System.getProperty("user.home") + "\\Desktop\\" + "boshtwain4JImages\\";
    /**
     *
     */
    private static final String OUT_PUT_TYPE_JPG = "jpg";
    private static final String OUT_PUT_TYPE_PDF = "pdf";
    private static final String OUT_PUT_FILE_NAME = "twain4jOut";
    //--
    public static boolean FINISH_ALL_SCAN_PROCCES = false;
    private String outPutFormat;

    public BoshScanListener() {
        fatherTree = FatherTree.getInstance();
    }

    @Override
    public void update(ScannerIOMetadata.Type type, ScannerIOMetadata metadata) {
        
        if (type.equals(ScannerIOMetadata.ACQUIRED)) {
            File fileOutputFolder;
            try {
                image = metadata.getImage();
                //--
                fileOutputFolder = new File(PATH);

                fatherTree.validateFolder(fileOutputFolder);

                switch (outPutFormat.toLowerCase()) {
                    case OUT_PUT_TYPE_PDF: {
                        System.out.println(OUT_PUT_TYPE_PDF + " ouput");
                        //--
                        ImageManager.convertWriteToPdf(image, fileOutputFolder.getCanonicalPath() + "\\" + OUT_PUT_FILE_NAME + generateNewId() + "." + OUT_PUT_TYPE_PDF);
                        break;
                    }
                    case OUT_PUT_TYPE_JPG: {
                        normalImageFormatWrite(OUT_PUT_TYPE_JPG);
                        break;
                    }
                    default:{
                        System.out.println("Intern Log : la opccion de formato de salida no es correcta");
                        break;
                    }
                }

                FINISH_ALL_SCAN_PROCCES = true;
            } catch (IOException e) {
                FatherTree.createNewLogFile(fatherTree.getCurrentFolder(), e.getMessage());
            }
        } else if (type.equals(ScannerIOMetadata.NEGOTIATE)) {
            /*
            System.out.println("Negotiate ejec");
            ScannerDevice device = metadata.getDevice();
            try {
                device.setShowUserInterface(true);
                device.setShowProgressBar(true);
                device.setResolution(100);
            } catch (ScannerIOException e) {
                System.out.println(e.getMessage());
            }
             */
        } else if (type.equals(ScannerIOMetadata.STATECHANGE)) {
            //System.out.println("State Change ejec");
            //--
            System.err.println("State : " + metadata.getStateStr());
            if (metadata.isFinished()) {
                System.out.println("Metadata is finished");
                System.exit(0);
            }
        } else if (type.equals(ScannerIOMetadata.EXCEPTION)) {
            FatherTree.createNewLogFile(fatherTree.getCurrentFolder(), metadata.getException().getMessage());
            System.exit(0);
        }
    }

    private void normalImageFormatWrite(String type) {
        try {
            System.out.println(type + " ouput");
            //--
            File fileOutputFolder = new File(PATH + OUT_PUT_FILE_NAME + generateNewId() + "." + type);
            //--
            image = ImageManager.convertBuferredImageType(image, BufferedImage.TYPE_INT_RGB);
            //--
            if (image == null) {
                FatherTree.createNewLogFile(fatherTree.getCurrentFolder(), "una de las imagenes escaneadas no pudo ser adquirida");
            } else {
                if (!ImageIO.write(image, type, fileOutputFolder)) {
                    FatherTree.createNewLogFile(fatherTree.getCurrentFolder(), "Ocurrio un error al intentar escribir el archivo");
                }
            }
        } catch (Exception e) {
        }
    }

    private String generateNewId() {
        String uniqueIdString = "";
        try {
            UUID uniqueId = UUID.randomUUID();
            uniqueIdString = String.valueOf(uniqueId);
        } catch (Exception e) {
            FatherTree.createNewLogFile(fatherTree.getCurrentFolder(), e.getMessage());
        }
        return uniqueIdString;
    }

    //--

    public String getOutPutFormat() {
        return outPutFormat;
    }

    public void setOutPutFormat(String outPutFormat) {
        this.outPutFormat = outPutFormat;
    }
    
}
