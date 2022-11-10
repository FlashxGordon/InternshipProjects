package com.sfa22.ScaleTickets.enums;

public enum PricePerKm {

    PRICE_PER_KM(0.30);

    public final double pricePerKm;

    public double getPricePerKm() {
        return pricePerKm;
    }

    PricePerKm(double pricePerKm) {

        this.pricePerKm = pricePerKm;
    }
}