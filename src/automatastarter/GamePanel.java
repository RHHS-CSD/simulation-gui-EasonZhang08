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
 * @author Eason Zhang
 */
public class GamePanel extends javax.swing.JPanel implements MouseListener {

    public static final String CARD_NAME = "game";

    CardSwitcher switcher; // This is the parent panel
    Timer animTimer;
    BufferedImage[] images;
    
    //determine whether or not the game is paused
    boolean isGamePaused = false;
    //the main grid
    PredatorPreyGrid grid;
    boolean lineDrawed = false;
    //this is size for each cell in the grid
    int cellSize;

    /**
     * Creates new form GamePanel
     */
    public GamePanel(CardSwitcher p) {
        initComponents();

        //initialize
        grid = new PredatorPreyGrid();
        //calculate the cellsize according to the panel size
        // -200 is because the 200 pixel on the right is for controls and not the grid
        cellSize = (getPreferredSize().width-200)/grid.GRIDSIZE;
        
        //load the images
        images = new BufferedImage[4];      
        images[0] = ImageUtil.loadAndResizeImage("prey.png", cellSize, cellSize);
        images[1] = ImageUtil.loadAndResizeImage("predator.png", cellSize, cellSize);
        images[2] = ImageUtil.loadAndResizeImage("habitat.png", cellSize, cellSize);
        images[3] = ImageUtil.loadAndResizeImage("apple.png", cellSize, cellSize);
        //initialize the grid
        grid.initializeGrid();
     
        this.setFocusable(true);

        // tell the program we want to listen to the mouse
        addMouseListener(this);
        //tells us the panel that controls this one
        switcher = p;
        //create and start a Timer for animation
        animTimer = new Timer(speedSlider.getValue(), new AnimTimerTick());
        animTimer.start();

        //set up the key bindings
        setupKeys();

    }

    private void setupKeys() {
        //these lines map a physical key, to a name, and then a name to an 'action'.  You will change the key, name and action to suit your needs
        //enter -> update the grid once
        this.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enter");
        this.getActionMap().put("enter", new Move("enter"));

        //space -> pause/continue program
        this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "space");
        this.getActionMap().put("space", new Move("space"));

    }

    /**
     * draw everything in the screen, including the simulation grid and the line to seperate parts of the panel
     * @param g graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //draw the grid
        if (grid != null) {
            grid.drawGrid(g, cellSize, images);
        }
        // draw the line to seperate controller and the grid
        // only draw this once
        if (!lineDrawed){
            g.drawLine(getPreferredSize().width - 200, 0, getPreferredSize().width - 200, getPreferredSize().height);
            lineDrawed = false;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        controllerLabel = new javax.swing.JLabel();
        addEntityButton = new javax.swing.JButton();
        entityComboBox = new javax.swing.JComboBox<>();
        numEntityTextField = new javax.swing.JTextField();
        numEntityLabel = new javax.swing.JLabel();
        speedSlider = new javax.swing.JSlider();
        sliderLabel = new javax.swing.JLabel();
        resetButton = new javax.swing.JButton();
        randomizeButton = new javax.swing.JButton();
        patternComboBox = new javax.swing.JComboBox<>();
        applyPatternButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        entitiesCountLabel = new javax.swing.JLabel();
        stateLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(900, 700));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(900, 700));
        setRequestFocusEnabled(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        controllerLabel.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        controllerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        controllerLabel.setText("Controller");

        addEntityButton.setText("Add Entity");
        addEntityButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEntityButtonActionPerformed(evt);
            }
        });

        entityComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Prey", "Predator", "Habitat", "Food" }));

        numEntityTextField.setText("1");
        numEntityTextField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        numEntityLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numEntityLabel.setText("Number of entity spawns");

        speedSlider.setMaximum(2000);
        speedSlider.setMinimum(100);
        speedSlider.setValue(500);

        sliderLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sliderLabel.setText("Speed of the simulation");

        resetButton.setText("Reset Board");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        randomizeButton.setText("Randomize Board");
        randomizeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randomizeButtonActionPerformed(evt);
            }
        });

        patternComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pattern 1", "Pattern 2", "Pattern 3", "Pattern 4" }));

        applyPatternButton.setText("Apply Pattern");
        applyPatternButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyPatternButtonActionPerformed(evt);
            }
        });

        clearButton.setText("Clear Board");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        entitiesCountLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        entitiesCountLabel.setText("Entities Left:");

        stateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stateLabel.setText("Current State: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(719, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(speedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(numEntityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(numEntityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(entityComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addEntityButton, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sliderLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                            .addComponent(controllerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(2, 2, 2)))
                    .addComponent(applyPatternButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(patternComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(randomizeButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resetButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clearButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(entitiesCountLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(stateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(controllerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addEntityButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(entityComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(numEntityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numEntityLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24)
                .addComponent(speedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sliderLabel)
                .addGap(41, 41, 41)
                .addComponent(applyPatternButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(patternComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(160, 160, 160)
                .addComponent(clearButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resetButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(randomizeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(entitiesCountLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stateLabel)
                .addGap(33, 33, 33))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
 
    }//GEN-LAST:event_formComponentShown

    private void addEntityButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEntityButtonActionPerformed
        // TODO add your handling code here:
        // get the index of the combo box in order to determin the type of the entity
        int entityIndex = entityComboBox.getSelectedIndex();
        //try and catch because the input might not be integer
        try{
            //randomly spawn entities for the inputed number of times
            for (int i = 0; i < Integer.parseInt(numEntityTextField.getText()); i++){
                grid.spawnEntity(1, entityIndex + 1);
            }  
        } catch (Exception e){
            System.out.println("Input is not an integer");
        }
        
            
    }//GEN-LAST:event_addEntityButtonActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        // TODO add your handling code here:
        //reset -> initialize the grid again
        grid.initializeGrid();
    }//GEN-LAST:event_resetButtonActionPerformed

    private void randomizeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randomizeButtonActionPerformed
        // TODO add your handling code here:
        // randomize the grid -> doesn't reset the number of entities
        grid.randomizeGrid();
    }//GEN-LAST:event_randomizeButtonActionPerformed

    private void applyPatternButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyPatternButtonActionPerformed
        // TODO add your handling code here:
        //create the pattern selected
        grid.initializePattern(patternComboBox.getSelectedIndex() +1);
    }//GEN-LAST:event_applyPatternButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        // TODO add your handling code here:
        //clear the screen
        grid.clear();
    }//GEN-LAST:event_clearButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addEntityButton;
    private javax.swing.JButton applyPatternButton;
    private javax.swing.JButton clearButton;
    private javax.swing.JLabel controllerLabel;
    private javax.swing.JLabel entitiesCountLabel;
    private javax.swing.JComboBox<String> entityComboBox;
    private javax.swing.JLabel numEntityLabel;
    private javax.swing.JTextField numEntityTextField;
    private javax.swing.JComboBox<String> patternComboBox;
    private javax.swing.JButton randomizeButton;
    private javax.swing.JButton resetButton;
    private javax.swing.JLabel sliderLabel;
    private javax.swing.JSlider speedSlider;
    private javax.swing.JLabel stateLabel;
    // End of variables declaration//GEN-END:variables

    /**
     * This event captures a click which is defined as pressing and releasing in
     * the same area
     *
     * @param me the mouse click event
     */
    public void mouseClicked(MouseEvent me) {
        //try and catch because the user can click on the controller area which is not in the grid
        try{
            //calculate where the user clicked
            int gridCol = me.getX()/cellSize;
            int gridRow = me.getY()/cellSize;

            //if it's already food, then it need to loop back to none
            if (grid.grid[gridRow][gridCol] ==  grid.FOOD){
                grid.changeEntity(gridRow, gridCol, grid.NONE);
            } else {
                //otherwise just loop to the next entity
                grid.changeEntity(gridRow, gridCol, grid.grid[gridRow][gridCol]+1);
            }

        } catch(Exception e) {
            //this means the user clicked on the controller area
            System.out.println("Mouse out of bounds");
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
            switch(key){
                case "enter": grid.updateGrid(); repaint(); break; //update the grid once
                case "space": isGamePaused = !isGamePaused; break; // pause/continue program
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
                //if the game is running, then tell the user that it's active
                stateLabel.setText("Current State: Active");
                //keep on updating the grid
                grid.updateGrid();
            } else {
                //otherwise tell the user the game is paused and don't update
                stateLabel.setText("Current State: Paused");
            }
            //the speed of the program is set to the slider's value
            animTimer.setDelay(speedSlider.getValue());
            //update the entitiesCountLabel
            entitiesCountLabel.setText("Entities Left: " + grid.totalEntities);
            //force redraw
            repaint();
        }
    }
}
