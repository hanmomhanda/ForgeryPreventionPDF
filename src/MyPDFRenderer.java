import org.apache.pdfbox.pdmodel.PDDocument;

import javax.print.PrintService;

/**
 * Created by hanmomhanda on 2015-12-21.
 */
public interface MyPDFRenderer {
    void render(PrintService printService, PDDocument pdDocument) throws Exception;
}
