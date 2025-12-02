package com.javarush.island.kochenkov.model.animals.herbivores;

import com.javarush.island.kochenkov.model.animals.Animal;
import com.javarush.island.kochenkov.model.animals.Herbivore;
import com.javarush.island.kochenkov.model.enums.AnimalType;

public class Sheep extends Herbivore {
    public Sheep() {
        super(AnimalType.SHEEP, 1, 200, 4, 0.15);
    }

    @Override
    protected Animal createChild() {
        return new Sheep();
    }
}
