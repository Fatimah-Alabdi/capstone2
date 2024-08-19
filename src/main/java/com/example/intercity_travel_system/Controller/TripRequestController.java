package com.example.intercity_travel_system.Controller;

import com.example.intercity_travel_system.Api.ApiResponse;
import com.example.intercity_travel_system.Model.Booking;
import com.example.intercity_travel_system.Model.TripRequest;
import com.example.intercity_travel_system.Service.TripRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/Trip_request")
@RequiredArgsConstructor
public class TripRequestController {
    private final TripRequestService tripRequestService;
    @GetMapping("/get")
    public ResponseEntity getAllTripRequest(){
        return ResponseEntity.status(200).body(tripRequestService.getAllTripRequest());
    }
    @PostMapping("/add")
    public ResponseEntity addTripRequest(@Valid@RequestBody TripRequest tripRequest, Errors errors){
        if(errors.hasErrors()){
           String errorMessage = errors.getFieldError().getDefaultMessage();
           return ResponseEntity.status(400).body(errorMessage);
        }
        tripRequestService.addTripRequest(tripRequest);
        return ResponseEntity.status(200).body(new ApiResponse("Trip Request Added Successfully"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateTripRequest(@PathVariable Integer id,@Valid@RequestBody TripRequest tripRequest, Errors errors){
        if(errors.hasErrors()){
            String errorMessage = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(errorMessage);
        }
        tripRequestService.updateTripRequest(id,tripRequest);
        return ResponseEntity.status(200).body(new ApiResponse("Trip Request Updated Successfully"));

    }
    @DeleteMapping("/delete/{id}/{userId}")
    public ResponseEntity deleteTripRequest(@PathVariable Integer id,@PathVariable Integer userId){
        tripRequestService.deleteTripRequest(id,userId);
        return ResponseEntity.status(200).body(new ApiResponse("Trip Request Deleted Successfully"));
    }

    @GetMapping("/get_requist_by_filter")
    public ResponseEntity getTripRequestsByFilters(

            @RequestParam String departureStation, @RequestParam String arrivalStation,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date departureTime,@RequestParam int numberOfSeats  ) {

        List<TripRequest> tripRequests = tripRequestService.getTriPRequstByFilters(departureStation, arrivalStation,departureTime,numberOfSeats);
        return ResponseEntity.status(200).body(tripRequests);
    }
    @PutMapping("/fulfill/{requestId}")
    public ResponseEntity markTripRequestAsFulfilled(@PathVariable Integer requestId) {

            tripRequestService.markTripRequestAsFulfilled(requestId);
            return ResponseEntity.status(200).body(new ApiResponse("Trip request marked as fulfilled successfully."));

    }


}
