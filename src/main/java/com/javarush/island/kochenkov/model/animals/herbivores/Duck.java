package com.javarush.island.kochenkov.model.animals.herbivores;

import com.javarush.island.kochenkov.model.animals.Animal;
import com.javarush.island.kochenkov.model.animals.Herbivore;
import com.javarush.island.kochenkov.model.enums.AnimalType;

public class Duck extends Herbivore {
    public Duck() {
        super(AnimalType.DUCK, 1, 200, 4, 0.15);
    }

    @Override
    protected Animal createChild() {
        return new Duck();
    }
}
