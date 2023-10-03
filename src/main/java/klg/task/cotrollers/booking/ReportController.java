package klg.task.cotrollers.booking;

import klg.task.dtos.report.ReportBookingCountGuestPriceDto;
import klg.task.dtos.report.ReportBookingTermDto;
import klg.task.service.booking.ReportBookingCountGuestPrice;
import klg.task.service.generator.PdfGenerator;
import klg.task.service.booking.ReportBookingTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/booking-report")
class ReportController {

    private static final String P_REPORT_DATE = "terms/{startDate}/{endDate}";
    private static final String P_REPORT_PRICE_DATE = "price/{startDate}/{endDate}";

    private final ReportBookingTerm reportBookingTerm;
    private final ReportBookingCountGuestPrice reportBookingCountGuestPrice;
    private final PdfGenerator pdfGenerator;


    @Autowired
    private ReportController(ReportBookingTerm reportBookingTerm,
         ReportBookingCountGuestPrice reportBookingCountGuestPrice,
         PdfGenerator pdfGenerator ) {

        Assert.notNull(reportBookingTerm, "reportBookingTerm should not be null");
        Assert.notNull(reportBookingCountGuestPrice, "reportBookingCountGuestPrice should not be null");
        Assert.notNull(pdfGenerator, "pdfGenerator should not be null");

        this.reportBookingTerm = reportBookingTerm;
        this.reportBookingCountGuestPrice = reportBookingCountGuestPrice;
        this.pdfGenerator = pdfGenerator;
    }


/* te dwie metody będą nam generować pliki pdf dostępne na ścieżkach: "C:/PdfBox/booking_term_report.pdf"
    i "C:/PdfBox/booking_count_report.pdf". Można to zrobić tak, aby raporty były wyświetlane w przeglądarce w jsonie,
    wtedy byłoby to lepiej widoczne przy testowaniu w postmanie. Nie wiedziałam na którą wersję się zdecydować,
    ale z racji, że nigdy nie generowałam plików pdf w javie- postawiłam na tę.
    Jezeli chcemy mieć wynik dostępny bezposrednio w postmanie można zrezygnować z generatora i po prostu
    zwrócić dto :)
 */

    @GetMapping(P_REPORT_DATE)
    private void generateReportBookingTerm(@PathVariable String startDate, @PathVariable String endDate) throws IOException {

        LocalDate sDate = LocalDate.parse(startDate);
        LocalDate eDate = LocalDate.parse(endDate);

        List<ReportBookingTermDto> reportData = reportBookingTerm.getBookingTermReport(sDate, eDate);
        pdfGenerator.generatePdfBookingTerm(reportData, "reportBookingTerm.pdf");
    }

    @GetMapping(P_REPORT_PRICE_DATE)
    private void generateReportB(@PathVariable String startDate, @PathVariable String endDate) throws IOException {

        LocalDate sDate = LocalDate.parse(startDate);
        LocalDate eDate = LocalDate.parse(endDate);

        List<ReportBookingCountGuestPriceDto> reportData = reportBookingCountGuestPrice.getBookingCountGuestPriceReport(sDate, eDate);
        pdfGenerator.generatePdfBookingCountGuestPrice(reportData, "reportBookingTerm.pdf");
    }
}
