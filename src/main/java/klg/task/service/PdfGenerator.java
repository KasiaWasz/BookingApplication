package klg.task.service;

import klg.task.dtos.report.ReportBookingTermDto;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PdfGenerator {

    public void generatePdf(List<ReportBookingTermDto> reportData, String outputPath) throws IOException {

        int bookingCount = reportData.size();
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 700);

            contentStream.showText("Ile razy obiekt byl wynajmowany " + bookingCount);
            contentStream.newLineAtOffset(0, -20);

            for (ReportBookingTermDto dto : reportData) {

                contentStream.showText("Nazwa obiektu: " + dto.getApartment());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Ilosc dni rezerwacji: " + dto.getDays());
                contentStream.newLineAtOffset(0, -20);
            }

            contentStream.endText();
        }

        document.save(outputPath);
        document.close();
    }
}

