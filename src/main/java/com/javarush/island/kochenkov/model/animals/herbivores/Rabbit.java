package com.javarush.island.kochenkov.model.animals.herbivores;

import com.javarush.island.kochenkov.model.animals.Animal;
import com.javarush.island.kochenkov.model.animals.Herbivore;
import com.javarush.island.kochenkov.model.enums.AnimalType;

public class Rabbit extends Herbivore {
    public Rabbit() {
        super(AnimalType.RABBIT, 2, 150, 2, 0.45);
    }

    @Override
    protected Animal createChild() {
        return new Rabbit();
    }
}
