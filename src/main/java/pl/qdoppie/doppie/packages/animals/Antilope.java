package pl.qdoppie.doppie.packages.animals;

import pl.qdoppie.doppie.packages.World;
import pl.qdoppie.doppie.packages.Animal;
import pl.qdoppie.doppie.packages.Organism;

public class Antilope extends Animal {
    public Antilope(int x, int y, World world) {
        super(x, y, world, 4, 4, "Antilope", 1);
    }

    @Override
    final public boolean escapesAttack() {
        return (Math.random() <= 0.5);
    }

    @Override
    final public boolean escape(Organism org) {
        boolean didEscape = false;

        if (this.getWorld().isEmpty(this.getX() - 1, this.getY())) {
            this.setX(this.getX() - 1);
            didEscape = true;
        } else if (this.getWorld().isEmpty(this.getX() + 1, this.getY())) {
            this.setX(this.getX() + 1);
            didEscape = true;
        } else if (this.getWorld().isEmpty(this.getX(), this.getY() - 1)) {
            this.setY(this.getY() - 1);
            didEscape = true;
        } else if (this.getWorld().isEmpty(this.getX(), this.getY() + 1)) {
            this.setY(this.getY() + 1);
            didEscape = true;
        }

        if (didEscape) {
            this.getWorld().getGUI().logEscape(this, org);
        }

        return didEscape;
    }

    @Override
    final protected void copy(int x, int y, World world) {
        world.addOrganism(new Antilope(x, y, world));
    }
}
