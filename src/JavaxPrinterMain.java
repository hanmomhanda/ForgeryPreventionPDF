import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;

/**
 * Created by hanmomhanda on 2015-12-17.
 */
public class JavaxPrinterMain {

    private static boolean jobRunning = true;

    private static class JobCompleteMonitor extends PrintJobAdapter {
        @Override
        public void printJobCompleted(PrintJobEvent pje) {
            System.out.println("Job Completed");
            jobRunning = false;
        }
    }

    public static void main(String[] args) throws Exception {

        PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
        System.out.println("print service : " + printService.getName());

//        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(docFlavor, aset);
//        for (PrintService item : printServices){
//            System.out.println("printerName: " + item.getName());
//        }
//        if (printServices.length == 0){
//            throw new IllegalStateException("No Printer Found");
//        }

        File aDir = new File("src");
        File[] files = aDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.endsWith("pdf")) return true;
                return false;
            }
        });
        for (File item: files) {
System.out.println(item);
            InputStream is = new FileInputStream(item);

            PDFParser pdfParser = new PDFParser(new RandomAccessBufferedFileInputStream(item));
            pdfParser.parse();
            PDDocument pdDocument = pdfParser.getPDDocument();

            MyPDFRenderer pdfRenderer = new PDPageMyPDFRenderer();
//            MyPDFRenderer pdfRenderer = new ImageRenderer();
            pdfRenderer.render(printService, pdDocument);

            pdDocument.close();
            is.close();
        }

    }
}


