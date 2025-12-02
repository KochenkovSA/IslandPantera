package com.javarush.island.kochenkov.model.animals.predators;

import com.javarush.island.kochenkov.model.animals.Animal;
import com.javarush.island.kochenkov.model.animals.Predator;
import com.javarush.island.kochenkov.model.enums.AnimalType;

public class Python extends Predator {
    public Python() {
        super(AnimalType.PYTHON, 15, 30, 2, 2);
    }

    @Override
    protected Animal createChild() {
        return new Python();
    }
}
