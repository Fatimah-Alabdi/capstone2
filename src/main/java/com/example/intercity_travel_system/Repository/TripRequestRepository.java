package com.example.intercity_travel_system.Repository;

import com.example.intercity_travel_system.Model.TripRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TripRequestRepository extends JpaRepository<TripRequest, Integer> {
    TripRequest findTripRequestById(Integer id);
    @Query("select t from TripRequest t where t.departureStation=?1 and t.arrivalStation=?2 and t.departureTime=?3and t.numberOfSeats>=?4")

    List<TripRequest> findByFilters(String departureStation, String arrivalStation,Date departureTime,int numberOfSeats);

}
