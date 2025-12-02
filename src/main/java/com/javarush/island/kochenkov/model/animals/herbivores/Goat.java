package com.javarush.island.kochenkov.model.animals.herbivores;

import com.javarush.island.kochenkov.model.animals.Animal;
import com.javarush.island.kochenkov.model.animals.Herbivore;
import com.javarush.island.kochenkov.model.enums.AnimalType;

public class Goat extends Herbivore {
    public Goat() {
        super(AnimalType.GOAT, 60, 140, 3, 10);
    }

    @Override
    protected Animal createChild() {
        return new Goat();
    }
}
