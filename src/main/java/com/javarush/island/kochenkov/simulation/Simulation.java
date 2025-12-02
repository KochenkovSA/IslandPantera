package com.javarush.island.kochenkov.simulation;

import com.javarush.island.kochenkov.model.island.Island;
import com.javarush.island.kochenkov.model.island.Statistics;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Simulation {
    private final Island island;
    private final Statistics statistics;
    private final ScheduledExecutorService mainScheduler;
    private final ExecutorService animalExecutor;
    private volatile boolean running = false;
    private final AtomicInteger turnCounter = new AtomicInteger(0);

    public Simulation(int width, int height) {
        this.island = new Island(width, height);
        this.statistics = new  Statistics(island);
        this.mainScheduler = Executors.newScheduledThreadPool(3);
        this.animalExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void start() {
        if (running) return;
        running = true;
        System.out.println("Simulation started!");
        mainScheduler.scheduleAtFixedRate(new PlantTask(island), 0, 1, TimeUnit.SECONDS); // Задача для роста растений (каждый ход)
        mainScheduler.scheduleAtFixedRate(new AnimalTask(island, animalExecutor, turnCounter), 0, 1, TimeUnit.SECONDS); // Задача для жизненного цикла животных (каждый ход)
        mainScheduler.scheduleAtFixedRate(new StatisticsTask(island, statistics,  this), 0, 2, TimeUnit.SECONDS); // Задача для статистики (каждые 2 хода)
    }

    public void stop() {
        running = false;
        System.out.println("\n Simulation stopped!");
        statistics.printStatistics(turnCounter.get());
        statistics.printIslandMap();
        mainScheduler.shutdown();
        animalExecutor.shutdown();
        try {
            if(!mainScheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                mainScheduler.shutdownNow();
            }
            if(!animalExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                animalExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            mainScheduler.shutdownNow();
            animalExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public void restart(){
        stop();
        System.out.println("Restart functionality to be implemented");
    }

    public boolean isRunning() {
        return running;
    }

    public int getTurn() {
        return turnCounter.get();
    }
}
