package pl.qdoppie.doppie.packages.plants;

import pl.qdoppie.doppie.packages.Organism;
import pl.qdoppie.doppie.packages.Plant;
import pl.qdoppie.doppie.packages.World;

public class Belladonna extends Plant {
    public Belladonna(int x, int y, World world) {
        super(x, y, world, 0, 0, "Belladonna", 1);
    }

    @Override
    final public boolean isEatenBy(Organism org) {
        org.dies();
        return super.isEatenBy(org);
    }

    @Override
    final protected void copy(int x, int y, World world) {
        world.addOrganism(new Belladonna(x, y, world));
    }
}
