package com.javarush.island.kochenkov.model.island;

import com.javarush.island.kochenkov.model.animals.Animal;
import com.javarush.island.kochenkov.model.animals.herbivores.*;
import com.javarush.island.kochenkov.model.animals.predators.*;
import com.javarush.island.kochenkov.model.enums.AnimalType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Island {
    private final int width;
    private final int height;
    private final Location[][] locations;
    private final Map<AnimalType, Integer> animalCounts;

    public Island(int width, int height) {
        this.width = width;
        this.height = height;
        this.locations = new Location[height][width];
        this.animalCounts = new ConcurrentHashMap<>();
        initializeIsland();
        populateIsland();
    }

    private void initializeIsland() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                locations[y][x] = new Location(x, y, this);
            }
        }
        for (AnimalType type : AnimalType.values()) {
            animalCounts.put(type, 0);
        }
    }


    private Animal populateIsland() {
        Random random = ThreadLocalRandom.current();
        int animalType = random.nextInt(15);
        return switch (animalType) {
            case 0 -> new Wolf();
            case 1 -> new Python();
            case 2 -> new Fox();
            case 3 -> new Bear();
            case 4 -> new Eagle();
            case 5 -> new Horse();
            case 6 -> new Deer();
            case 7 -> new Rabbit();
            case 8 -> new Mouse();
            case 9 -> new Goat();
            case 10 -> new Sheep();
            case 11 -> new Boar();
            case 12 -> new Buffalo();
            case 13 -> new Duck();
            case 14 -> new Caterpillar();
            default -> new Rabbit();
        };
    }

    public void spawnNewAnimals() {
        Random random = ThreadLocalRandom.current();
        Set<AnimalType> existingTypes = getExistingAnimalTypes();
        for (AnimalType type : AnimalType.values()) {
            if (type != AnimalType.PLANT && !existingTypes.contains(type)) {
                int x = random.nextInt(width);
                int y = random.nextInt(height);
                Animal animal = createAnimalByType(type);
                locations[y][x].addAnimal(animal);
                updateAnimalCount(type, 1);
            }
        }
    }

    private Animal createAnimalByType(AnimalType type) {
        return switch (type) {
            case WOLF -> new Wolf();
            case PYTHON -> new Python();
            case FOX -> new Fox();
            case BEAR -> new Bear();
            case EAGLE -> new Eagle();
            case HORSE -> new Horse();
            case DEER -> new Deer();
            case RABBIT -> new Rabbit();
            case MOUSE -> new Mouse();
            case GOAT -> new Goat();
            case SHEEP -> new Sheep();
            case BOAR -> new Boar();
            case BUFFALO -> new Buffalo();
            case DUCK -> new Duck();
            case CATERPILLAR -> new Caterpillar();
            default -> new Rabbit();
        };
    }

    private Set<AnimalType> getExistingAnimalTypes() {
        Set<AnimalType> types = new HashSet<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (Animal animal : locations[y][x].getAnimals()) {
                    types.add(animal.getType());
                }
            }
        }
        return types;
    }

    public boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Location getLocation(int x, int y) {
        return locations[x][y];
    }

    public Location[][] getLocations() {
        return locations;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void updateAnimalCount(AnimalType type, int delta) {
        animalCounts.merge(type, delta, Integer::sum);
    }

    public Map<AnimalType, Integer> getAnimalCounts() {
        return new HashMap<>(animalCounts);
    }
}
