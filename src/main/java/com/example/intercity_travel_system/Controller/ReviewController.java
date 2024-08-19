package com.example.intercity_travel_system.Controller;

import com.example.intercity_travel_system.Api.ApiResponse;
import com.example.intercity_travel_system.Model.Review;
import com.example.intercity_travel_system.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController {
    private final ReviewService reviewService;
    @GetMapping("/get")
    public ResponseEntity getAllReview(){
        return ResponseEntity.status(200).body(reviewService.getAllReview());
    }
    @PostMapping("/add")
    public ResponseEntity addReview(@Valid@RequestBody Review review, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        reviewService.addReview(review);
        return ResponseEntity.status(200).body(new ApiResponse("review added successfully"));

    }
    @GetMapping("/find_review_by_vehicle_offer_id/{vehicleOfferId}")
    public ResponseEntity findReviewByVehicleOffer_id(@PathVariable Integer vehicleOfferId){
        return ResponseEntity.status(200).body(reviewService.findReviewByVehicleOffer_id(vehicleOfferId));
    }
}
