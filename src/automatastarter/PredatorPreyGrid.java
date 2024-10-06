/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package automatastarter;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.*;
import java.lang.*;
import utils.ImageUtil;

/**
 *
 * @author easonzhang
 */
public class PredatorPreyGrid {
    
    //2d grid
    int[][] grid;
    final int GRIDSIZE = 20;
    //the number of preys and predators to start with
    final int INITIALPREYS = 100;
    final int INITIALPREDATORS = 10;
    final int INITIALHABITAT = 10;
    final int INITIALFOOD = 20;
    
    //the predators are able to reproduce if they ate a prey in the last 10 steps
    final int REPRODUCTION_HUNGER = 10;
    //chance to reproduce
    final int PREY_REPRODUCTION_PROBABILITY = 10; // 10% chance -> preys
    final int HABITAT_BOOST_REPRODUCTION_PROBABILITY = 5; //20% chance when the prey is near a food
    final int PREDATOR_REPRODUCTION_PROBABILITY = 20; // 5% chance -> predators
    
    final int NONE = 0; // 0 represents there's nothing in that grid
    final int PREY = 1; // 1 represents prey
    final int PREDATOR = 2; // 2 -> predator
    final int HABITAT = 3; // 3 -> habitat
    final int FOOD = 4; // 4 -> food
    
    //indicate directions with ints
    final int UP = 0;
    final int LEFT = 1;
    final int DOWN = 2;
    final int RIGHT = 3;
    
    //the first list contains all entities of that kind
    //the second list 2 value, the row and the col, which is the position of the entity
    ArrayList<int[]> preys;
    ArrayList<int[]> predators;
    // records the hunger of each predator, the length of predatorHunger and predators should be the same
    ArrayList<Integer> predatorHunger;
    
    //keep track of the number of entities
    int totalEntities = 0;
    int numFood = 0;
    int numHabitat = 0;
    
    /**
     * create a new predator prey grid
     */
    public PredatorPreyGrid(){
        //create a square grid
        grid = new int[GRIDSIZE][GRIDSIZE];
        preys = new ArrayList<int[]>();
        predators = new ArrayList<int[]>();
        predatorHunger = new ArrayList<Integer>();
    }

    /**
     * Draw the grid using images
     * @param g Grpahics
     * @param cellSize size of each cell in the grid
     * @param images array of images for each element in the grid
     */
    public void drawGrid(Graphics g, int cellSize, BufferedImage[] images){
        //loop through each row and column
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                //see the position of the next cell
                int x = column * cellSize;
                int y = row * cellSize;
                //draw the border of each cell
                g.setColor(Color.black);
                g.drawRect(x, y, cellSize, cellSize);
                //on top of the border, draw the actual entity
                switch (grid[row][column]) {
                    //use the image corresponding to the entity
                    case PREY:                      
                        g.drawImage(images[0], x, y, null);
                        break;
                    case PREDATOR:
                        g.drawImage(images[1], x, y, null);
                        break;
                    case HABITAT:
                        g.drawImage(images[2], x, y, null);
                        break;
                    case FOOD:               
                        g.drawImage(images[3], x, y, null);
                        break;
                }
                
            }
        }
        
    }
    
    /**
     * Draw the grid using color blocks
     * @param g Grpahics
     * @param cellSize size of each cell in the grid
     */
    public void drawGrid(Graphics g, int cellSize){
        // loop through entire grid
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                //calculate where the next cell is
                int x = column * cellSize;
                int y = row * cellSize;

                //set each cell to their corresponding color
                switch (grid[row][column]) {
                    case NONE:
                        g.setColor(Color.WHITE);
                        break;
                    case PREY:
                        g.setColor(Color.GREEN);
                        break;
                    case PREDATOR:
                        g.setColor(Color.RED);
                        break;
                    case HABITAT:
                        g.setColor(Color.BLUE);
                        break;
                    case FOOD:
                        g.setColor(Color.YELLOW);
                        break;
                }
                g.fillRect(x, y, cellSize, cellSize);
                
            }
        }
        
    }
    
    /**
     * This function will print the current status of the grid in the console
     */
    public void printGrid(){
        //double for loop to print a 2d array
        //print the rows
        for (int row = 0; row < grid.length; row++){
            //print the columns
            for (int column = 0; column < grid[0].length; column++){
                
                switch (grid[row][column]) {
                    case NONE:
                        System.out.print("."); // --> NONE
                        break;
                    case PREY:
                        System.out.print("$"); // --> PREY
                        break;
                    case PREDATOR:
                        System.out.print("!"); // --> PREDATOR
                        break;
                    case HABITAT:
                        System.out.print("H"); // --> HABITAT
                        break;
                    case FOOD:
                        System.out.print("F"); // --> FOOD
                        break;
                    default:
                        break;
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }
    
    /**
     * Update the status of the entities
     */
    public void updateGrid(){
        //update for both preys and predators
        updateEntities(predators, PREDATOR);
        updateEntities(preys, PREY);
    }
    
    /**
     * clear the current grid, then randomly spawn each entity
     */
    public void initializeGrid(){
        //clear the board first
        clear();
        //randomly spawn preys and predators
        spawnEntity(INITIALPREYS, PREY);
        spawnEntity(INITIALPREDATORS, PREDATOR);
        spawnEntity(INITIALHABITAT, HABITAT);
        spawnEntity(INITIALFOOD, FOOD);  
    }
    
    /**
     * create the pattern inputed
     * @param pattern the index of the pattern
     */
    public void initializePattern(int pattern){
        //clear the current board
        clear();
        //set the center row and col as variables sinch many patterns use them
        int centerRow = GRIDSIZE/2;
        int centerCol = GRIDSIZE/2;
        
        switch (pattern) {
            case 1:
                //this pattern will have preys on the top and predators on the bottom
                for (int i = 0; i < grid[0].length; i++){
                    spawnEntity(PREY, 0, i);
                }   for (int i = 0; i < grid[0].length; i++){
                    spawnEntity(PREDATOR, grid.length-1, i);
                }   break;
            case 2:
                //this pattern creates a ring of predator, a ring of food and a square of preys
                {
                    int radius = 7;
                    // draw a square of predators
                    for (int row = centerRow - radius; row <= centerRow + radius; row++) {
                        for (int col = centerCol - radius; col <= centerCol + radius; col++) {
                            if (row >= 0 && row < GRIDSIZE && col >= 0 && col < GRIDSIZE) {
                                spawnEntity(PREDATOR, row, col);
                            }
                        }
                    }      
                    // in a smaller radius, erase the predatos and cover it with black spaces
                    for (int row = centerRow - radius+1; row <= centerRow + radius-1; row++) {
                        for (int col = centerCol - radius+1; col <= centerCol + radius-1; col++) {
                            if (row >= 0 && row < GRIDSIZE && col >= 0 && col < GRIDSIZE) {
                                changeEntity(row, col, NONE);
                            }
                        }
                    }     
                    // even smaller circle, cover the space with food
                    for (int row = centerRow - radius+2; row <= centerRow + radius-2; row++) {
                        for (int col = centerCol - radius+2; col <= centerCol + radius-2; col++) {
                            if (row >= 0 && row < GRIDSIZE && col >= 0 && col < GRIDSIZE) {
                                changeEntity(row, col, FOOD);
                            }
                        }
                    }       
                    // same thing for preys
                    for (int row = centerRow - radius+3; row <= centerRow + radius-3; row++) {
                        for (int col = centerCol - radius+3; col <= centerCol + radius-3; col++) {
                            if (row >= 0 && row < GRIDSIZE && col >= 0 && col < GRIDSIZE) {
                                changeEntity(row, col, PREY);
                            }
                        }
                    }       break;
                }
            case 3:
                {
                    int radius = 3;
                    //this pattern 
                    for (int row = centerRow - radius; row <= centerRow + radius; row++) {
                        for (int col = centerCol - radius; col <= centerCol + radius; col++) {
                            if (row >= 0 && row < GRIDSIZE && col >= 0 && col < GRIDSIZE) {
                                if (grid[row][col] == NONE) {
                                    grid[row][col] = FOOD;
                                    numFood++;
                                    totalEntities++;
                                }
                            }
                        }
                    }       break;
                }
            case 4:
                //spawn a block of each entity
                for (int row = 0; row < centerRow; row++) {
                    for (int col = 0; col < centerCol; col++) {
                        spawnEntity(HABITAT, row, col);
                    }
                }   for (int row = 0; row < centerRow; row++) {
                    for (int col = centerCol; col < GRIDSIZE; col++) {
                        spawnEntity(PREDATOR, row, col);
                    }
                }   for (int row = centerRow; row < GRIDSIZE; row++) {
                    for (int col = 0; col < centerCol; col++) {
                        spawnEntity(FOOD, row, col);
                    }
                }   for (int row = centerRow; row < GRIDSIZE; row++) {
                    for (int col = centerCol; col < GRIDSIZE; col++) {
                        spawnEntity(PREY, row, col);
                    }
                }   break;
            default:
                break;
        }

    }
    
    /**
     * clear the entire grid
     */
    public void clear(){
        //reset everything
        preys = new ArrayList<int[]>();
        predators = new ArrayList<int[]>();
        predatorHunger = new ArrayList<Integer>();
        totalEntities = 0;
        numFood = 0;
        numHabitat = 0;
        //double for loop, set every cell to nothing
        for (int row = 0; row < grid.length; row++){
            for (int column = 0; column < grid[0].length; column++){
                grid[row][column] = NONE;
            }
        }
    }
    
    /**
     * randomize the grid without reseting the number of entities on the grid
     */
    public void randomizeGrid(){
        //store the original number of each entity
        int preysNum = preys.size();
        preys = new ArrayList<int[]>();
        int predatorsNum = predators.size();
        predators = new ArrayList<int[]>();
        //set the total entity number to 0, this number will increase when spawning entities again
        totalEntities = 0;
        int orgNumHabitat = numHabitat;
        int orgNumFood = numFood;
        numHabitat = 0;
        numFood = 0;
        //double for loop, first set every grid to nothing
        for (int row = 0; row < grid.length; row++){
            for (int column = 0; column < grid[0].length; column++){
                grid[row][column] = NONE;
            }
        }
        //randomly spawn preys and predators
        spawnEntity(preysNum, PREY);
        spawnEntity(predatorsNum, PREDATOR);
        spawnEntity(orgNumHabitat, HABITAT);
        spawnEntity(orgNumFood, FOOD);  
        
    }
    
    /**
     * randomly spawn entities on the grid
     * @param value the number of targeted entity that's going to spawn
     * @param target the type of entity
     */
    public void spawnEntity(int value, int target){
        //keep track of how many entity has spawned
        int entitySpawned = 0;
        //keep spawning until the count hits the value
        while (entitySpawned < value && totalEntities < GRIDSIZE * GRIDSIZE){
            int randRow = (int)(Math.floor(Math.random() * GRIDSIZE));
            int randColumn = (int)(Math.floor(Math.random() * GRIDSIZE));
            //if there is nothing there, set it to the entity
            if (grid[randRow][randColumn] == NONE){
                grid[randRow][randColumn] = target;
                switch (target) {
                    case PREY:
                        //add this prey to the preys arraylist
                        preys.add(new int[]{randRow, randColumn});
                        break;
                    case PREDATOR:
                        //same thing for predators
                        predators.add(new int[]{randRow, randColumn});
                        //initialize hunger tracker for each predator
                        predatorHunger.add(0);
                        break;
                    case FOOD:
                        numFood++;
                        break;
                    case HABITAT:
                        numHabitat++;
                        break;
                    default:
                        break;
                }
                totalEntities++;
                //add one to count if it successfully spawn the entity
                entitySpawned++;
            }
        }
    }
    
    /**
     * spawn an entity at a specific position, this will only spawn the entity when the position has nothing in it
     * @param target type of entity
     * @param row row number of the grid
     * @param col column number of the grid
     */
    public void spawnEntity(int target, int row, int col){
        //if there is nothing there, set it to the entity
        if (grid[row][col] == NONE){
            grid[row][col] = target;
            switch (target) {
                case PREY:
                    //add this prey to the preys arraylist
                    preys.add(new int[]{row, col});
                    break;
                case PREDATOR:
                    //same thing for predators
                    predators.add(new int[]{row, col});
                    //initialize hunger tracker for each predator
                    predatorHunger.add(0);
                    break;
                case FOOD:
                    numFood++;
                    break;
                case HABITAT:
                    numHabitat++;
                    break;
                default:
                    break;
            }
            totalEntities++;
        }
    }
    
    private void updateEntities(ArrayList<int[]> entities, int target){
        
        //just return if the list is empty
        if (entities.isEmpty()) {
            return; 
        }
        
        //loop backward to prevent errors from removing entities
        for (int i = entities.size() - 1; i >= 0; i--){
            //move all the entites in the arraylist
            move(entities.get(i), target);

            // if it is a predator
            if (target == PREDATOR) { 
                // Increase hunger counter for each predator
                predatorHunger.set(i, predatorHunger.get(i) + 1); 

                //If hunger reaches 20, remove predator becasue it is starving to death
                if (predatorHunger.get(i) >= 20){
                    // Remove predator from grid and lists
                    removeEntity(predators.get(i)[0], predators.get(i)[1], PREDATOR);
                    continue;
                }
                // Only reproduce if they ate a prey within last 10 steps, they will have a 5% chance to reproduce
                if (predatorHunger.get(i) <= REPRODUCTION_HUNGER) { 
                    int rand = (int) Math.floor(Math.random() * PREDATOR_REPRODUCTION_PROBABILITY);
                    if (rand == 0) {
                        reproduce(entities.get(i), target);
                    }
                }
            }
        }
        
        // set the orginal size to prevent infinite reproducing
        int orgSize = entities.size();
        if (target == PREY){
            //if it is a prey, they will have a 10% chance of reproducing
            //however, if they are beside a habitat, then their reproducing chance will become 20%
            for (int i = 0; i < orgSize; i++){
                int[] directionToHabitat = detectSurrounding(entities.get(i), HABITAT);
                int rand;
                if (directionToHabitat[0] == 0 && directionToHabitat[1] == 0){
                    rand = (int)Math.floor(Math.random() * PREY_REPRODUCTION_PROBABILITY);
                } else {
                    rand = (int)Math.floor(Math.random() * HABITAT_BOOST_REPRODUCTION_PROBABILITY);
                }
                if (rand == 0){
                    //reproduce
                    reproduce(entities.get(i), target);
                }
            }
        } 
    }
    
    private void move(int[] position, int target){
        int[] directions = {UP, LEFT, DOWN, RIGHT};
        // if the move function is used on a predator
        if (target == PREDATOR) {
            //get the direction of a nearby prey
            //if there is a prey, this will have an actual value, otherwise the array will be [0,0]
            int[] predatorDirection = detectSurrounding(position, PREY);
                      
            //if there is actually a prey
            if (predatorDirection[0] != 0 || predatorDirection[1] != 0) {
                // eat the prey
                //removeEntity(position[0], position[1], PREY);
                moveEntity(position, predatorDirection[0], predatorDirection[1], target);
                totalEntities--;
                for (int i = 0; i < preys.size(); i++) {
                    int[] prey = preys.get(i);
                    //if the prey is on the given position
                    if (prey[0] == position[0] && prey[1] == position[1]) {
                        //then remove it
                        preys.remove(i);
                        //end the loop
                        break;
                    }
                }
                
                //set the hunger back to 0
                predatorHunger.set(predators.indexOf(position), 0);
            } else {
                // If no prey found, check if there are food around
                int[] foodDirection = detectSurrounding(position, FOOD);
                //if there are food around
                if (foodDirection[0] != 0 || foodDirection[1] != 0) {
                    // Move towards the food
                    moveEntity(position, foodDirection[0], foodDirection[1], target);
                    totalEntities--;
                    numFood--;
                    //immediately reproduce
                    reproduce(position, target);
                    //set the hunger back to 0
                    predatorHunger.set(predators.indexOf(position), 0);
                } else {
                    // if no food and no prey, move randomly
                    directions = shuffle(directions);
                    for (int i = 0; i < directions.length; i++) {
                        if (checkIfMovable(position, directions[i], target)) {
                            break;
                        }
                    }
                }   
            }
        } else if (target == PREY){
            // For preys, check for food first
            int[] foodDirection = detectSurrounding(position, FOOD);
            //if there are food around
            if (foodDirection[0] != 0 || foodDirection[1] != 0) {
                // Move towards the food
                moveEntity(position, foodDirection[0], foodDirection[1], target);
                totalEntities--;
                numFood--;
                //immediately reproduce
                reproduce(position, target);
            } else {
                // if no food, move randomly
                directions = shuffle(directions);
                for (int i = 0; i < directions.length; i++) {
                    if (checkIfMovable(position, directions[i], target)) {
                        break;
                    }
                }
            }   
        }
    }
    
    private void moveEntity(int[] position, int rowOffset, int colOffset, int target) {
        //get the new position
        int newRow = position[0] + rowOffset;
        int newCol = position[1] + colOffset;

        //set the position to the target
        grid[newRow][newCol] = target;
        //set the original spot to none
        grid[position[0]][position[1]] = NONE;
        
        position[0] = newRow;
        position[1] = newCol;
        
    }
    
    /**
     * this will change the entity at location to the new entity no matter if the cell is blank or not
     * @param row row number of the grid
     * @param col column number of the grid
     * @param target type of entity
     */
    public void changeEntity(int row, int col, int target){
        removeEntity(row, col, grid[row][col]);
        spawnEntity(target, row, col);
    }
    
    /**
     * remove an entity from the grid
     * @param row row number of the grid
     * @param col column number of the grid
     * @param target type of entity
     */
    public void removeEntity(int row, int col, int target){
        //only decrease totalEntities by 1 if the cell is not blank
        if (target != NONE)
            totalEntities--;
        
        switch (target) {
            case PREY:
                for (int i = 0; i < preys.size(); i++) {
                    int[] prey = preys.get(i);
                    //if the prey is on the given position
                    if (prey[0] == row && prey[1] == col) {
                        //then remove it
                        preys.remove(i);
                        //end the loop
                        break;
                    }
                }   break;
            case PREDATOR:
                //check every predator
                for (int i = 0; i < predators.size(); i++) {
                    int[] predator = predators.get(i);
                    //if the predator is on the given position
                    if (predator[0] == row && predator[1] == col) {
                        //remove it
                        predators.remove(i);
                        predatorHunger.remove(i);  // Remove the corresponding hunger tracker
                        break;
                    }
                }   break;
            case FOOD:
                numFood--;
                break;
            case HABITAT:
                numHabitat--;
                break;
            default:
                break;
        }
        //set the cell to blank
        grid[row][col] = NONE;
    }
    
    private boolean checkIfMovable(int[] position, int direction, int target){
        //the new position
        int newRow = position[0];
        int newCol = position[1];

        //if it is up or down, change the row number
        switch (direction) {
            case UP:
                newRow --;
                break;
            case DOWN:
                newRow ++;
                break;
            case RIGHT:
                //otherwise change the column number
                newCol ++;
                break;
            default:
                newCol --;
                break;
        }

        //clamp the entity
        if (newRow >= 0 && newRow < GRIDSIZE && newCol >= 0 && newCol < GRIDSIZE && grid[newRow][newCol] == NONE) {
            //move the entity
            moveEntity(position, newRow - position[0], newCol - position[1], target);
            //it's moveable
            return true;         
        }
        //otherwise it's not moveable
        return false;

    }
      
    private int[] shuffle(int[] array){
        //used for shuffling an array of numbers
        int[] newArray = new int[array.length];
        int i = 0;
        //put random numbers of the original array into the new array
        while (i < array.length){
            //get a random index
            int rand = (int)(Math.floor(Math.random()*(array.length)));
            //if that index in the array is not -1 then put it into the new array
            if (array[rand] != -1){
                newArray[i] = rand;
                //used numbers become -1
                array[rand] = -1;
                //only add one to i if it successfully added the number
                i++;
            }
        }
        return newArray;
    }

    private int[] detectSurrounding(int[] position, int target){
        //create an array containing the row and column
        int[] direction = new int[2];
        
        //set both value to 0
        direction[0] = 0;
        direction[1] = 0;
        
        // change the direction depending the position of the nearby preys
        if (position[0] < GRIDSIZE - 1 && grid[position[0] + 1][position[1]] == target){ //down
            direction[0] = 1;
        } else if (position[0] > 0 && grid[position[0] - 1][position[1]] == target){ //up
            direction[0] = -1;
        } else if (position[1] < GRIDSIZE - 1 && grid[position[0]][position[1] + 1] == target){ //right
            direction[1] = 1;
        } else if (position[1] > 0 && grid[position[0]][position[1] - 1] == target){ //left
            direction[1] = -1;
        }             
            
        return direction;
    }
       
    private void reproduce(int[] position, int target){
        int[] directions = {UP, DOWN, LEFT, RIGHT};

        // Shuffle the directions
        directions = shuffle(directions);

        // Try to find an empty spot in one of the adjacent cells
        for (int direction : directions) {
            int newRow = position[0];
            int newCol = position[1];

            // Determine the new position based on direction
            switch (direction) {
                case UP -> newRow--;
                case DOWN -> newRow++;
                case LEFT -> newCol--;
                case RIGHT -> newCol++;
            }

            // Check if the new position is within bounds and unoccupied
            if (newRow >= 0 && newRow < GRIDSIZE && newCol >= 0 && newCol < GRIDSIZE && grid[newRow][newCol] == NONE) {
                // Reproduce the entity in the empty spot
                grid[newRow][newCol] = target;
                totalEntities++;
                // add the entity to the corresponding array
                if (target == PREY) {
                    preys.add(new int[]{newRow, newCol});
                } else if (target == PREDATOR){
                    predators.add(new int[]{newRow, newCol});
                    //make sure the new predator has its corresponding hunger value
                    predatorHunger.add(0);
                }
                break;  // Reproduction done, no need to check other directions
            }
        }
    }
}

