package com.javarush.island.kochenkov.simulation;

import com.javarush.island.kochenkov.model.animals.Animal;
import com.javarush.island.kochenkov.model.island.Island;
import com.javarush.island.kochenkov.model.island.Location;
import com.javarush.island.kochenkov.model.plants.Plant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


public class AnimalTask implements Runnable {
    private final Island island;
    private final ExecutorService executor;
    private final AtomicInteger turnCounter;

    public AnimalTask(Island island, ExecutorService executor, AtomicInteger turnCounter) {
        this.island = island;
        this.executor = executor;
        this.turnCounter = turnCounter;
    }

    @Override
    public void run() {
        List<Runnable> tasks = new ArrayList<>();
        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Location location = island.getLocation(x, y);
                List<Animal> animals = new ArrayList<>(location.getAnimals());
                for (Animal animal : animals) {
                    if (animal.isAlive()) {
                        tasks.add(() -> processAnimal(animal));
                    }
                }
            }
        }
        try {
            executor.invokeAll(tasks.stream()
                    .map(Executors::callable)
                    .toList());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        int currentTurn = turnCounter.incrementAndGet();
        if(currentTurn > 0 && currentTurn % 5 ==0) {
            island.spawnNewAnimals();
        }
    }

    private void processAnimal(Animal animal) {
        if (!animal.isAlive()) return;
        Location currentLocation = animal.getLocation();
        if (animal.getSatiety() < animal.getFoodNeeded()) {
            boolean hasEaten = false;
            Plant plant = currentLocation.getPlant();
            if (plant.isAlive()) {
                hasEaten = animal.eat(plant);
            }
            if (!hasEaten) {
                List<Animal> potentialPrey = currentLocation.getAnimals().stream()
                        .filter(prey -> prey != animal && prey.isAlive())
                        .toList();
                for (Animal prey : potentialPrey) {
                    if (animal.eat(prey)) {
                        hasEaten = true;
                        break;
                    }
                }
            }
        }

        if (animal.getSatiety() >= animal.getFoodNeeded() * 0.5) {
            animal.multiply();
        }

        Location newLocation = animal.chooseDirection();
        if (newLocation != currentLocation) {
            animal.move(newLocation);
        }

        animal.reduceSatiety();
    }
}
