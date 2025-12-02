package com.javarush.island.kochenkov.model.island;

import com.javarush.island.kochenkov.model.animals.Animal;
import com.javarush.island.kochenkov.model.enums.AnimalType;

import java.util.HashMap;
import java.util.Map;

public class Statistics {
    private final Island island;

    public Statistics(Island island) {
        this.island = island;
    }

    public void printStatistics(int turn) {
        System.out.println("\n=== Turn " + turn + " ===");
        System.out.println("Animal Statistics:");
        Map<AnimalType, Integer> counts = island.getAnimalCounts();
        counts.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .sorted(Map.Entry.<AnimalType, Integer>comparingByValue().reversed())
                .forEach(entry -> System.out.printf("%s %s: %d%n",
                        entry.getKey().getEmoji(),
                        entry.getKey().name(),
                        entry.getValue()));
        System.out.println("Total animals: " +
                counts.values().stream().mapToInt(Integer::intValue).sum());
    }

    public void printIslandMap() {
        System.out.println("\nIsland Map (10x10):");
        System.out.println("=".repeat(45)); // Верхняя граница

        Location[][] locations = island.getLocations();

        for (int y = 0; y < island.getHeight(); y++) {
            System.out.print("| ");
            for (int x = 0; x < island.getWidth(); x++) {
                Location location = locations[y][x];
                String cellRepresentation = getCellSymbol(location);
                System.out.print(cellRepresentation + " | ");
            }
            System.out.println();
            if (y < island.getHeight() - 1) {
                System.out.println("-".repeat(45));
            }
        }
        System.out.println("=".repeat(45)); // Нижняя граница
    }

    private String getCellSymbol(Location location) {
        Map<AnimalType, Integer> typeCounts = new java.util.HashMap<>();
        for (Animal animal : location.getAnimals()) {
            if (animal.isAlive()) {
                typeCounts.merge(animal.getType(), 1, Integer::sum);
            }
        }
        if (typeCounts.isEmpty()) {
            return location.getPlant().isAlive() ? "🌿" : "·";
        } else {
            return typeCounts.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(entry -> entry.getKey().getEmoji())
                    .orElse("·");
        }
    }

    private String getCellRepresentation(Location location) {
        Map<AnimalType, Integer> typeCounts = new HashMap<>();
        for (Animal animal : location.getAnimals()) {
            typeCounts.merge(animal.getType(), 1, Integer::sum);
        }
        if (typeCounts.isEmpty()) {
            return location.getPlant().isAlive() ? AnimalType.PLANT.getEmoji() : "\uD83D\uDCA9";
        }
        return typeCounts.entrySet().stream()
                .sorted(Map.Entry.<AnimalType, Integer>comparingByValue().reversed())
                .limit(4).map(entry -> entry.getKey().getEmoji()).reduce("", String::concat);
    }
}
