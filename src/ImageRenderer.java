import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Sides;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

/**
 * Created by hanmomhanda on 2015-12-21.
 */
public class ImageRenderer implements MyPDFRenderer {
    @Override
    public void render(PrintService printService, PDDocument pdDocument) throws Exception {

        DocFlavor docFlavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        aset.add(Sides.ONE_SIDED);
//        Attribute attr = new DateTimeAtProcessing(new Date());
//        System.out.println(attr instanceof Attribute);
//        aset.add(attr);

        PDFRenderer renderer = new PDFRenderer(pdDocument);


        final BufferedImage image = renderer.renderImage(0);
        Graphics2D g  = (Graphics2D)image.getGraphics();
        g.drawString("333", 300, 600);

//        PDFImageWriter imageWriter = new PDFImageWriter();
//        imageWriter.writeImage(pdDocument, "jpg", "", 1, Integer.MAX_VALUE, "gen");

        SimpleDoc doc = new SimpleDoc(new Printable() {
            Image printImage = image;
            @Override
            public int print(Graphics graphics, PageFormat pf, int pageIndex) throws PrinterException {
                Graphics2D g2d = (Graphics2D) graphics;
                graphics.translate((int) (pf.getImageableX()), (int) (pf.getImageableY()));
                if (pageIndex == 0) {
                    double pageWidth = pf.getImageableWidth();
                    double pageHeight = pf.getImageableHeight();
                    double imageWidth = printImage.getWidth(null);
                    double imageHeight = printImage.getHeight(null);
                    double scaleX = pageWidth / imageWidth;
                    double scaleY = pageHeight / imageHeight;
                    double scaleFactor = Math.min(scaleX, scaleY);
                    g2d.scale(scaleFactor, scaleFactor);
                    graphics.drawImage(printImage, 0, 0, null);
                    return Printable.PAGE_EXISTS;
                }
                return Printable.NO_SUCH_PAGE;
            }
        }, docFlavor, null);
        DocPrintJob job = printService.createPrintJob();
        job.print(doc, null);


//        DocPrintJob docPrintJob = printService.createPrintJob();
//        docPrintJob.addPrintJobListener(new JobCompleteMonitor());
//        Doc doc = new SimpleDoc(is, docFlavor, null);
//        docPrintJob.print(doc, null);
//        while(jobRunning){
//            Thread.sleep(1000);
//        }
        System.out.println("Terminated");
    }
}
