package klg.task.cotrollers.booking;

import klg.task.dtos.report.ReportBookingTermDto;
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

    private static final String P_REPORT_DATE = "/{startDate}/{endDate}";

    private final ReportBookingTerm reportBookingTerm;
    private final PdfGenerator pdfGenerator;


    @Autowired
    private ReportController(ReportBookingTerm reportBookingTerm, PdfGenerator pdfGenerator) {

        Assert.notNull(reportBookingTerm, "reportBookingTerm should not be null");
        Assert.notNull(pdfGenerator, "pdfGenerator should not be null");

        this.reportBookingTerm = reportBookingTerm;
        this.pdfGenerator = pdfGenerator;
    }


    @GetMapping(P_REPORT_DATE)
    private void generateReport(@PathVariable String startDate, @PathVariable String endDate) throws IOException {

        LocalDate sDate = LocalDate.parse(startDate);
        LocalDate eDate = LocalDate.parse(endDate);

        List<ReportBookingTermDto> reportData = reportBookingTerm.getBookingTermReport(sDate, eDate);
        pdfGenerator.generatePdf(reportData, "report.pdf");
    }
}
