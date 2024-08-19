package com.example.intercity_travel_system.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "user id cannot be null")
    @Column(columnDefinition = " int not null")
    private Integer userId;
    @NotNull(message = "vehicle offer id cannot be null")
    @Column(columnDefinition = " int not null")
    private Integer vehicleOfferId;
    @NotNull(message = "book id cannot be null")
    @Column(columnDefinition = " int not null")
    private Integer bookId;
@NotNull(message = "rating cannot be null")
@Positive
@Min(value = 1)
@Max(value = 5)
@Column(columnDefinition = " int not null")
    private int rating;
@NotEmpty(message = "comment cannot be empty")
@Column(columnDefinition = "varchar(200)not null")
    private String comment;
    @NotNull(message = "review Date Time cannot be null")
    @Column(columnDefinition = "date not null")
    private Date reviewDateTime;
}
