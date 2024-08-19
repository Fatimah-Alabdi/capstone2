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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BookingRepository bookingRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final VehicleOfferRepository vehicleOfferRepository;

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }
    public void addUser(User user) {
        userRepository.save(user);
    }
    public void update(Integer id,User user)  {
        User u=userRepository.findUserById(id);
        if(u == null) {
            throw new ApiException("no user found");
        }

        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        u.setEmail(user.getEmail());
        u.setPhone(user.getPhone());
        u.setBalance(user.getBalance());
        u.setRole(user.getRole());
        userRepository.save(u);

    }
    public void delete(Integer id)  {
        User u=userRepository.findUserById(id);
        if(u == null) {
            throw new ApiException("no user found");
        }
        userRepository.delete(u);
    }
    public List<Booking> getUserBookings(Integer userId) {
        return bookingRepository.findBookingByUserId(userId);
    }

    public List<Review> getUserReviews(Integer userId) {
        return reviewRepository.findReviewByUserId(userId);
    }
    public List<Object> getUserTripHistory(Integer userId) {
        List<Object> tripHistory = new ArrayList<>();
        tripHistory.addAll(bookingRepository.findBookingByUserId(userId));
        tripHistory.addAll(reviewRepository.findReviewByUserId(userId));
        return tripHistory;
    }
    public double getDriverRating(Integer vid) {
        VehicleOffer v= vehicleOfferRepository.findVehicleOfferById(vid);
        if(v==null) {
            throw new ApiException("no VehicleOffer found");
        }

        List<Review> reviews = reviewRepository.findReviewByVehicleOfferId(vid);
        if(reviews.isEmpty()) {
            throw new ApiException("No ratings available for this  Vehicle offer driver");
        }
        int totalRating = 0;
        for (Review review : reviews) {
            totalRating += review.getRating();
        }

        double averageRating = (double) totalRating / reviews.size();

        return averageRating;

    }
}
