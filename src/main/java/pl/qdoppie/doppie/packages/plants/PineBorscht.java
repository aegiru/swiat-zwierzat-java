package pl.qdoppie.doppie.packages.plants;

import pl.qdoppie.doppie.packages.Organism;
import pl.qdoppie.doppie.packages.Plant;
import pl.qdoppie.doppie.packages.World;

public class PineBorscht extends Plant {
    public PineBorscht(int x, int y, World world) {
        super(x, y, world, 10, 0, "PineBorscht", 1);
    }

    @Override
    final public boolean isEatenBy(Organism org) {
        org.dies();
        return super.isEatenBy(org);
    }

    private void kills(Organism org) {
        this.getWorld().getGUI().logPineBorscht(this, org);
        org.dies();
    }

    @Override
    final public void action() {
        if (!this.getWorld().isEmpty(this.getX() - 1, this.getY())) {
            if (this.getWorld().getOrganism(this.getX() - 1, this.getY()).isAnimal()) {
                this.kills(this.getWorld().getOrganism(this.getX() - 1, this.getY()));
            }
        }

        if (!this.getWorld().isEmpty(this.getX() + 1, this.getY())) {
            if (this.getWorld().getOrganism(this.getX() + 1, this.getY()).isAnimal()) {
                this.kills(this.getWorld().getOrganism(this.getX() + 1, this.getY()));
            }
        }

        if (!this.getWorld().isEmpty(this.getX(), this.getY() - 1)) {
            if (this.getWorld().getOrganism(this.getX(), this.getY() - 1).isAnimal()) {
                this.kills(this.getWorld().getOrganism(this.getX(), this.getY() - 1));
            }
        }

        if (!this.getWorld().isEmpty(this.getX(), this.getY() + 1)) {
            if (this.getWorld().getOrganism(this.getX(), this.getY() + 1).isAnimal()) {
                this.kills(this.getWorld().getOrganism(this.getX(), this.getY() + 1));
            }
        }

        super.action();
    }

    @Override
    final protected void copy(int x, int y, World world) {
        world.addOrganism(new PineBorscht(x, y, world));
    }
}
