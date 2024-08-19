package com.example.intercity_travel_system.Repository;

import com.example.intercity_travel_system.Model.VehicleOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VehicleOfferRepository extends JpaRepository<VehicleOffer,Integer> {
    VehicleOffer findVehicleOfferById(Integer id);

    @Query("select v from VehicleOffer v where v.departureLocation=?1 and v.destinationLocation=?2 and v.departureDateTime=?3 and v.availableSeats>=?4")
    List<VehicleOffer> searchTrip(String departureStation, String arrivalStation, Date departureDate, int seatsRequired);

    VehicleOffer findVehicleOfferByUserId(Integer userId);

    @Query("Select v from VehicleOffer v where v.departureLocation=?1 and v.destinationLocation=?2 and v.availableSeats>=?3 and v.departureDateTime>=?4")
    List<VehicleOffer> findMatchingOffers( String departureLocation,String destination, int seatsNeeded, Date departureDateTime);
}

