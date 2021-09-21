package com.example.quickdel;

public class Orders {
    private String Vehicle;
    private String Size;
    private String Weight;
    private Double SizePrice;
    private Double VehiclePrice;
    private Double WeightPrice;
    private Double Total;
    private String orderNumber;
    private String distance;
    private Double distancePrice;
    private String recipientPhone;

    public String getRecipientPhone() {
        return recipientPhone;
    }
    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public Double getDistancePrice() {
        return distancePrice;
    }

    public void setDistancePrice(Double distancePrice) {
        this.distancePrice = distancePrice;
    }


    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }



    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }


    public String getRecipient() {
        return Recipient;
    }

    public void setRecipient(String recipient) {
        Recipient = recipient;
    }

    private String Recipient;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    private String Description;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    private String UserID;

    public String getPickupPoint() {
        return PickupPoint;
    }

    public void setPickupPoint(String pickupPoint) {
        PickupPoint = pickupPoint;
    }

    public String getDestinationPoint() {
        return DestinationPoint;
    }

    public void setDestinationPoint(String destinationPoint) {
        DestinationPoint = destinationPoint;
    }

    private String PickupPoint;
    private String DestinationPoint;

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

    public Double getSizePrice() {
        return SizePrice;
    }

    public void setSizePrice(Double sizePrice) {
        SizePrice = sizePrice;
    }

    public Double getVehiclePrice() {
        return VehiclePrice;
    }

    public void setVehiclePrice(Double vehiclePrice) {
        VehiclePrice = vehiclePrice;
    }

    public Double getWeightPrice() {
        return WeightPrice;
    }

    public void setWeightPrice(Double weightPrice) {
        WeightPrice = weightPrice;
    }


    public String getVehicle() {
        return Vehicle;
    }

    public void setVehicle(String vehicle) {
        Vehicle = vehicle;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

}
