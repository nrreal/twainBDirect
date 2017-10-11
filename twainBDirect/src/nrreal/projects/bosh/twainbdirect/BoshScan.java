package nrreal.projects.bosh.twainbdirect;

import java.awt.Graphics2D;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

import uk.co.mmscomputing.device.scanner.Scanner;
import uk.co.mmscomputing.device.scanner.ScannerDevice;
import uk.co.mmscomputing.device.scanner.ScannerListener;
import uk.co.mmscomputing.device.scanner.ScannerIOException;
import uk.co.mmscomputing.device.scanner.ScannerIOMetadata;

public class BoshScan {

    private static BoshScan app;
    private static Scanner scanner;
    private static BoshScanListener scannerListener;

    public BoshScan() {
    }

    public boolean initScanApp() {
        scanner = Scanner.getDevice();
        //--
        if (scanner != null) {
            waitToBeginProcces(scanner);
        }
        //--
        scannerListener = new BoshScanListener();
        scanner.addListener(scannerListener);
        //--
        return verifyAndInitComponets(scanner);
    }

    public static boolean verifyAndInitComponets(Scanner innerScannerComp) {
        try {
            if (SystemProperties.verifyJvmArch().equals("JVM_x32")) {
                //-
                if (innerScannerComp != null) {
                    //--
                    if (innerScannerComp.isAPIInstalled()) {
                        innerScannerComp.setCancel(false);
                        //--
                        return true;
                        /*
                        if (scanner.getDeviceNames().length >= 1) {
                            //selecciona el primer scanner encontrado
                            scanner.select(scanner.getDeviceNames()[0]);
                            return true;
                        }*/
                    }
                }
            }
        } catch (ScannerIOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void initScanAcquire() throws ScannerIOException {
        try {
            scanner.acquire();
            //--
            waitToStopProcces();
        } catch (ScannerIOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void waitToBeginProcces(Scanner scanner) {
        try {
            while (scanner.isBusy()) {
                Thread.currentThread().sleep(200);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void waitToStopProcces() {
        try {
            while (!BoshScanListener.FINISH_ALL_SCAN_PROCCES) {                                            // make sure program waits for scanner to finish!
                Thread.currentThread().sleep(20);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

}
