package com.example.intercity_travel_system.Repository;

import com.example.intercity_travel_system.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {
    List<Review> findReviewByVehicleOfferId(int vehicleOfferId);
    List<Review> findReviewByUserId(int userId);

}
