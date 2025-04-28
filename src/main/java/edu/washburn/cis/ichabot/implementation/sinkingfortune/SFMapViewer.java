package edu.washburn.cis.ichabot.implementation.sinkingfortune;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Set;
import java.io.*;
import java.util.Comparator;

public class SFMapViewer extends JPanel {
    private final SFMap map;
    private final SFSinglePlayerEnvironment env;
    private int cellSize = 50;
    private ImageIcon spawnIcon;
    private ImageIcon exitIcon;
    private int maxHeight;
    private int waterLevel;

    public SFMapViewer(SFSinglePlayerEnvironment env, int cellSize) {
        this.env = env;
        this.map = env.getMap();
        this.cellSize = cellSize;
        this.maxHeight = map.tileHeight()
            .values().stream().max(Comparator.naturalOrder()).get();

        this.spawnIcon = new ImageIcon(
            this.getClass().getResource("/images/spawn.png"));
        this.spawnIcon = new ImageIcon(spawnIcon.getImage()
            .getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));
        this.exitIcon = new ImageIcon(
                this.getClass().getResource("/images/exit.png"));
        this.exitIcon = new ImageIcon(exitIcon.getImage()
            .getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));
        setPreferredSize(new Dimension(map.width() * cellSize, 
                                       map.height() * cellSize));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.waterLevel = env.getWaterLevel();
        Graphics2D g2 = (Graphics2D) g;

        for (int r = 0; r < map.height(); r++) {
            for (int c = 0; c < map.width(); c++) {
                SFCoordinates coord = SFCoordinates.coordinates(r, c);

                g2.setColor(interpolateWaterColor(
                                map.tileHeight().get(coord)));
                g2.fillRect(c * cellSize, r * cellSize, cellSize, cellSize);

                g2.setColor(Color.BLACK);
                g2.drawRect(c * cellSize, r * cellSize, cellSize, cellSize);

                if (map.spawnPoints().contains(coord)) {
                    spawnIcon.paintIcon(this, g2, c * cellSize + 5, r * cellSize + 5);
                } else if (map.exits().contains(coord)) {
                    exitIcon.paintIcon(this, g2, c * cellSize + 5, r * cellSize + 5);
                }
                
                Integer goldAmount = map.gold().get(coord);
                if (goldAmount != null && goldAmount > 0) {
                    g2.setColor(Color.YELLOW);
                    g2.setFont(new Font("Arial", Font.BOLD, 16));
                    g2.drawString(goldAmount.toString(), c * cellSize + cellSize / 2 - 8, r * cellSize + cellSize / 2 + 8);
                }
            }
        }
    }

    private Color interpolateWaterColor(int height) {
        double pct = (double)(height - waterLevel)/(double)maxHeight;
        int red = (int) (255 * pct);
        int green = (int) (255 * (1 - pct));
        return pct > 0 ? new Color(red, green, 0) : new Color(0, 0, 255);
    }
}