package com.example.intercity_travel_system.Repository;

import com.example.intercity_travel_system.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {
    Booking findBookingById(Integer id);
    List<Booking> findBookingByUserId(Integer userId);

    @Query("SELECT t FROM Booking t WHERE t.userId = :userId AND t.tripStatus = 'in progress'")
    List<Booking> findActiveTripsByUserId( Integer userId);
}
