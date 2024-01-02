package pl.qdoppie.doppie.packages.animals;

import pl.qdoppie.doppie.packages.World;
import pl.qdoppie.doppie.packages.Animal;

public class Sheep extends Animal {
    public Sheep(int x, int y, World world) {
        super(x, y, world, 4, 4, "Sheep", 1);
    }

    @Override
    final protected void copy(int x, int y, World world) {
        world.addOrganism(new Sheep(x, y, world));
    }
}
