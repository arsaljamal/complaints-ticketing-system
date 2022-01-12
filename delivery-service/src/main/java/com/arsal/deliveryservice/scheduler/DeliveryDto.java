package com.arsal.deliveryservice.scheduler;

import java.util.Date;

public class DeliveryDto {

    private Long id;

    private String customerType;

    private String deliveryStatus;

    private String riderRating;

    private Date timeToReachDestination;

    private Date expectedDeliveryTime;

    private Date estimatedTimeOfDelivery;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getRiderRating() {
        return riderRating;
    }

    public void setRiderRating(String riderRating) {
        this.riderRating = riderRating;
    }

    public Date getTimeToReachDestination() {
        return timeToReachDestination;
    }

    public void setTimeToReachDestination(Date timeToReachDestination) {
        this.timeToReachDestination = timeToReachDestination;
    }

    public Date getExpectedDeliveryTime() {
        return expectedDeliveryTime;
    }

    public void setExpectedDeliveryTime(Date expectedDeliveryTime) {
        this.expectedDeliveryTime = expectedDeliveryTime;
    }

    public Date getEstimatedTimeOfDelivery() {
        return estimatedTimeOfDelivery;
    }

    public void setEstimatedTimeOfDelivery(Date estimatedTimeOfDelivery) {
        this.estimatedTimeOfDelivery = estimatedTimeOfDelivery;
    }
}
