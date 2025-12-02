package com.javarush.island.kochenkov.model.animals;

import com.javarush.island.kochenkov.model.enums.AnimalType;
import com.javarush.island.kochenkov.model.plants.Plant;
import com.javarush.island.kochenkov.model.island.Location;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Herbivore extends Animal {

    private static final double[][] EAT_PROBABILITY = {
            {0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},           // Horse
            {0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 100},           // Deer
            {0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 100},           // Rabbit
            {0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 90, 100},          // Mouse
            {0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 100},           // Goat
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 100},           // Sheep
            {0, 0, 0, 0, 0, 0, 0, 0, 50, 0, 0, -1, 0, 0, 90, 100},         // Boar
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 100},           // Buffalo
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 90, 100},          // Duck
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 100}            // Caterpillar
    };

    public Herbivore(AnimalType type, double weight, int maxPerLocation, int speed, double foodNeeded) {
        super(type, weight, maxPerLocation, speed, foodNeeded);
    }

    @Override
    public boolean eat(Object food) {
        if (food instanceof Plant) {
            Plant plant = (Plant) food;
            double probability = getEatProbability(AnimalType.PLANT);
            if (tryToEat(probability)) {
                double foodAmount = Math.min(plant.getWeight(), foodNeeded - satiety);
                satiety += foodAmount;
                weight = Math.min(maxWeight, weight + foodAmount + 0.05);
                plant.reduceWeight(foodAmount);
                return true;
            }
        } else if (food instanceof Animal) {
            Animal prey = (Animal) food;
            double probability = getEatProbability(prey.getType());
            if (tryToEat(probability)) {
                double foodAmount = prey.getWeight();
                satiety += foodAmount;
                weight = Math.min(maxWeight, weight + foodAmount * 0.1);
                prey.die();
                return true;
            }
        }
        return false;
    }

    private double getEatProbability(AnimalType foodType) {
        int herbivoreIndex = getHerbivoreIndex();
        int foodIndex = getFoodIndex(foodType);
        if (herbivoreIndex >= 0 && foodIndex >= 0) {
            return EAT_PROBABILITY[herbivoreIndex][foodIndex];
        }
        return 0;
    }

    private int getHerbivoreIndex() {
        return switch (this.type) {
            case HORSE -> 0;
            case DEER -> 1;
            case RABBIT -> 2;
            case MOUSE -> 3;
            case GOAT -> 4;
            case SHEEP -> 5;
            case BOAR -> 6;
            case BUFFALO -> 7;
            case DUCK -> 8;
            case CATERPILLAR -> 9;
            default -> -1;
        };
    }

    private int getFoodIndex(AnimalType preyType) {
        return switch (preyType) {
            case WOLF -> 0;
            case PYTHON -> 1;
            case FOX -> 2;
            case BEAR -> 3;
            case EAGLE -> 4;
            case HORSE -> 5;
            case DEER -> 6;
            case RABBIT -> 7;
            case MOUSE -> 8;
            case GOAT -> 9;
            case SHEEP -> 10;
            case BOAR -> 11;
            case BUFFALO -> 12;
            case DUCK -> 13;
            case CATERPILLAR -> 14;
            case PLANT -> 15;
            default -> -1;
        };
    }

    @Override
    public void multiply() {
        if (satiety >= foodNeeded * 0.5) {
            List<Animal> sameTypeAnimals = location.getAnimals().stream()
                    .filter(a -> a.getType() == this.type && a != this)
                    .toList();
            if (!sameTypeAnimals.isEmpty()) {
                Animal partner = sameTypeAnimals.getFirst();
                if (partner.getSatiety() >= partner.getFoodNeeded() * 0.5) {
                    location.addAnimal(createChild());
                    satiety *= 0.7;
                    partner.setSatiety(partner.getSatiety() * 0.7);
                }
            }
        }
    }

    protected abstract Animal createChild();

    @Override
    public Location chooseDirection() {
        List<Location> possibleLocation = location.getAdjacentLocations();
        if (!possibleLocation.isEmpty()) {
            return possibleLocation.get(ThreadLocalRandom.current().nextInt(possibleLocation.size()));
        }
        return location;
    }
}
