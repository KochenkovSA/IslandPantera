package com.javarush.island.kochenkov.model.animals.herbivores;

import com.javarush.island.kochenkov.model.animals.Animal;
import com.javarush.island.kochenkov.model.animals.Herbivore;
import com.javarush.island.kochenkov.model.enums.AnimalType;

public class Buffalo extends Herbivore {
    public Buffalo() {
        super(AnimalType.BUFFALO, 700, 10, 3, 100);
    }

    @Override
    protected Animal createChild() {
        return new Buffalo();
    }
}
