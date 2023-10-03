package klg.task.service.generator;

import klg.task.dtos.report.ReportBookingCountGuestPriceDto;
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

    public void generatePdfBookingTerm(List<ReportBookingTermDto> reportData, String outputPath) throws IOException {

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 700);

            for (ReportBookingTermDto dto : reportData) {

                contentStream.showText("Nazwa obiektu: " + dto.getApartment());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Ilosc dni rezerwacji: " + dto.getDays());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Ile razy obiekt byl wynajmowany " + dto.getBookingCount());
                contentStream.newLineAtOffset(0, -20);
            }

            contentStream.endText();
        }

        document.save("C:/PdfBox/booking_term_report.pdf");
        document.close();
    }

    public void generatePdfBookingCountGuestPrice(List<ReportBookingCountGuestPriceDto> reportData, String outputPath)
        throws IOException {

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 700);

            for (ReportBookingCountGuestPriceDto dto : reportData) {

                contentStream.showText("Nazwa wlasciciela obiektu: " + dto.getLandlordName());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Ilosc zarezerwowanych obiektow: " + dto.getApartmentCount());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Ilosc gosci " + dto.getTenantCount());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Zysk " + dto.getPrice());
                contentStream.newLineAtOffset(0, -20);
            }

            contentStream.endText();
        }

        document.save("C:/PdfBox/booking_count_report.pdf");
        document.close();
    }
}

