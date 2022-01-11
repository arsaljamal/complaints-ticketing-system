package com.arsal.ticketingservice.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tickets")
public class Tickets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "delivery_id", nullable = false)
    private Long deliveryId;

    @Column(name = "customer_type", nullable = false)
    private String customerType;

    @Column(name = "delivery_status", nullable = false)
    private String deliveryStatus;

    @Column(name = "rider_rating", nullable = false)
    private String riderRating;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expected_delivery_time", nullable = false)
    private Date expectedDeliveryTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expected_time_of_delivery", nullable = false)
    private Date estimatedTimeOfDelivery;

    public Long getId() {
        return id;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
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
