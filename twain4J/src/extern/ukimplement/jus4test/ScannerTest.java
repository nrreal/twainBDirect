package extern.ukimplement.jus4test;

import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JOptionPane;

import uk.co.mmscomputing.device.scanner.Scanner;
import uk.co.mmscomputing.device.scanner.ScannerDevice;
import uk.co.mmscomputing.device.scanner.ScannerListener;
import uk.co.mmscomputing.device.scanner.ScannerIOException;
import uk.co.mmscomputing.device.scanner.ScannerIOMetadata;

public class ScannerTest implements ScannerListener {

    static ScannerTest app;

    Scanner scanner;

    public ScannerTest(String[] argv) throws ScannerIOException {
        scanner = Scanner.getDevice();
        if (!scanner.isAPIInstalled()) {
            JOptionPane.showMessageDialog(null, "No se encuentra instalado el DMS para TWAIN \n por favor instale twainapp.win[x32?x64].installer.msi", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            scanner.addListener(this);
            /*scanner.select();
        scanner.acquire();*/

            if (scanner == null) {
                System.out.println("no se encontraron recursos de controlador");
            } else {
                JOptionPane.showMessageDialog(null, "Devices : " + scanner.getDeviceNames().length);
                if (scanner.getDeviceNames().length >= 1) {
                    for (String deviceName : scanner.getDeviceNames()) {
                        JOptionPane.showMessageDialog(null, deviceName);
                        //System.out.println(deviceName);
                    }
                }
            }
            scanner.waitToExit();
            //--
        }
    }

    @Override
    public void update(ScannerIOMetadata.Type type, ScannerIOMetadata metadata) {
        if (type.equals(ScannerIOMetadata.ACQUIRED)) {
            //--
            System.out.println("Acquired ejec");
            //--
            BufferedImage image = metadata.getImage();
            System.out.println("Have an image now!");
            try {
                ImageIO.write(image, "png", new File("mmsc_image.png"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
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
                System.exit(0);
            }
        } else if (type.equals(ScannerIOMetadata.EXCEPTION)) {
            System.out.println(metadata.getException().getMessage());
        }
    }

    public static void main(String[] argv) {
        JOptionPane.showMessageDialog(null, "Search Scanner Devices");
        try {
            app = new ScannerTest(argv);
        } catch (ScannerIOException e) {
            System.out.println("Error : "+e.getMessage());
        }
    }
}
