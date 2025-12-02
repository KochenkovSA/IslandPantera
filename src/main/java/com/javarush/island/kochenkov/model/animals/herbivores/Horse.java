package com.javarush.island.kochenkov.model.animals.herbivores;

import com.javarush.island.kochenkov.model.animals.Animal;
import com.javarush.island.kochenkov.model.animals.Herbivore;
import com.javarush.island.kochenkov.model.enums.AnimalType;

public class Horse extends Herbivore {
    public Horse() {
        super(AnimalType.HORSE, 400, 20, 4, 50);
    }

    @Override
    protected Animal createChild() {
        return new Horse();
    }
}
