package com.example.intercity_travel_system.Controller;

import com.example.intercity_travel_system.Api.ApiResponse;
import com.example.intercity_travel_system.Model.Booking;
import com.example.intercity_travel_system.Model.VehicleOffer;
import com.example.intercity_travel_system.Service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    @GetMapping("/get")
    public ResponseEntity getAllBooking(){
        return ResponseEntity.status(200).body(bookingService.findAll());
    }
    @PostMapping("/book")
    public ResponseEntity bookTrip(@Valid @RequestBody Booking booking, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
         bookingService.bookTrip(booking);
return ResponseEntity.status(200).body(new ApiResponse("trip booked successfully"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updatebooking(@PathVariable Integer id,@Valid @RequestBody Booking booking, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        bookingService.updateBooking(id,booking);
        return ResponseEntity.status(200).body(new ApiResponse("booking updated successfully"));
    }
    @DeleteMapping("/cancel/{userId}/{bookId}")
    public ResponseEntity cancelbooking(@PathVariable Integer userId,@PathVariable Integer bookId){
        bookingService.cancelBooking(userId,bookId);
        return ResponseEntity.status(200).body(new ApiResponse("booking deleted successfully"));
    }
    @GetMapping("/trip_details/{bookId}")
    public ResponseEntity getBookingDetails(@PathVariable Integer bookId){
        return ResponseEntity.status(200).body(bookingService.getBookingDetails(bookId));
    }
    @PutMapping("/payment/{bookingId}/{userId}/{paymentStatus}")
    public ResponseEntity updatePaymentStatus(
            @PathVariable Integer bookingId,
            @PathVariable Integer userId,
            @PathVariable String paymentStatus
    ) {

            bookingService.updatePaymentStatus(bookingId,userId,paymentStatus);
            return ResponseEntity.status(200).body(new ApiResponse("Payment status updated successfully"));

    }
    @GetMapping("/activ_trip/{userId}")
    public ResponseEntity getActiveTripsForUser(@PathVariable Integer userId){
        List<Booking> activeTrips = bookingService.getActiveTripsForUser(userId);
        return ResponseEntity.status(200).body(activeTrips);
    }
    @PutMapping("/trip_status/{bookingId}/{userId}/{tripStatus}")
    public ResponseEntity updateTripStatus(@PathVariable Integer bookingId,@PathVariable Integer userId,@PathVariable String tripStatus){
        bookingService.updateBookingStatus(bookingId,userId,tripStatus);
        return ResponseEntity.status(200).body(new ApiResponse("trip status updated successfully"));
    }


}
