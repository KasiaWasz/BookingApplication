package klg.task.cotrollers.booking;

import klg.task.service.booking.BookingService;
import klg.task.dtos.BookingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/booking-list")
class BookingListController {

    private static final String P_BOOKING_TENANT_LIST = "/tenant/{name}";
    private static final String P_BOOKING_APARTMENT_LIST = "/apartment/{id}";

    private final BookingService bookingService;


    @Autowired
    private BookingListController(BookingService bookingService) {

        Assert.notNull(bookingService, "bookingService should not be null");

        this.bookingService = bookingService;
    }


    @GetMapping
    private List<BookingDto> getAllBookingsDto(){

        return bookingService.getAllBookingsDto();
    }

    @GetMapping(P_BOOKING_TENANT_LIST)
    private List<Optional<BookingDto>> findBookingDtoByTenantName(@PathVariable String name){

        return bookingService.findBookingDtoByTenantName(name);
    }

    @GetMapping(P_BOOKING_APARTMENT_LIST)
    private List<Optional<BookingDto>> findBookingDtoByApartmentId(@PathVariable Long id){

        return bookingService.findBookingDtoByApartmentId(id);
    }
}
