package com.javarush.island.kochenkov.model.animals.predators;

import com.javarush.island.kochenkov.model.animals.Animal;
import com.javarush.island.kochenkov.model.animals.Predator;
import com.javarush.island.kochenkov.model.enums.AnimalType;

public class Eagle extends Predator {
    public Eagle() {
        super(AnimalType.EAGLE, 6, 20, 4, 6);
    }

    @Override
    protected Animal createChild() {
        return new Eagle();
    }
}
