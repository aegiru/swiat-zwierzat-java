package pl.qdoppie.doppie.packages;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import pl.qdoppie.doppie.packages.animals.*;
import pl.qdoppie.doppie.packages.plants.*;

public class GUI {
    private JFrame window;
    private JPanel content;
    private JPanel tilePannel;
    private JButton[][] tiles;
    private JTextArea logArea;
    private JTextArea currentInput;
    private JScrollPane inputPane;
    private JScrollPane scrollPane;
    private JButton nextTurnButton;
    private JButton saveButton;
    private JButton loadButton;
    private int sizeX;
    private int sizeY;
    private int index;
    private String firstName;
    private String lastName;
    private World world;
    private static final String[] names = {"Empty", "Wolf", "Sheep", "Fox", "Turtle", "Antilope", "Grass", "Dandelion", "Guarana", "Belladonna", "PineBorscht", "Human"};
    private String userInput;

    public GUI(int sizeX, int sizeY, int index, String firstName, String lastName) {
        this.world = new World(sizeX, sizeY, this);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.index = index;
        this.firstName = firstName;
        this.lastName = lastName;

        this.window = new JFrame("Animal World - " + firstName + " " + lastName + " - " + index);
        this.window.setSize(1920, 1080);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.content = new JPanel();
        this.content.setLayout(new FlowLayout(FlowLayout.LEADING));
        this.window.setContentPane(this.content);

        this.logArea = new JTextArea();
        this.logArea.setEditable(false);
        this.scrollPane = new JScrollPane(this.logArea);
        this.scrollPane.setPreferredSize(new Dimension(500, 1000));
        this.content.add(this.scrollPane);

        this.tilePannel = new JPanel();
        this.tilePannel.setLayout(new GridLayout(sizeX, sizeY));
        this.tilePannel.setPreferredSize(new Dimension(1000, 1000));
        this.content.add(this.tilePannel);

        this.nextTurnButton = new JButton("Next turn");
        this.nextTurnButton.addActionListener(e -> this.nextTurn());
        this.content.add(this.nextTurnButton);

        this.currentInput = new JTextArea();
        this.currentInput.setEditable(false);
        this.inputPane = new JScrollPane(this.currentInput);
        this.inputPane.setPreferredSize(new Dimension(150, 40));
        this.content.add(this.inputPane);

        this.saveButton = new JButton("Save");
        this.saveButton.addActionListener(e -> this.saveToFile());
        this.content.add(this.saveButton);

        this.loadButton = new JButton("Load");
        this.loadButton.addActionListener(e -> this.loadFromFile());
        this.content.add(this.loadButton);

        this.window.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                userInput = String.valueOf(e.getKeyChar()).toLowerCase();
                currentInput.setText("CURRENT INPUT: " + userInput.toUpperCase());
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> userInput = "w";
                    case KeyEvent.VK_DOWN -> userInput = "s";
                    case KeyEvent.VK_LEFT -> userInput = "a";
                    case KeyEvent.VK_RIGHT -> userInput = "d";
                }

                if (userInput != null) {
                    currentInput.setText("CURRENT INPUT: " + userInput.toUpperCase());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        this.tiles = new JButton[sizeX][sizeY];
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                JButton tileButton = new JButton();
                tileButton.setFont(new Font("Arial", Font.PLAIN, 8));
                tileButton.setMargin(new Insets(0, 0, 0, 0));
                int finalX = x;
                int finalY = y;
                tileButton.addActionListener(e -> handleButtonPress(finalX, finalY));
                this.tilePannel.add(tileButton);
                this.tiles[finalX][finalY] = tileButton;
            }
        }

        updateNames();

        this.window.setFocusable(true);
        this.window.requestFocus();

        this.window.setVisible(true);

        this.tilePannel.revalidate();
        this.tilePannel.repaint();
        this.window.revalidate();
        this.window.repaint();
    }

    private void nextTurn() {
        this.logArea.setText("Turn " + this.world.getTurn() + "\n" + "\n");

        this.world.doTurn();

        this.updateNames();

        this.window.requestFocus();
    }

    private void updateNames() {
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if (this.getWorld().getOrganism(x, y) != null) {
                    this.tiles[x][y].setText(this.getWorld().getOrganism(x, y).getName());
                } else {
                    this.tiles[x][y].setText("X");
                }
            }
        }
    }

    private void updateMapSize(int x, int y) {
        this.sizeX = x;
        this.sizeY = y;
        this.tilePannel.removeAll();
        this.tilePannel.setLayout(new GridLayout(sizeX, sizeY));
        this.tilePannel.setPreferredSize(new Dimension(1000, 1000));

        this.tiles = new JButton[sizeX][sizeY];
        for (int iy = 0; iy < sizeY; iy++) {
            for (int ix = 0; ix < sizeX; ix++) {
                JButton tileButton = new JButton();
                tileButton.setFont(new Font("Arial", Font.PLAIN, 8));
                tileButton.setMargin(new Insets(0, 0, 0, 0));
                int finalX = ix;
                int finalY = iy;
                tileButton.addActionListener(e -> handleButtonPress(finalX, finalY));
                this.tilePannel.add(tileButton);
                this.tiles[finalX][finalY] = tileButton;
            }
        }

        this.tilePannel.revalidate();
        this.tilePannel.repaint();
        this.window.revalidate();
        this.window.repaint();
    }

    private void log(String message) {
        this.logArea.append(message + "\n");
    }


    private static JComponent twoColumns(String[] labelStrings, JComponent[] fields) {
        JLabel[] labels = new JLabel[labelStrings.length];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel(labelStrings[i]);
        }

        JComponent panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        GroupLayout.Group yLabelGroup = layout.createParallelGroup();
        hGroup.addGroup(yLabelGroup);
        GroupLayout.Group yFieldGroup = layout.createParallelGroup();
        hGroup.addGroup(yFieldGroup);
        layout.setHorizontalGroup(hGroup);
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        layout.setVerticalGroup(vGroup);
        int p = GroupLayout.PREFERRED_SIZE;

        for (JLabel label : labels) {
            yLabelGroup.addComponent(label);
        }

        for (JComponent field : fields) {
            yFieldGroup.addComponent(field, p, p, p);
        }

        for (int l = 0; l < labels.length; l++) {
            vGroup.addGroup(layout.createParallelGroup().
                    addComponent(labels[l]).
                    addComponent(fields[l], p, p, p));
        }

        return panel;
    }

    private Organism interpretOrganism(String name, int x, int y, World world) {
        return switch (name) {
            case "Wolf" -> new Wolf(x, y, world);
            case "Sheep" -> new Sheep(x, y, world);
            case "Fox" -> new Fox(x, y, world);
            case "Turtle" -> new Turtle(x, y, world);
            case "Antilope" -> new Antilope(x, y, world);
            case "Belladonna" -> new Belladonna(x, y, world);
            case "Grass" -> new Grass(x, y, world);
            case "Dandelion" -> new Dandelion(x, y, world);
            case "Guarana" -> new Guarana(x, y, world);
            case "PineBorscht" -> new PineBorscht(x, y, world);
            case "Human" -> new Human(x, y, world);
            default -> null;
        };
    }

    private Organism orgDialog(Organism org, int x, int y) {
        String[] labels = {"Type", "Strength", "Initiative", "Age", "Range"};
        JComponent nameBox = new JComboBox<>(names);
        JTextField strengthBox = new JTextField("0", 9);
        JTextField initiativeBox = new JTextField("0", 9);
        JTextField ageBox = new JTextField("0", 9);
        JTextField rangeBox = new JTextField("1", 9);
        JComponent[] fields = {nameBox, strengthBox, initiativeBox, ageBox, rangeBox};


        if (org != null) {
            ((JComboBox) nameBox).setSelectedItem(org.getName());
            strengthBox.setText(Integer.toString(org.getStrength()));
            initiativeBox.setText(Integer.toString(org.getInitiative()));
            ageBox.setText(Integer.toString(org.getAge()));
            rangeBox.setText(Integer.toString(org.getRange()));
        } else {
            ((JComboBox) nameBox).setSelectedItem("Empty");
        }

        JOptionPane.showMessageDialog(null,
                twoColumns(labels, fields),
                "Edit Organism",
                JOptionPane.PLAIN_MESSAGE);

        String name = (String) ((JComboBox) nameBox).getSelectedItem();
        int strength = Integer.parseInt(strengthBox.getText());
        int initiative = Integer.parseInt(initiativeBox.getText());
        int age = Integer.parseInt(ageBox.getText());
        int range = Integer.parseInt(rangeBox.getText());

        Organism newOrg = interpretOrganism(name, x, y, this.getWorld());

        if (newOrg != null) {
            newOrg.setStrength(strength);
            newOrg.setInitiative(initiative);
            newOrg.setAge(age);
            newOrg.setRange(range);
        }

        return newOrg;
    }

    private void handleButtonPress(int x, int y) {
        Organism org = this.world.getOrganism(x, y);

        Organism newOrg = orgDialog(org, x, y);

        if (org != null) {
            org.getWorld().clearOrganism(x, y);
        }

        if (newOrg != null) {
            newOrg.getWorld().setOrganism(newOrg);
        }

        this.updateNames();

        this.window.requestFocus();
    }

    public World getWorld() {
        return world;
    }

    private void logAction(Organism subject, Organism object, String action) {
        int subX = subject.getX();
        int subY = subject.getY();
        int objX = object.getX();
        int objY = object.getY();

        this.log("[" + subX + "," + subY + "] " + subject.getName() + " " + action + " [" + objX + "," + objY + "] " + object.getName());
    }

    public void logEating(Organism eater, Organism eaten) {
        this.logAction(eater, eaten, "ate");
    }

    public void logKill(Organism killer, Organism killed) {
        this.logAction(killer, killed, "killed");
    }

    public void logPineBorscht(Organism pineBorscht, Organism stepper) {
        this.logAction(stepper, pineBorscht, "stepped on");
    }

    public void logReflect(Organism turtle, Organism reflected) {
        this.logAction(turtle, reflected, "reflected");
    }

    public void logEscape(Organism escaper, Organism chaser) {
        this.logAction(escaper, chaser, "escaped from");
    }

    public void logWontEnter(Organism fox, Organism other) {
        this.logAction(fox, other, "wont enter");
    }

    public String getUserInput() {
        return this.userInput;
    }

    private void saveToFile() {
        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        j.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int r = j.showSaveDialog(null);
        if (r == JFileChooser.APPROVE_OPTION) {
            File fil = j.getSelectedFile();

            if (fil == null) {
                return;
            }

            FileWriter fw = null;
            try {
                fw = new FileWriter(fil, false);
                fw.write(this.world.outputString());
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fw != null) {
                    try {
                        fw.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        this.window.requestFocus();
    }

    private void loadFromFile() {
        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        j.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int r = j.showOpenDialog(null);
        if (r == JFileChooser.APPROVE_OPTION) {
            File fil = j.getSelectedFile();

            if (fil == null) {
                return;
            }


            try {
                Scanner scan = new Scanner(fil);

                int x = Integer.parseInt(scan.nextLine().trim());
                int y = Integer.parseInt(scan.nextLine().trim());
                int turn = Integer.parseInt(scan.nextLine().trim());
                int organismCount = Integer.parseInt(scan.nextLine().trim());

                this.world = new World(x, y, this);
                this.world.setTurn(turn);

                for (int i = 0; i < organismCount; i++) {
                    String name = scan.nextLine().trim();
                    int posX = Integer.parseInt(scan.nextLine().trim());
                    int posY = Integer.parseInt(scan.nextLine().trim());
                    int strength = Integer.parseInt(scan.nextLine().trim());
                    int age = Integer.parseInt(scan.nextLine().trim());
                    int initiative = Integer.parseInt(scan.nextLine().trim());
                    int range = Integer.parseInt(scan.nextLine().trim());
                    int cooldown = Integer.parseInt(scan.nextLine().trim());

                    Organism org = interpretOrganism(name, posX, posY, this.world);
                    org.setStrength(strength);
                    org.setInitiative(initiative);
                    org.setAge(age);
                    org.setRange(range);
                    org.setAbilityCooldown(cooldown);
                    this.world.setOrganism(org);
                }

                this.sizeX = x;
                this.sizeY = y;

                this.updateMapSize(x, y);
                this.updateNames();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.window.requestFocus();
    }
}
