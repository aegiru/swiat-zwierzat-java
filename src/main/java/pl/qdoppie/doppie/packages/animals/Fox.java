package pl.qdoppie.doppie.packages.animals;

import pl.qdoppie.doppie.packages.World;
import pl.qdoppie.doppie.packages.Animal;
import pl.qdoppie.doppie.packages.Organism;

public class Fox extends Animal {
    public Fox(int x, int y, World world) {
        super(x, y, world, 3, 7, "Fox",  1);
    }

    @Override
    final public boolean wontEnter(Organism org) {
        if (this.getStrength() >= org.getStrength()) {
            this.getWorld().getGUI().logWontEnter(this, org);
            return true;
        }
        return false;
    }

    @Override
    final protected void copy(int x, int y, World world) {
        world.addOrganism(new Fox(x, y, world));
    }
}
