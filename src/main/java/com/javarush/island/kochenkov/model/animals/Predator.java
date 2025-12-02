package com.javarush.island.kochenkov.model.animals;

import com.javarush.island.kochenkov.model.enums.AnimalType;
import com.javarush.island.kochenkov.model.island.Location;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Predator extends Animal{

    private static final double[][] EAT_PROBABILITY = {
            {-1, 0, 0, 0, 0, 10, 15, 60, 80, 60, 70, 15, 10, 40, 0, 0},     // Wolf
            {0, -1, 15, 0, 0, 0, 0, 20, 40, 0, 0, 0, 0, 10, 0, 0},          // Python
            {0, 0, -1, 0, 0, 0, 0, 70, 90, 0, 0, 0, 0, 60, 40, 0},          // Fox
            {0, 80, 0, -1, 0, 40, 80, 80, 90, 70, 70, 50, 20, 10, 0, 0},    // Bear
            {0, 0, 10, 0, -1, 0, 0, 90, 90, 0, 0, 0, 0, 80, 0, 0}           // Eagle
    };

    public Predator(AnimalType type, double weight, int maxPerLocation, int speed, double foodNeeded) {
        super(type, weight, maxPerLocation, speed, foodNeeded);
    }

    @Override
    public boolean eat(Object food) {
        if(food instanceof Animal) {
            Animal prey = (Animal) food;
            double probability = getEatProbability(prey.getType());
            if(tryToEat(probability)) {
                double foodAmount = prey.getWeight();
                satiety += foodAmount;
                weight = Math.min(maxWeight, weight + foodAmount * 0.1);
                prey.die();
                return true;
            }
        }
        return false;
    }

    private double getEatProbability(AnimalType preyType) {
        int predatorIndex = getPredatorIndex();
        int preyIndex = getPreyIndex(preyType);
        if (predatorIndex >= 0 && preyIndex >= 0) {
            return EAT_PROBABILITY[predatorIndex][preyIndex];
        }
        return 0;
    }

    private int getPredatorIndex() {
        return switch (this.type) {
            case WOLF -> 0;
            case PYTHON -> 1;
            case FOX -> 2;
            case BEAR -> 3;
            case EAGLE -> 4;
            default -> -1;
        };
    }

    private int getPreyIndex(AnimalType preyType) {
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
        if (satiety >= foodNeeded *0.5) {
            List<Animal> sameTypeAnimals = location.getAnimals().stream()
                    .filter(a -> a.getType() == this.type && a != this)
                    .toList();
            if(!sameTypeAnimals.isEmpty()) {
                Animal partner = sameTypeAnimals.getFirst();
                if(partner.getSatiety() >= partner.getFoodNeeded() * 0.5) {
                    location.addAnimal(createChild());
                    satiety *=0.7;
                    partner.setSatiety(partner.getSatiety() *0.7);
                }
            }
        }
    }

    protected abstract Animal createChild();

    @Override
    public Location chooseDirection() {
        List<Location> possibleLocations = location.getAdjacentLocations();
        if(!possibleLocations.isEmpty()) {
            return possibleLocations.get(ThreadLocalRandom.current().nextInt(possibleLocations.size()));
        }
        return location;
    }
}
