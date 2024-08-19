package com.example.intercity_travel_system.Service;

import com.example.intercity_travel_system.Api.ApiException;
import com.example.intercity_travel_system.Model.Booking;
import com.example.intercity_travel_system.Model.TripRequest;
import com.example.intercity_travel_system.Model.User;
import com.example.intercity_travel_system.Model.VehicleOffer;
import com.example.intercity_travel_system.Repository.BookingRepository;
import com.example.intercity_travel_system.Repository.TripRequestRepository;
import com.example.intercity_travel_system.Repository.UserRepository;
import com.example.intercity_travel_system.Repository.VehicleOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripRequestService {
    private final TripRequestRepository tripRequestRepository;
    private BookingRepository bookingRepository;
    private VehicleOfferRepository vehicleOfferRepository;
    private final UserRepository userRepository;

    public List<TripRequest> getAllTripRequest() {
        return tripRequestRepository.findAll();
    }

    public void addTripRequest(TripRequest tripRequest) {
        User u = userRepository.findUserById(tripRequest.getUserId());
        if (u == null) {
            throw new ApiException("user not found");
        }
        if (!u.getRole().equalsIgnoreCase("passenger")) {
            throw new ApiException(" the user that want to make trip request must be a passenger");
        }
        if(tripRequest.getDepartureTime().after(tripRequest.getArrivalTime())){
            throw new ApiException("the departure time cannot be after the arrival time");
        }
        tripRequestRepository.save(tripRequest);

    }

    public void updateTripRequest(Integer id, TripRequest tripRequest) {
        User u = userRepository.findUserById(tripRequest.getUserId());
        if (u == null) {
            throw new ApiException("user not found");

        }
        if (!u.getRole().equalsIgnoreCase("passenger")) {
            throw new ApiException(" the user that want to update trip request must be a passenger");
        }
        TripRequest tripRequest1 = tripRequestRepository.findTripRequestById(id);
        if (tripRequest1 == null) {
            throw new ApiException("trip request not found");
        }
        tripRequest.setArrivalStation(tripRequest.getArrivalStation());
        tripRequest.setDepartureStation(tripRequest.getDepartureStation());
        //tripRequest.setArrivalTime(tripRequest.getArrivalTime());
        tripRequest.setDepartureTime(tripRequest.getDepartureTime());
        tripRequest.setNumberOfSeats(tripRequest.getNumberOfSeats());
        tripRequest.setUserId(tripRequest.getUserId());
        tripRequestRepository.save(tripRequest);

    }

    public void deleteTripRequest(Integer id, Integer userId) {
        User u = userRepository.findUserById(userId);
        if (u == null) {
            throw new ApiException("user not found");
        }
        if (!u.getRole().equalsIgnoreCase("passenger")) {
            throw new ApiException(" the user that want to delete trip request must be a passenger");
        }
        TripRequest tripRequest = tripRequestRepository.findTripRequestById(id);
        if (tripRequest == null) {
            throw new ApiException("trip request not found");
        }
        if (!tripRequest.getUserId().equals(userId)) {
            throw new ApiException("Unauthorized: You can only delete your own trip requests");
        }
        tripRequestRepository.delete(tripRequest);
    }

public List<TripRequest> getTriPRequstByFilters(String departureStation, String arrivalStation,Date departureTime,int numberOfSeats) {


        if (departureStation == null && arrivalStation == null  && numberOfSeats == 0) {

            return tripRequestRepository.findAll();
        }

        return tripRequestRepository.findByFilters(departureStation, arrivalStation,departureTime,numberOfSeats);
    }
    public void markTripRequestAsFulfilled(Integer requestId) {

        TripRequest tripRequest = tripRequestRepository.findTripRequestById(requestId);
                if (tripRequest == null) {
                    throw new ApiException("trip request not found");
                }


        if (tripRequest.getStatus().equalsIgnoreCase("fulfilled")) {
            throw new ApiException("This trip request is already fulfilled.");
        }


        tripRequest.setStatus("fulfilled");
        tripRequestRepository.save(tripRequest);
    }

}

