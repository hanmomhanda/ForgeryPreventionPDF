import org.apache.pdfbox.pdmodel.PDDocument;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

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

    public static void main(String[] args) {

        PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
        System.out.println("print service : " + printService.getName());

//        String origString = "����ü";
//        String s1 = new String(origString.getBytes("MS949"), "UTF-8");
//        System.out.println(s1);
//        String s2 = new String(origString.getBytes("ISO-8859-1"), "UTF-8");
//        System.out.println(s2);
//        String s3 = new String(origString.getBytes("UTF-8"), "ISO-8859-1");
//        System.out.println(s3);
//        String s4 = new String(origString.getBytes("EUC-KR"), "MS949");
//        System.out.println(s4);
//        String s5 = new String(origString.getBytes("UTF-8"), "EUC-KR");
//        System.out.println(s5);
//        String s6 = new String(s3.getBytes("ISO-8859-1"), "UTF-8");
//        System.out.println(s6);
//        String s7 = new String(s3.getBytes("UTF-8"), "ISO-8859-1");
//        System.out.println(s7);


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
        PDDocument pdDocument = null;
        for (File item: files) {
System.out.println(item);

            try{
//                InputStream is = new FileInputStream(item);

//            PDFParser pdfParser = new PDFParser(new RandomAccessBufferedFileInputStream(item));
//            pdfParser.parse();
//            PDDocument pdDocument = pdfParser.getPDDocument();

                pdDocument = PDDocument.load(item);


//            PDDocumentCatalog documentCatalog = pdDocument.getDocumentCatalog();
//            PDAcroForm acroForm = documentCatalog.getAcroForm();
//            PDResources res = acroForm.getDefaultResources();
//            if (res == null){
//                res = new PDResources();
//            }
//            File fontFile = new File("src/gulim.ttc");
//            TrueTypeCollection trueTypeCollection = new TrueTypeCollection(fontFile);
//            PDFont font = PDType0Font.load(pdDocument, trueTypeCollection.getFontByName("Gulim"), true);
//            COSName name = res.add(font);
//            acroForm.setDefaultResources(res);


                MyPDFRenderer pdfRenderer = new PDPageMyPDFRenderer();
//            MyPDFRenderer pdfRenderer = new ImageRenderer();
                pdfRenderer.render(printService, pdDocument);
            } catch(IOException e) {
                System.err.println(e.getMessage());
                throw new RuntimeException("File load error");
            } catch(Exception e) {
                System.err.println(e.getCause().getMessage());
                throw new RuntimeException("Exception");
            } finally {
                if (pdDocument != null) {
                    try {
                        pdDocument.close();
                    } catch (IOException e) {}
                }
            }
        }
    }
}


