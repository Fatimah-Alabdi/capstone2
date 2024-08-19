package com.example.intercity_travel_system.Controller;

import com.example.intercity_travel_system.Api.ApiResponse;
import com.example.intercity_travel_system.Model.TripRequest;
import com.example.intercity_travel_system.Model.VehicleOffer;
import com.example.intercity_travel_system.Service.VehicleOfferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vehicle_offer")
public class VehicleOfferController {
    private final VehicleOfferService vehicleOfferService;

    @GetMapping("/get")
    public ResponseEntity getAllVehicleOffer() {
        return ResponseEntity.status(200).body(vehicleOfferService.findAllVehicleOffer());
    }

    @PostMapping("/add")
    public ResponseEntity addVehicleOffer(@Valid @RequestBody VehicleOffer vehicleOffer, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        vehicleOfferService.addVehicleOffer(vehicleOffer);
        return ResponseEntity.status(201).body(new ApiResponse("offer added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateVehicleOffer(@PathVariable Integer id, @Valid @RequestBody VehicleOffer vehicleOffer, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        vehicleOfferService.updateVehicleOffer(id, vehicleOffer);
        return ResponseEntity.status(201).body(new ApiResponse("offer updated successfully"));
    }

    @DeleteMapping("/delete/{id}/{userId}")
    public ResponseEntity deleteVehicleOffer(@PathVariable Integer id, @PathVariable Integer userId) {
        vehicleOfferService.deleteVehicleOffer(id, userId);
        return ResponseEntity.status(201).body(new ApiResponse("offer deleted successfully"));
    }

    @GetMapping("/search/{departureStation}/{arrivalStation}/{departureDate}/{seatsRequired}")
    public ResponseEntity searchAvailableTrips(
            @PathVariable String departureStation,
            @PathVariable String arrivalStation,
            @PathVariable  Date departureDate,
            @PathVariable int seatsRequired) {
        //@DateTimeFormat(pattern = "yyyy-MM-dd")

        List<VehicleOffer> trips = vehicleOfferService.SearchTrip(departureStation, arrivalStation, departureDate, seatsRequired);
        return ResponseEntity.status(200).body(trips);
    }

    @GetMapping("/seats/{vehicleOfferId}")
    public ResponseEntity viewAvailableSeats(@PathVariable Integer vehicleOfferId) {

        int availableSeats = vehicleOfferService.getAvailableSeats(vehicleOfferId);
        return ResponseEntity.status(200).body(availableSeats);



    }
    @PutMapping("/apply-discount/{vehicleOfferId}/{userId}/{discountPercentage}")
    public ResponseEntity applyDiscount(
            @PathVariable Integer vehicleOfferId,
            @PathVariable Integer userId,@PathVariable int discountPercentage) {

            vehicleOfferService.applyDiscount(vehicleOfferId, userId,discountPercentage);
            return ResponseEntity.status(200).body(new ApiResponse("Discount applied successfully."));

    }
    @GetMapping("/matches/{id}")
    public ResponseEntity<List<VehicleOffer>> findTripMatches(@PathVariable Integer id) {
        List<VehicleOffer> matches = vehicleOfferService.findMatches(id);
        return ResponseEntity.status(200).body(matches);
    }
}