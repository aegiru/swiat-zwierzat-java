package pl.qdoppie.doppie.packages;

public abstract class Animal extends Organism {
    public Animal(int x, int y, World world, int strength, int initiative, String name, int range) {
        super(x, y, world, strength, initiative, name, range);
    }

    @Override
    public void action()  {
        int newPos[] = this.getRandomNeighbour();

        Organism potentialCollision = this.getWorld().getOrganism(newPos[0], newPos[1]);

        if (this.collision(potentialCollision)) {
            this.setX(newPos[0]);
            this.setY(newPos[1]);
        }
    }

    @Override
    public boolean collision(Organism org) {
        if (org == null) {
            return true;
        }

        if (areOfSameType(org)) {
            this.reproduction();
            return false;
        } else {
            if (org.reflectsAttack(this)) {
                return false;
            }

            if (this.wontEnter(org)) {
                return false;
            }

            if (org.isAnimal()) {
                if (this.escapesAttack()) {
                    return !this.escape(org);
                }

                if (org.escapesAttack()) {
                    return org.escape(this);
                }

                return this.fight(org);
            }

            if (org.isPlant()) {
                return this.eats(org);
            }
        }

        return false;
    }

    @Override
    public boolean isAnimal() {
        return true;
    }

    protected abstract void copy(int x, int y, World world);
}
