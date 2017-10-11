package nrreal.projects.twain4j.test;

import extern.bosh.twain4j.BoshScanListener;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import uk.co.mmscomputing.device.scanner.Scanner;
import uk.co.mmscomputing.device.scanner.ScannerDevice;
import uk.co.mmscomputing.device.scanner.ScannerIOException;
import uk.co.mmscomputing.device.scanner.ScannerIOMetadata;
import uk.co.mmscomputing.device.scanner.ScannerListener;
import javax.swing.WindowConstants;

/**
 * @author theboshy
 */
public class ScannerInterfaceTest extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JButton select = null;
    private JButton scan = null;
    private Scanner scanner;

    public static void main(String[] args) {
        new ScannerInterfaceTest().setVisible(true);
    }

    public void run(String arg0) {

        new ScannerInterfaceTest().setVisible(true);
    }

    public ScannerInterfaceTest() {
        super();
        initialize();
        try {
            scanner = Scanner.getDevice();
            BoshScanListener boshScanListener = new BoshScanListener();
            scanner.addListener(boshScanListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialize() {
        this.setSize(300, 120);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setContentPane(getJContentPane());
        this.setTitle("Scan");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.add(getJButton(), null);
            jContentPane.add(getJButton1(), null);
        }
        return jContentPane;
    }

    private JButton getJButton() {
        if (select == null) {
            select = new JButton();
            select.setBounds(new Rectangle(4, 16, 131, 42));
            select.setText("Select Device");
            select.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    selectDevice();
                }
            });
        }
        return select;
    }

    private JButton getJButton1() {
        if (scan == null) {
            scan = new JButton();
            scan.setBounds(new Rectangle(160, 16, 131, 42));
            scan.setText("Scan");
            scan.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {

                    getScan();

                }
            });
        }
        return scan;
    }

    public void selectDevice() {

        try {
            scanner.select();
        } catch (ScannerIOException e1) {
            System.err.println(e1.toString());
        }

    }

    public void getScan() {
        try {
            scanner.acquire();
        } catch (ScannerIOException e1) {
            System.err.println("Acceso denegado" + e1.getMessage());
        }

    }

}
