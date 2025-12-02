package com.javarush.island.kochenkov.simulation;

import com.javarush.island.kochenkov.model.island.Island;
import com.javarush.island.kochenkov.model.island.Statistics;

public class StatisticsTask implements Runnable{
    private final Island island;
    private final Statistics statistics;
    private final Simulation simulation;

    public StatisticsTask(Island island, Statistics statistics, Simulation simulation) {
        this.island = island;
        this.statistics = statistics;
        this.simulation = simulation;
    }

    @Override
    public void run() {
        if (simulation.isRunning()) {
            statistics.printStatistics(simulation.getTurn());
            statistics.printIslandMap();
        }
    }
}
