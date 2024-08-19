package com.example.intercity_travel_system.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "user id cannot be null")
    @Column(columnDefinition = " int not null")
    private Integer userId;
    @NotNull(message = "vehicle offer id cannot be null")
    @Column(columnDefinition = " int not null")
    private Integer vehicleOffer_id;
    @Positive(message = "number of seats must be positive number")
    @NotNull(message = "number of Seats cannot be null")
    @Column(columnDefinition = "int not null")
    private int seatsBooked;
    @NotNull(message = "booking Date Time cannot be null")
    @Column(columnDefinition = "date not null")
    private Date bookingDateTime;
    @NotEmpty(message = "payment Status cannot be empty")
    @Pattern(regexp = "completed|not completed|refunded", message = "payment Status must be either 'completed' or 'not completed' or 'refunded' only")
    @Check(constraints = "paymentStatus IN ('completed', 'not completed','refunded')")

    @Column(columnDefinition = "varchar(15) not null")
    private String paymentStatus;
    @Pattern(regexp = "in progress|arrived|canceled", message = "trip Status must be either 'in progress' or 'arrived' or 'canceled' only")
    @Column(columnDefinition = "varchar(15) not null")
    @Check(constraints = "tripStatus IN ('in progress', 'arrived','canceled')")
    private String tripStatus ;
}
