package com.example.intercity_travel_system.Service;

import com.example.intercity_travel_system.Api.ApiException;
import com.example.intercity_travel_system.Model.Booking;
import com.example.intercity_travel_system.Model.Review;
import com.example.intercity_travel_system.Model.User;
import com.example.intercity_travel_system.Model.VehicleOffer;
import com.example.intercity_travel_system.Repository.BookingRepository;
import com.example.intercity_travel_system.Repository.ReviewRepository;
import com.example.intercity_travel_system.Repository.UserRepository;
import com.example.intercity_travel_system.Repository.VehicleOfferRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {
    private ReviewRepository reviewRepository;
    private UserRepository userRepository;
    private VehicleOfferRepository vehicleOfferRepository;
    private BookingRepository bookingRepository;

    public List<Review> getAllReview() {
        return reviewRepository.findAll();

    }

    public void addReview(Review review) {
        User u = userRepository.findUserById(review.getUserId());
        if (u == null) {
            throw new ApiException("user not found");
        }
        if(!u.getRole().equalsIgnoreCase("passenger")){
            throw new ApiException("the user who make the review must be a passenger");
        }
        VehicleOffer v = vehicleOfferRepository.findVehicleOfferById(review.getVehicleOfferId());
        if (v == null) {
            throw new ApiException("vehicleOffer not found");
        }
        Booking b = bookingRepository.findBookingById(review.getBookId());
        if (b == null) {
            throw new ApiException("booking not found");
        }
        if (!b.getTripStatus().equalsIgnoreCase("arrived")) {
            throw new ApiException("the  trip status must be arrived so you can make review");
        }
        if(review.getReviewDateTime().before(v.getArrivalTime())){
            throw new ApiException("the review time must be after the arrival time");
        }
        if (!b.getUserId().equals(review.getUserId())) {
            throw new ApiException("Unauthorized: You can only review trips that you have booked");
        }
        reviewRepository.save(review);

    }
    public List<Review> findReviewByVehicleOffer_id(Integer vehicleOfferId) {
       List<Review> r= reviewRepository.findReviewByVehicleOfferId(vehicleOfferId);
       if(r.isEmpty()){
           throw new ApiException("vehicleOffer  review not found");
       }
       return r;
    }
}

