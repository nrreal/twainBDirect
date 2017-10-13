package nrreal.projects.twainbdirect.bosh;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author theboshy
 */
public class ImageManager {

    public static BufferedImage convertBuferredImageType(BufferedImage src, int bufImgType) {
        BufferedImage img = null;
        img = new BufferedImage(src.getWidth(), src.getHeight(), bufImgType);
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(src, 0, 0, null);
        g2d.dispose();
        return img;
    }

    public static void convertWriteToPdf(BufferedImage bufeBufferedImage, String path) {
        try {
            //Image img = Image.getInstance("C:\\Users\\SOFTWARE1\\Desktop\\boshtwain4JImages\\testcapture1507134499431.jpg");
            Image img = Image.getInstance(bufeBufferedImage, null);
            Document document = new Document(img);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
            //--
            document.open();
            img.setAbsolutePosition(0, 0);
            //--
            document.add(img);
            //--
            document.close();
        } catch (DocumentException | IOException e) {
            System.out.println("Intern Log : " + e.getMessage());
        }
    }

    public static void convertWriteToMultiplePdf(List<BufferedImage> bufeBufferedImage, String path) {
        List<Image> images;
        try {

            images = new ArrayList<>();

            for (BufferedImage bufferedImage : bufeBufferedImage) {
                images.add(Image.getInstance(bufferedImage, null));
            }
            
            Document document = new Document(images.get(0));
            
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
            //--
            document.open();
            images.get(0).setAbsolutePosition(0, 0);
            //--
            for (Image image : images) {
                document.newPage();
                document.add(image);
            }
            //--
            document.close();
        } catch (Exception e) {
            System.out.println("Intern Log : " + e.getMessage());
        }
    }

}
