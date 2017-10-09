package extern.ukimplement.jus4test;

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
 *@deprecated 
 * @author theboshy
 */
public class ScannerInterfaceTest extends JFrame implements ScannerListener {

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JButton jButton = null;
    private JButton jButton1 = null;
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
            scanner.addListener(this);
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
        if (jButton == null) {
            jButton = new JButton();
            jButton.setBounds(new Rectangle(4, 16, 131, 42));
            jButton.setText("Select Device");
            jButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                        selectDevice();
                }
            });
        }
        return jButton;
    }

    private JButton getJButton1() {
        if (jButton1 == null) {
            jButton1 = new JButton();
            jButton1.setBounds(new Rectangle(160, 16, 131, 42));
            jButton1.setText("Scan");
            jButton1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {

                    getScan();

                }
            });
        }
        return jButton1;
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
            System.err.println("Acceso denegado"+e1.getMessage());
        }

    }

    @Override
    public void update(ScannerIOMetadata.Type type, ScannerIOMetadata metadata) {

        if (type.equals(ScannerIOMetadata.ACQUIRED)) {
            metadata.setImage(null);
            try {
                new uk.co.mmscomputing.concurrent.Semaphore(0, true).tryAcquire(2000, null);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }

        } else if (type.equals(ScannerIOMetadata.NEGOTIATE)) {
            ScannerDevice device = metadata.getDevice();
            try {
                device.setResolution(100);
            } catch (ScannerIOException e) {
                System.err.println(e.getMessage());
            }
        } else if (type.equals(ScannerIOMetadata.STATECHANGE)) {
        } else if (type.equals(ScannerIOMetadata.EXCEPTION)) {
            System.err.println(metadata.getException().toString());

        }

    }

}
