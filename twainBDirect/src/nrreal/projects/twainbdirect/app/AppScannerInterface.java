package nrreal.projects.twainbdirect.app;

import nrreal.projects.bosh.twainbdirect.BoshScan;
import nrreal.projects.bosh.twainbdirect.BoshScanListener;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import uk.co.mmscomputing.device.scanner.Scanner;
import uk.co.mmscomputing.device.scanner.ScannerIOException;
import javax.swing.WindowConstants;

/**
 * @author theboshy
 */
public class AppScannerInterface extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JButton select = null;
    private JButton scan = null;
    private Scanner scanner;

    public static void main(String[] args) {
        new AppScannerInterface().setVisible(true);
    }

    public void run(String arg0) {
        new AppScannerInterface().setVisible(true);
    }

    public AppScannerInterface() {
        super();
        initialize();
        try {
            scanner = Scanner.getDevice();
            //--
            if (BoshScan.verifyAndInitComponets(scanner)) {
                BoshScanListener boshScanListener = new BoshScanListener();
                scanner.addListener(boshScanListener);
            } else {
                JOptionPane.showMessageDialog(null, "No cumple con los requerimientos necesarios para \nusar twain4J", "Error", JOptionPane.ERROR_MESSAGE);
            }
            //--
        } catch (HeadlessException e) {
            System.out.println(e.getMessage());
        }
    }

    private void initialize() {
        this.setSize(300, 120);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setContentPane(contenedorJPane());
        this.setTitle("Escanear");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private JPanel contenedorJPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.add(selectButton(), null);
            jContentPane.add(scanButton(), null);
        }
        return jContentPane;
    }

    private JButton selectButton() {
        if (select == null) {
            select = new JButton();
            select.setBounds(new Rectangle(4, 16, 131, 42));
            select.setText("Dispositivos");
            select.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    selectDevice();
                }
            });
        }
        return select;
    }

    private JButton scanButton() {
        if (scan == null) {
            scan = new JButton();
            scan.setBounds(new Rectangle(160, 16, 131, 42));
            scan.setText("Scan");
            scan.addActionListener(new java.awt.event.ActionListener() {
                @Override
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
