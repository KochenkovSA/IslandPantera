package com.javarush.island.kochenkov;

import com.javarush.island.kochenkov.simulation.Simulation;

import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Simulation simulation = new Simulation(10, 10);
        Scanner scanner = new Scanner(System.in);
        System.out.println("====Island simulation====");
        System.out.println("Commands: start(1), stop(2), restart(3), exit(4)");
        while (true) {
            System.out.println("Enter command: ");
            String command = String.valueOf(scanner.nextInt());

            switch (command) {
                case "1":
                    simulation.start();
                    break;
                case "2":
                    simulation.stop();
                    break;
                case "3":
                    simulation.restart();
                    break;
                case "4":
                    simulation.stop();
                    scanner.close();
                    return;
                default:
                    System.out.println("Unknow command. Use: start(1), stop(2), restart(3), exit(4)");
            }
        }
    }
}
