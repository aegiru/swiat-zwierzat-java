package pl.qdoppie.doppie.packages.plants;

import pl.qdoppie.doppie.packages.Plant;
import pl.qdoppie.doppie.packages.World;

public class Dandelion extends Plant {
    public Dandelion(int x, int y, World world) {
        super(x, y, world, 0, 0, "Dandelion",1);
    }

    @Override
    final public void action() {
        for (int i = 0; i < 3; i++) {
            super.action();
        }
    }

    @Override
    final protected void copy(int x, int y, World world) {
        world.addOrganism(new Dandelion(x, y, world));
    }
}
