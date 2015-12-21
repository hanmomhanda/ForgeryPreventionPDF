import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.util.Matrix;

import javax.print.PrintService;
import java.awt.print.PrinterJob;

/**
 * Created by hanmomhanda on 2015-12-21.
 */
public class PDPageMyPDFRenderer implements MyPDFRenderer {
    @Override
    public void render(PrintService printService, PDDocument pdDocument) throws Exception {

        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintService(printService);

        PDPageTree allPages = pdDocument.getDocumentCatalog().getPages();
        int length = allPages.getCount();
        PDFont font = PDType1Font.HELVETICA;

        float fontSize = 15.0f;

        for ( int i = 0 ; i < length ; i++ ) {
            PDPage page = (PDPage)allPages.get(i);
            PDRectangle pageSize = page.getMediaBox();
            String strPage = new StringBuilder().append(i+1).append("/").append(length).toString();
            float stringWidth = font.getStringWidth(strPage)*fontSize/1000f;
            float pageWidth = pageSize.getWidth();
            float pageHeight = pageSize.getHeight();
            PDPageContentStream contentStream = new PDPageContentStream(pdDocument, page, true, true, true);
            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            Matrix matrix = new Matrix();
            matrix.translate(pageWidth - stringWidth - 15, pageHeight - 20);
            contentStream.setTextMatrix(matrix);
            contentStream.showText(strPage);
            contentStream.endText();
            contentStream.close();
//                pdDocument.save("PrinterExample/Numbered" + i + ".pdf");
        }
        job.setPageable(new PDFPageable(pdDocument));
        job.print();
    }
}
