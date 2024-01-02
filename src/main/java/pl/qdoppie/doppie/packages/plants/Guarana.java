package pl.qdoppie.doppie.packages.plants;

import pl.qdoppie.doppie.packages.Organism;
import pl.qdoppie.doppie.packages.Plant;
import pl.qdoppie.doppie.packages.World;

public class Guarana extends Plant {
    public Guarana(int x, int y, World world) {
        super(x, y, world, 0, 0, "Guarana", 1);
    }

    @Override
    final public boolean isEatenBy(Organism org) {
        org.setStrength(org.getStrength() + 3);
        return super.isEatenBy(org);
    }

    @Override
    final protected void copy(int x, int y, World world) {
        world.addOrganism(new Guarana(x, y, world));
    }
}
