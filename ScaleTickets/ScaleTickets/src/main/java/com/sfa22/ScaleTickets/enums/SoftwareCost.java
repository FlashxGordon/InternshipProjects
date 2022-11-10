package com.sfa22.ScaleTickets.enums;

public enum SoftwareCost {

    SOFTWARE_COST(0.01);

    public final double softwareCost;

    public double getPricePerKm() {
        return softwareCost;
    }

    SoftwareCost(double softwareCost) {

        this.softwareCost = softwareCost;
    }
}