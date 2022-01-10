package com.arsal.deliveryservice.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "delivery_details")
public class DeliveryDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private CustomerType customerType;

    @Column(name = "delivery_status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private DeliveryStatus deliveryStatus;

    @Column(name = "rider_rating", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private RiderRating riderRating;

    @Column(name = "is_processed", nullable = false)
    private Boolean isProcessed = false;

    @Column(name = "current_distance_from_destination_in_meters", nullable = false)
    private Integer currentDistanceFromDestinationInMeters;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_to_reach_destination", nullable = false)
    private Date timeToReachDestination;

    @Column(name = "restaurant_mean_time_to_prepare_food_in_minutes", nullable = false)
    private Integer restaurantMeanTimeToPrepareFood;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expected_delivery_time", nullable = false)
    private Date expectedDeliveryTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public RiderRating getRiderRating() {
        return riderRating;
    }

    public void setRiderRating(RiderRating riderRating) {
        this.riderRating = riderRating;
    }

    public Boolean getProcessed() {
        return isProcessed;
    }

    public void setProcessed(Boolean processed) {
        isProcessed = processed;
    }

    public Integer getCurrentDistanceFromDestinationInMeters() {
        return currentDistanceFromDestinationInMeters;
    }

    public void setCurrentDistanceFromDestinationInMeters(Integer currentDistanceFromDestinationInMeters) {
        this.currentDistanceFromDestinationInMeters = currentDistanceFromDestinationInMeters;
    }

    public Date getTimeToReachDestination() {
        return timeToReachDestination;
    }

    public void setTimeToReachDestination(Date timeToReachDestination) {
        this.timeToReachDestination = timeToReachDestination;
    }

    public Integer getRestaurantMeanTimeToPrepareFood() {
        return restaurantMeanTimeToPrepareFood;
    }

    public void setRestaurantMeanTimeToPrepareFood(Integer restaurantMeanTimeToPrepareFood) {
        this.restaurantMeanTimeToPrepareFood = restaurantMeanTimeToPrepareFood;
    }

    public Date getExpectedDeliveryTime() {
        return expectedDeliveryTime;
    }

    public void setExpectedDeliveryTime(Date expectedDeliveryTime) {
        this.expectedDeliveryTime = expectedDeliveryTime;
    }
}
