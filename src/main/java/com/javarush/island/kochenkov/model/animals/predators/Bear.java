package com.javarush.island.kochenkov.model.animals.predators;

import com.javarush.island.kochenkov.model.animals.Animal;
import com.javarush.island.kochenkov.model.animals.Predator;
import com.javarush.island.kochenkov.model.enums.AnimalType;

public class Bear extends Predator {
    public Bear() {
        super(AnimalType.BEAR, 500, 5, 2, 80);
    }

    @Override
    protected Animal createChild() {
        return new Bear();
    }
}
