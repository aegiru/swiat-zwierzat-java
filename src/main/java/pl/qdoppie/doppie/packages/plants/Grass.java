package pl.qdoppie.doppie.packages.plants;

import pl.qdoppie.doppie.packages.Plant;
import pl.qdoppie.doppie.packages.World;

public class Grass extends Plant {
    public Grass(int x, int y, World world) {
        super(x, y, world, 0, 0, "Grass", 1);
    }

    @Override
    final protected void copy(int x, int y, World world) {
        world.addOrganism(new Grass(x, y, world));
    }
}
