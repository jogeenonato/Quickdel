package com.example.quickdel;

public class Orders {
    private String Vehicle;
    private String Size;
    private String Weight;
    private Double SizePrice;
    private Double VehiclePrice;
    private Double WeightPrice;
    private Double Total;

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
