package pl.qdoppie.doppie.packages;

public class World {
    private final Organism[] arr;
    private final GUI gui;
    private int sizeX = 0;
    private int sizeY = 0;
    private int turn = 0;

    public World(int x, int y, GUI gui) {
        this.sizeX = x;
        this.sizeY = y;
        this.arr = new Organism[x * y];
        this.gui = gui;
    }



    public void setOrganism(Organism org) {
        for (int i = 0; i < (this.getSizeX() * this.getSizeY()); i++) {
            if (this.arr[i] == null) {
                this.arr[i] = org;
                break;
            }
        }
    }

    public void clearOrganism(int x, int y) {
        for (int i = 0; i < (this.getSizeX() * this.getSizeY()); i++) {
            if (this.arr[i] != null) {
                if (this.arr[i].getX() == x && this.arr[i].getY() == y) {
                    this.arr[i] = null;
                    break;
                }
            }
        }
    }

    public Organism getOrganism(int x, int y) {
        for (int i = 0; i < (this.getSizeX() * this.getSizeY()); i++) {
            if (this.arr[i] != null) {
                if (this.arr[i].getX() == x && this.arr[i].getY() == y) {
                    return this.arr[i];
                }
            }
        }
        return null;
    }

    public Organism getOrganism(int id) {
        return this.arr[id];
    }

    public void addOrganism(Organism org) {
        this.setOrganism(org);
    }

    public boolean isEmpty(int x, int y) {
        return this.getOrganism(x, y) == null;
    }

    private void swapOrganisms(int i, int j) {
        Organism temp = this.arr[i];
        this.arr[i] = this.arr[j];
        this.arr[j] = temp;
    }

    private boolean isOrganismHigher(int i, int j) {
        if (this.arr[i] == null && this.arr[j] == null) {
            return false;
        } else if (this.arr[i] == null) {
            return false;
        } else if (this.arr[j] == null) {
            return true;
        }

        if (this.arr[i].getInitiative() > this.arr[j].getInitiative()) {
            return true;
        } else if (this.arr[i].getInitiative() == this.arr[j].getInitiative()) {
            return this.arr[i].getAge() < this.arr[j].getAge();
        }

        return false;
    }

    private void partitionOrganisms(int hi, int lo) {
        int i = lo - 1;
        for (int j = lo; j < hi; j++) {
            if (this.isOrganismHigher(j, hi)) {
                i++;
                this.swapOrganisms(i, j);
            }
        }

        this.swapOrganisms(i + 1, hi);
    }

    private void sortOrganisms(int hi, int lo) {
        if (lo < hi) {
            int pi = (hi + lo) / 2;

            this.partitionOrganisms(hi, lo);

            this.sortOrganisms(pi - 1, lo);
            this.sortOrganisms(hi, pi + 1);
        }
    }

    private void sortOrganisms() {
        this.sortOrganisms(this.arr.length - 1, 0);
    }

    public void doTurn() {
        this.setTurn(this.getTurn() + 1);

        this.sortOrganisms();

        for (int i = 0; i < (this.getSizeX() * this.getSizeY()); i++) {
            if (this.arr[i] != null) {
                this.arr[i].setAge(this.arr[i].getAge() + 1);
                this.arr[i].action();
            }
        }
    }

    public int getOrganismCount() {
        int c = 0;

        for (int i = 0; i < (this.getSizeX() * this.getSizeY()); i++) {
            if (this.arr[i] != null) {
                c++;
            }
        }

        return c;
    }

    public int getTurn() {
        return this.turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public GUI getGUI() {
        return this.gui;
    }

    public String outputString() {
        String output = "";

        output += (this.getSizeX() + "\n");
        output += (this.getSizeY() + "\n");
        output += (this.getTurn() + "\n");
        output += (this.getOrganismCount() + "\n");

        for (int i = 0; i < this.arr.length; i++) {
            if (this.arr[i] != null) {
                output += (this.arr[i].getName() + "\n");
                output += (this.arr[i].getX() + "\n");
                output += (this.arr[i].getY() + "\n");
                output += (this.arr[i].getStrength() + "\n");
                output += (this.arr[i].getAge() + "\n");
                output += (this.arr[i].getInitiative() + "\n");
                output += (this.arr[i].getRange() + "\n");
                output += (this.arr[i].getAbilityCooldown() + "\n");
            }
        }

        return output;
    }

    public int getSizeX() {
        return this.sizeX;
    }

    public int getSizeY() {
        return this.sizeY;
    }
}
