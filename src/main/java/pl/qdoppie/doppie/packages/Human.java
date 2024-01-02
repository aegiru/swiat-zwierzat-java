package pl.qdoppie.doppie.packages;

public class Human extends Animal {

    public Human(int x, int y, World world) {
        super(x, y, world, 5, 4, "Human", 1);
    }

    private void activateAbility() {
        if (this.getAbilityCooldown() == 0) {
            this.setAbilityCooldown(10);
        }
    }

    private void updateAbility() {
        this.range = 1;
        int currentCooldown = this.getAbilityCooldown();

        if (currentCooldown > 7) {
            this.range = 2;
        }

        if (currentCooldown > 5) {
            if (Math.random() % 2 == 1) {
                this.range = 2;
            }
        }

        if (currentCooldown > 0) {
            this.setAbilityCooldown(currentCooldown - 1);
        }
    }

    @Override
    final public void action() {
        this.updateAbility();

        String input = this.getWorld().getGUI().getUserInput();
        int xDiff = 0;
        int yDiff = 0;

        if (input.equals("w")) {
            yDiff -= (this.range);
        } else if (input.equals("s")) {
            yDiff += (this.range);
        } else if (input.equals("a")) {
            xDiff -= (this.range);
        } else if (input.equals("d")) {
            xDiff += (this.range);
        } else if (input.equals("e")) {
            this.activateAbility();
        } else {
            return;
        }

        if (this.wouldBeOutOfBounds(this.getX() + xDiff, this.getY() + yDiff)) {
            return;
        }

        Organism potentialCollision = this.getWorld().getOrganism(this.getX() + xDiff, this.getY() + yDiff);

        if (this.collision(potentialCollision)) {
            this.setX(this.getX() + xDiff);
            this.setY(this.getY() + yDiff);
        }
    }

    @Override
    final protected void copy(int x, int y, World world) {
        return;
    }

    @Override
    final public boolean isHuman() {
        return true;
    }
}
