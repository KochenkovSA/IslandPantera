package com.javarush.island.kochenkov.model.animals.herbivores;

import com.javarush.island.kochenkov.model.animals.Animal;
import com.javarush.island.kochenkov.model.animals.Herbivore;
import com.javarush.island.kochenkov.model.enums.AnimalType;

public class Boar extends Herbivore {
    public Boar() {
        super(AnimalType.BOAR, 400, 50, 2, 50);
    }

    @Override
    protected Animal createChild() {
        return new Boar();
    }
}
