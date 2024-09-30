/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatastarter;


import java.awt.Color;
import utils.CardSwitcher;
import utils.ImageUtil;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/**
 *
 * @author michael.roy-diclemen
 */
public class GamePanel extends javax.swing.JPanel implements MouseListener {

    public static final String CARD_NAME = "game";

    CardSwitcher switcher; // This is the parent panel
    Timer animTimer;
    //Image predator = Toolkit.getDefaultToolkit().getImage("predator.png");
    //Image prey = Toolkit.getDefaultToolkit().getImage("prey.webp");
    BufferedImage[] images;

    boolean isGamePaused = false;
    PredatorPreyGrid grid;

    /**
     * Creates new form GamePanel
     */
    public GamePanel(CardSwitcher p) {
        initComponents();

        images = new BufferedImage[4];
        grid = new PredatorPreyGrid(getPreferredSize().width);
        grid.initializeGrid();
        
        
        this.setFocusable(true);

        // tell the program we want to listen to the mouse
        addMouseListener(this);
        //tells us the panel that controls this one
        switcher = p;
        //create and start a Timer for animation
        animTimer = new Timer(1000, new AnimTimerTick());
        animTimer.start();

        //set up the key bindings
        setupKeys();

    }

    private void setupKeys() {
        //these lines map a physical key, to a name, and then a name to an 'action'.  You will change the key, name and action to suit your needs
        this.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enter");
        this.getActionMap().put("enter", new Move("enter"));

        this.getInputMap().put(KeyStroke.getKeyStroke("N"), "nKey");
        this.getActionMap().put("nKey", new Move("n"));

        this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "space");
        this.getActionMap().put("space", new Move("space"));

        this.getInputMap().put(KeyStroke.getKeyStroke("X"), "xKey");
        this.getActionMap().put("xKey", new Move("x"));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (grid != null) {
            grid.drawGrid(g, images);
        }
        g.setColor(Color.black);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(500, 500));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(500, 500));
        setRequestFocusEnabled(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
 
    }//GEN-LAST:event_formComponentShown


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    /**
     * This event captures a click which is defined as pressing and releasing in
     * the same area
     *
     * @param me
     */
    public void mouseClicked(MouseEvent me) {
        int gridCol = me.getX()/grid.cellSize;
        int gridRow = me.getY()/grid.cellSize;
        
        if (grid.grid[gridRow][gridCol] ==  grid.NONE){
            grid.addEntity(gridRow, gridCol, grid.PREY);
        } else if (grid.grid[gridRow][gridCol] ==  grid.PREY){
            grid.removeEntity(gridRow, gridCol, grid.PREY);
            grid.addEntity(gridRow, gridCol, grid.PREDATOR);
        } else if (grid.grid[gridRow][gridCol] ==  grid.PREDATOR){
            grid.removeEntity(gridRow, gridCol, grid.PREDATOR);
            grid.addEntity(gridRow, gridCol, grid.HABITAT);
        } else if (grid.grid[gridRow][gridCol] ==  grid.HABITAT){
            grid.addEntity(gridRow, gridCol, grid.FOOD);
        } else {
            grid.addEntity(gridRow, gridCol, grid.NONE);
        }
        

    }

    /**
     * When the mountain is pressed
     *
     * @param me
     */
    public void mousePressed(MouseEvent me) {

    }

    /**
     * When the mouse button is released
     *
     * @param me
     */
    public void mouseReleased(MouseEvent me) {

    }

    /**
     * When the mouse enters the area
     *
     * @param me
     */
    public void mouseEntered(MouseEvent me) {

    }

    /**
     * When the mouse exits the panel
     *
     * @param me
     */
    public void mouseExited(MouseEvent me) {

    }

    /**
     * Everything inside here happens when you click on a captured key.
     */
    private class Move extends AbstractAction {

        String key;

        public Move(String akey) {
            key = akey;
        }

        public void actionPerformed(ActionEvent ae) {
            // here you decide what you want to happen if a particular key is pressed
            System.out.println("llll" + key);
            switch(key){
                case "d": System.out.println(); break;
                case "enter": grid.updateGrid(); repaint(); break;
                case "n": animTimer.stop(); switcher.switchToCard(EndPanel.CARD_NAME); break;
                case "space": isGamePaused = !isGamePaused; break;
            }
            
        }

    }

    /**
     * Everything inside this actionPerformed will happen every time the
     * animation timer clicks.
     */
    private class AnimTimerTick implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            if (isGamePaused){
                grid.updateGrid();
            }
            
            //force redraw
            repaint();
        }
    }
}
