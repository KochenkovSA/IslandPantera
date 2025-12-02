package com.javarush.island.kochenkov.model.animals.predators;

import com.javarush.island.kochenkov.model.animals.Animal;
import com.javarush.island.kochenkov.model.animals.Predator;
import com.javarush.island.kochenkov.model.enums.AnimalType;

public class Wolf extends Predator {
    public Wolf() {
        super(AnimalType.WOLF, 50, 30, 3, 8);
    }

    @Override
    protected Animal createChild() {
        return new Wolf();
    }
}
