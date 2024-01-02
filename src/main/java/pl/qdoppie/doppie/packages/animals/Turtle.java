package pl.qdoppie.doppie.packages.animals;

import pl.qdoppie.doppie.packages.Animal;
import pl.qdoppie.doppie.packages.Organism;
import pl.qdoppie.doppie.packages.World;

public class Turtle extends Animal {
    public Turtle (int x, int y, World world) {
        super(x, y, world, 2, 1, "Turtle",  1);
    }

    @Override
    final public void action() {
        if (Math.random() < 0.75) {
            return;
        }

        super.action();
    }

    @Override
    final public boolean reflectsAttack(Organism org) {
        if (org.getStrength() < 5) {
            this.getWorld().getGUI().logReflect(this, org);
            return true;
        }

        return false;
    }

    @Override
    final protected void copy(int x, int y, World world) {
        world.addOrganism(new Turtle(x, y, world));
    }
}
