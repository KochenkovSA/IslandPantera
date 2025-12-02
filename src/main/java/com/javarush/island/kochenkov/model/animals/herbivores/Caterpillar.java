package com.javarush.island.kochenkov.model.animals.herbivores;

import com.javarush.island.kochenkov.model.animals.Animal;
import com.javarush.island.kochenkov.model.animals.Herbivore;
import com.javarush.island.kochenkov.model.enums.AnimalType;

public class Caterpillar extends Herbivore {
    public Caterpillar() {
        super(AnimalType.CATERPILLAR, 0.01, 1000, 0, 0);
    }

    @Override
    protected Animal createChild() {
        return new Caterpillar();
    }
}
