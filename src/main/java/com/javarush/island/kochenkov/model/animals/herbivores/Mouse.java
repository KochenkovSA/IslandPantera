package com.javarush.island.kochenkov.model.animals.herbivores;

import com.javarush.island.kochenkov.model.animals.Animal;
import com.javarush.island.kochenkov.model.animals.Herbivore;
import com.javarush.island.kochenkov.model.enums.AnimalType;

public class Mouse extends Herbivore {
    public Mouse() {
        super(AnimalType.MOUSE, 0.05, 500, 1, 0.01);
    }

    @Override
    protected Animal createChild() {
        return new Mouse();
    }
}
