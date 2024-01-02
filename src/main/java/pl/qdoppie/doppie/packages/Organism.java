package pl.qdoppie.doppie.packages;

public abstract class Organism {
    protected int strength;
    protected int initiative;
    protected int x;
    protected int y;
    protected int age;
    protected String name;
    protected int range;
    protected World world;
    protected int abilityCooldown;

    protected Organism(int x, int y, World world, int strength, int initiative, String name, int range) {
        this.x = x;
        this.y = y;
        this.world = world;
        this.strength = strength;
        this.initiative = initiative;
        this.name = name;
        this.range = range;
        this.age = 0;
        this.abilityCooldown = 0;
    }

    protected boolean wouldBeOutOfBounds(int x, int y) {
        int maxX = this.getWorld().getSizeX();
        int maxY = this.getWorld().getSizeY();

        return (x < 0 || x >= maxX || y < 0 || y >= maxY);
    }


    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getInitiative() {
        return this.initiative;
    }

    public int getAge() {
        return this.age;
    }

    public int getStrength() {
        return this.strength;
    }

    public void action() {};

    public boolean collision(Organism org) {
        return false;
    };

    boolean areOfSameType(Organism org) {
        return this.getClass().equals(org.getClass());
    }

    public boolean reflectsAttack(Organism org) {
        return false;
    }

    public boolean escapesAttack() {
        return false;
    }

    public boolean escape(Organism org) {
        return false;
    }

    public void dies() {
        this.getWorld().clearOrganism(this.getX(), this.getY());
    }

    boolean fight(Organism org) {
        if (this.getStrength() >= org.getStrength()) {
            this.getWorld().getGUI().logKill(this, org);
            org.dies();
            return true;
        } else {
            this.getWorld().getGUI().logKill(org, this);
            this.dies();
            return false;
        }
    }

    public boolean isEatenBy(Organism org) {
        return false;
    }

    boolean eats(Organism plant) {
        this.getWorld().getGUI().logEating(this, plant);
        return plant.isEatenBy(this);
    }

    public World getWorld() {
        return world;
    }

    public void reproduction() {
        if (this.age < 5) {
            return;
        }

        if (Math.random() < 0.1) {
            return;
        }

        int[] neighbour = this.getRandomNeighbour();

        if (this.getWorld().isEmpty(neighbour[0], neighbour[1])) {
            this.copy(neighbour[0], neighbour[1], this.getWorld());
        }
    }

    protected int[] getRandomNeighbour() {
        int[] neighbour = new int[2];
        int x = this.getX();
        int y = this.getY();

        int rand = (int) (Math.random() * 4);

        switch (rand) {
            case 0:
                neighbour[0] = x;
                neighbour[1] = y - 1;
                break;
            case 1:
                neighbour[0] = x + 1;
                neighbour[1] = y;
                break;
            case 2:
                neighbour[0] = x;
                neighbour[1] = y + 1;
                break;
            case 3:
                neighbour[0] = x - 1;
                neighbour[1] = y;
                break;
        }

        if (this.wouldBeOutOfBounds(neighbour[0], neighbour[1])) {
            return this.getRandomNeighbour();
        }

        return neighbour;
    }

    protected abstract void copy(int x, int y, World world);

    public boolean wontEnter(Organism org) {
        return false;
    }

    public boolean isPlant() {
        return false;
    }

    public boolean isAnimal() {
        return false;
    }

    public boolean isHuman() {
        return false;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
    	this.y = y;
    }

    public void setStrength(int strength) {
    	this.strength = strength;
    }

    public String getName() {
    	return this.name;
    }

    public int getRange() {
    	return this.range;
    }

    public void setRange(int range) {
    	this.range = range;
    }

    public void setInitiative(int initiative) {
    	this.initiative = initiative;
    }

    public void setAbilityCooldown(int cooldown) {
        this.abilityCooldown = cooldown;
    }

    public int getAbilityCooldown() {
        return this.abilityCooldown;
    }
}
