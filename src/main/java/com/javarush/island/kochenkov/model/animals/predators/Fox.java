package com.javarush.island.kochenkov.model.animals.predators;

import com.javarush.island.kochenkov.model.animals.Animal;
import com.javarush.island.kochenkov.model.animals.Predator;
import com.javarush.island.kochenkov.model.enums.AnimalType;

public class Fox extends Predator {
    public Fox() {
        super(AnimalType.FOX, 8, 30, 2, 2);
    }

    @Override
    protected Animal createChild() {
        return new Fox();
    }
}
