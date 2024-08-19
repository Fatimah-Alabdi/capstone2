package com.example.intercity_travel_system.Controller;

import com.example.intercity_travel_system.Api.ApiResponse;
import com.example.intercity_travel_system.Model.Booking;
import com.example.intercity_travel_system.Model.Review;
import com.example.intercity_travel_system.Model.User;
import com.example.intercity_travel_system.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    @GetMapping("/get")
    public ResponseEntity getUser() {
        return ResponseEntity.status(200).body(userService.findAll());
    }
    @PostMapping("/add")
    public ResponseEntity addUser(@Valid @RequestBody User user, Errors errors) {
        if (errors.hasErrors()) {
            String errorMessage = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse( "User added successfully"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity UpdateUser(@PathVariable Integer id,@Valid @RequestBody User user, Errors errors)  {
        if (errors.hasErrors()) {
            String errorMessage = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }
        userService.update(id,user);
        return ResponseEntity.status(200).body(new ApiResponse( "User updated successfully"));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable  Integer id)  {
        userService.delete(id);
        return ResponseEntity.status(200).body(new ApiResponse("user delete successfully"));

    }
    @GetMapping("/bookings/{userId}")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable Integer userId) {
        List<Booking> bookings = userService.getUserBookings(userId);
        return ResponseEntity.status(200).body(bookings);
    }

    @GetMapping("/reviews/{userId}")
    public ResponseEntity<List<Review>> getUserReviews(@PathVariable Integer userId) {
        List<Review> reviews = userService.getUserReviews(userId);
        return ResponseEntity.status(200).body(reviews);
    }
    @GetMapping("/trip-history/{userId}")
    public ResponseEntity<List<Object>> getUserTripHistory(@PathVariable Integer userId) {
        List<Object> tripHistory = userService.getUserTripHistory(userId);
        return ResponseEntity.ok(tripHistory);
    }
    @GetMapping("/rating/{vid}")
    public ResponseEntity getDriverRating(@PathVariable Integer vid) {

        double averageRating = userService.getDriverRating(vid);
        return ResponseEntity.status(200).body(averageRating);

    }
}
