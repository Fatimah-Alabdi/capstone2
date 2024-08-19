package com.example.intercity_travel_system.Service;

import com.example.intercity_travel_system.Api.ApiException;
import com.example.intercity_travel_system.Model.Booking;
import com.example.intercity_travel_system.Model.User;
import com.example.intercity_travel_system.Model.VehicleOffer;
import com.example.intercity_travel_system.Repository.BookingRepository;
import com.example.intercity_travel_system.Repository.UserRepository;
import com.example.intercity_travel_system.Repository.VehicleOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final VehicleOfferRepository vehicleOfferRepository;
    private final UserRepository userRepository;

    public List<Booking> findAll(){
        return bookingRepository.findAll();
    }
    public void bookTrip(Booking booking){
        User u= userRepository.findUserById(booking.getUserId());
        if(u==null){
            throw new ApiException("user not found");
        }
        if(u.getRole().equalsIgnoreCase("driver")){
            throw new ApiException("the passenger can book only ");
        }
        VehicleOffer v=vehicleOfferRepository.findVehicleOfferById(booking.getVehicleOffer_id());
        if(v==null){
            throw new ApiException("vehicleOffer not found");
        }
        if (v.getAvailableSeats() < booking.getSeatsBooked()) {
            throw new ApiException("Not enough seats available");
        }
        int totalCost = booking.getSeatsBooked() * v.getPricePerSeat();
        if (u.getBalance() < totalCost) {
            throw new ApiException("Insufficient balance to book the trip");
        }
        if(booking.getBookingDateTime().after(v.getDepartureDateTime())){
            throw new ApiException("the date must be before the departure date");
        }
        v.setAvailableSeats(v.getAvailableSeats() - booking.getSeatsBooked());
        vehicleOfferRepository.save(v);


        u.setBalance(u.getBalance() - totalCost);

        User driver = userRepository.findUserById(v.getUserId());
        if (driver != null && driver.getRole().equalsIgnoreCase("driver")) {
            driver.setBalance(driver.getBalance() + totalCost);
            userRepository.save(driver);
        }
        userRepository.save(u);

        bookingRepository.save(booking);

    }
    public void updateBooking(Integer id,Booking booking){
        User u= userRepository.findUserById(booking.getUserId());
        if(u==null){
            throw new ApiException("user not found");
        }

        VehicleOffer v=vehicleOfferRepository.findVehicleOfferById(booking.getVehicleOffer_id());
        if(v==null){
            throw new ApiException("vehicleOffer not found");
        }
        Booking b=bookingRepository.findBookingById(id);
        if(b==null){
            throw new ApiException("booking not found");
        }
        b.setSeatsBooked(booking.getSeatsBooked());
        b.setBookingDateTime(booking.getBookingDateTime());
        b.setUserId(booking.getUserId());
        b.setVehicleOffer_id(b.getVehicleOffer_id());
        b.setPaymentStatus(booking.getPaymentStatus());
        b.setTripStatus(booking.getTripStatus());
        bookingRepository.save(b);
    }
    public void cancelBooking(Integer userId,Integer bookId){


        User u= userRepository.findUserById(userId);
        if(u==null){
            throw new ApiException("user not found");
        }
        Booking b=bookingRepository.findBookingById(bookId);
        if(b==null){
            throw new ApiException("booking not found");
        }
        VehicleOffer vehicleOffer = vehicleOfferRepository.findVehicleOfferById(b.getVehicleOffer_id());
        if(vehicleOffer==null){
            throw new ApiException("vehicleOffer not found");
        }
        if(b.getTripStatus().equalsIgnoreCase("arrived")||b.getPaymentStatus().equalsIgnoreCase("completed")){
            throw new ApiException("cannot cancel trip after arrived or payment completed");
        }
        vehicleOffer.setAvailableSeats(vehicleOffer.getAvailableSeats() + b.getSeatsBooked());
        vehicleOfferRepository.save(vehicleOffer);
        int totalRefund = b.getSeatsBooked() * vehicleOffer.getPricePerSeat();
        u.setBalance(u.getBalance() + totalRefund);

        User driver = userRepository.findUserById(vehicleOffer.getUserId());
        if (driver != null && driver.getRole().equalsIgnoreCase("driver")) {
            driver.setBalance(driver.getBalance() - totalRefund);
            userRepository.save(driver);
        }
        userRepository.save(u);
        b.setTripStatus("canceled");
        b.setPaymentStatus("refunded");
        bookingRepository.save(b);

        //bookingRepository.delete(b);

    }
    public Booking getBookingDetails(Integer bookingId) {
        Booking b= bookingRepository.findBookingById(bookingId);
        if(b==null){
            throw new ApiException("booking not found");
        }
        return b;


    }
    public void updatePaymentStatus(Integer bookingId,Integer userId, String paymentStatus) {

        if (!paymentStatus.equals("completed") && !paymentStatus.equals("not completed")) {
            throw new ApiException("Invalid payment status. Must be 'completed' or 'not completed'.");
        }
        User u= userRepository.findUserById(userId);


        if (!u.getRole().equalsIgnoreCase("driver")) {
            throw new ApiException("You do not have permission to update the payment status.");
        }


        Booking booking = bookingRepository.findBookingById(bookingId);
                if(booking== null){
                    throw new ApiException("booking not found");
                }


        booking.setPaymentStatus(paymentStatus);
        bookingRepository.save(booking);
    }
    public List<Booking> getActiveTripsForUser(Integer userId) {

        return bookingRepository.findActiveTripsByUserId(userId);
    }

    public void updateBookingStatus(Integer bookingId,Integer userId, String tripStatus) {

        Booking b = bookingRepository.findBookingById(bookingId);
        if (b == null) {
            throw new ApiException("booking not found");
        }


        VehicleOffer vehicleOffer = vehicleOfferRepository.findVehicleOfferById(b.getVehicleOffer_id());
        if (vehicleOffer == null) {

            throw new ApiException("vehicleOffer not found");
        }


        User u = userRepository.findUserById(userId);
                if (u == null) {
                    throw new ApiException("user not found");
                }

        if (!u.getRole().equalsIgnoreCase("driver")) {
            throw new ApiException("Only drivers can update booking status.");
        }
        if(b.getTripStatus().equalsIgnoreCase("arrived")){
            throw new ApiException("trip already arrived can update trip status.");
        }
        if (!vehicleOffer.getUserId().equals(userId)) {
            throw new ApiException("Unauthorized: Only the driver for this trip can update the status.");
        }

        b.setTripStatus(tripStatus);
        bookingRepository.save(b);
    }

}
