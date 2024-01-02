package pl.qdoppie.doppie.packages;

public abstract class Plant extends Organism {
    public Plant(int x, int y, World world, int strength, int initiative, String name, int range) {
        super(x, y, world, strength, initiative, name, range);
    }

    @Override
    public void action() {
        this.reproduction();
    }

    @Override
    public boolean collision(Organism org) {
        return true;
    }

    @Override
    public boolean isPlant() {
        return true;
    }

    @Override
    public boolean isEatenBy(Organism org) {
        this.dies();
        return true;
    }

    protected abstract void copy(int x, int y, World world);
}
