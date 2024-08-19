package com.example.intercity_travel_system.Service;

import com.example.intercity_travel_system.Api.ApiException;
import com.example.intercity_travel_system.Model.TripRequest;
import com.example.intercity_travel_system.Model.User;
import com.example.intercity_travel_system.Model.VehicleOffer;
import com.example.intercity_travel_system.Repository.TripRequestRepository;
import com.example.intercity_travel_system.Repository.UserRepository;
import com.example.intercity_travel_system.Repository.VehicleOfferRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleOfferService {
    private final VehicleOfferRepository vehicleOfferRepository;
    private final UserRepository userRepository;
    private final TripRequestRepository tripRequestRepository;

    public List<VehicleOffer> findAllVehicleOffer(){
        return vehicleOfferRepository.findAll();
    }
    public void addVehicleOffer(VehicleOffer vehicleOffer){
        User u=userRepository.findUserById(vehicleOffer.getUserId());
        if(u==null){
            throw new ApiException("user not found");
        }
        if(!u.getRole().equalsIgnoreCase("driver")){
            throw new ApiException("user that make Vehicle Offer must be a driver");
        }
        if(vehicleOffer.getDepartureDateTime().after(vehicleOffer.getArrivalTime())){
            throw new ApiException("the departure time cannot be after the arrival time");
        }
        vehicleOfferRepository.save(vehicleOffer);


    }
    public void updateVehicleOffer(Integer id,VehicleOffer vehicleOffer){
        User u=userRepository.findUserById(vehicleOffer.getUserId());
        if(u==null){
            throw new ApiException("user not found");
        }
        if(!u.getRole().equalsIgnoreCase("driver")){
            throw new ApiException("user that update Vehicle Offer must be a driver");
        }
        VehicleOffer v=vehicleOfferRepository.findVehicleOfferById(id);
        if(v==null){
            throw new ApiException("vehicle Offer not found");
        }
        v.setUserId(vehicleOffer.getUserId());
        v.setArrivalTime(vehicleOffer.getArrivalTime());
        v.setDepartureDateTime(vehicleOffer.getDepartureDateTime());
        v.setAvailableSeats(vehicleOffer.getAvailableSeats());
        v.setDestinationLocation(vehicleOffer.getDestinationLocation());
        v.setDepartureLocation(vehicleOffer.getDepartureLocation());
        v.setPricePerSeat(vehicleOffer.getPricePerSeat());
        vehicleOfferRepository.save(v);


    }
    public void deleteVehicleOffer(Integer id,Integer userId){
        User u=userRepository.findUserById(userId);
        if(u==null){
            throw new ApiException("user not found");
        }
        if(!u.getRole().equalsIgnoreCase("driver")){
            throw new ApiException("user that delete Vehicle Offer must be a driver");
        }
        VehicleOffer v=vehicleOfferRepository.findVehicleOfferById(id);
        if(v==null){
            throw new ApiException("vehicle Offer not found");
        }
        vehicleOfferRepository.delete(v);
    }

    public List<VehicleOffer> SearchTrip(String departureStation, String arrivalStation, Date departureDate, int seatsRequired){
        List<VehicleOffer> vehicleOffer=vehicleOfferRepository.searchTrip(departureStation,arrivalStation,departureDate,seatsRequired);
        if(vehicleOffer==null){
            throw new ApiException("vehicle Offer not found");
        }
        return vehicleOffer;

    }

    public int getAvailableSeats(Integer vehicleOfferId) {
        VehicleOffer vehicleOffer = vehicleOfferRepository.findVehicleOfferById(vehicleOfferId);
                if(vehicleOffer==null){
                    throw new ApiException("vehicle Offer not found");
                }

        return vehicleOffer.getAvailableSeats();
    }

    public void applyDiscount(Integer vehicleOfferId, Integer userId,int discountPercentage) {

        VehicleOffer vehicleOffer = vehicleOfferRepository.findVehicleOfferById(vehicleOfferId);
        if(vehicleOffer==null){
            throw new ApiException("vehicle Offer not found");
        }
        User driver = userRepository.findUserById(vehicleOffer.getUserId());
        if(driver==null){
            throw new ApiException("driver not found");
        }
        if(!driver.getRole().equalsIgnoreCase("driver")){
            throw new ApiException("ou are not authorized to apply a discount for this vehicle offer");
        }



        int originalPrice = vehicleOffer.getPricePerSeat();
        int discountedPrice = originalPrice - (originalPrice * discountPercentage / 100);


        vehicleOffer.setPricePerSeat(discountedPrice);
        vehicleOfferRepository.save(vehicleOffer);
    }
    public List<VehicleOffer> findMatches(Integer tripRequestId) {
        TripRequest request = tripRequestRepository.findTripRequestById(tripRequestId);
        if (request==null){
            throw new ApiException("trip request not found");
        }

        return vehicleOfferRepository.findMatchingOffers(
                request.getDepartureStation(),
                request.getArrivalStation(),
                request.getNumberOfSeats(),
                request.getDepartureTime()
        );
    }

}
