package klg.task.cotrollers.booking;

import klg.task.models.booking.Booking;
import klg.task.service.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/booking-add")
class BookingEditController {

    private static final String M_ADD_FORM = "bookingForm";
    private static final String P_BOOKING_UPDATE = "/update/{id}";

    private final BookingService bookingService;
    private final BookingValidator bookingValidator;


    @Autowired
    private BookingEditController(BookingService bookingService,
        BookingValidator bookingValidator) {

        Assert.notNull(bookingService, "bookingService should not be null");
        Assert.notNull(bookingValidator, "bookingvalidator should not be null");

        this.bookingService = bookingService;
        this.bookingValidator = bookingValidator;
    }

    @InitBinder(M_ADD_FORM)
    private void initBinder(WebDataBinder binder) {

        binder.addValidators(bookingValidator);
    }

    @PostMapping
    private ResponseEntity<Booking> addBooking(@RequestBody @Valid BookingForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            return ResponseEntity.status(400).build();
        }

        bookingService.addNewBooking(form);

        return ResponseEntity.status(201).build();
    }

    //tutaj też warto byłoby dodać walidację, aby nie można było zmienić daty na taką, w której wybrany apartament jest już zarezerwowany
    @PutMapping(P_BOOKING_UPDATE)
    private ResponseEntity<Booking> updateBooking(@PathVariable Long id,
        @RequestBody BookingForm form,
        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            return ResponseEntity.status(400).build();
        }
        bookingService.updateBooking(id, form);

        return ResponseEntity.status(201).build();
    }
}
