package pl.qdoppie.doppie.packages.animals;

import pl.qdoppie.doppie.packages.Animal;
import pl.qdoppie.doppie.packages.World;

public class Wolf extends Animal {
    public Wolf(int x, int y, World world) {
        super(x, y, world, 9, 5, "Wolf", 1);
    }

    @Override
    final protected void copy(int x, int y, World world) {
        world.addOrganism(new Wolf(x, y, world));
    }
}
