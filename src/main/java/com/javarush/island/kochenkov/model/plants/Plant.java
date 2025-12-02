package com.javarush.island.kochenkov.model.plants;

import com.javarush.island.kochenkov.model.enums.AnimalType;

public class Plant {
    private static final double MAX_WEIGHT = 1.0;
    private static final double GROWTH_RATE = 0.1;
    private double weight;
    private final AnimalType type = AnimalType.PLANT;

    public Plant() {
        this.weight = MAX_WEIGHT;
    }

    public void grow() {
        weight = Math.min(MAX_WEIGHT, weight + GROWTH_RATE);
    }

    public void reduceWeight(double amount) {
        weight = Math.max(0, weight-amount);
    }

    public double getWeight() {
        return weight;
    }

    public boolean isAlive() {
        return weight > 0;
    }

    public AnimalType getType() {
        return type;
    }
}
