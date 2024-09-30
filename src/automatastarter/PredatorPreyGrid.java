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

    //Scanner s;
    //boolean to see if the program is supposed to be running
    //boolean isSimulationRunning;
    
    
    
    //2d grid
    int[][] grid;
    final int GRIDSIZE = 20;
    int cellSize;
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
    
    
    public PredatorPreyGrid(int width){
        //s = new Scanner(System.in);
        //create a square grid
        grid = new int[GRIDSIZE][GRIDSIZE];
        preys = new ArrayList<int[]>();
        predators = new ArrayList<int[]>();
        predatorHunger = new ArrayList<Integer>();
        cellSize = width/GRIDSIZE;
    }
    
    /**
     * Initialize and runs the program
     */
//    public void run(Graphics g, int width, BufferedImage[] images){
//        isSimulationRunning = true;
//        //show the original grid
//        initializeGrid();
//        drawGrid(g, width, images);
//        
////        while(isSimulationRunning){
////            //keep on updating the grid unless isSimulationRunning is set to false
////            updateGrid();
////            //print the grid again after updating it
////            drawGrid(g, width);
////        }
//                
//        s.close();
//    }
    
    
    public void drawGrid(Graphics g, BufferedImage[] images){
        images[0] = ImageUtil.loadAndResizeImage("prey.png", cellSize, cellSize);
        images[1] = ImageUtil.loadAndResizeImage("predator.png", cellSize, cellSize);
        images[2] = ImageUtil.loadAndResizeImage("apple.png", cellSize, cellSize);
        images[3] = ImageUtil.loadAndResizeImage("habitat.png", cellSize, cellSize);
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                int x = column * cellSize;
                int y = row * cellSize;
                
                switch (grid[row][column]) {
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
    
    public void drawGrid(Graphics g){
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                int x = column * cellSize;
                int y = row * cellSize;

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
     * This function will print the current status of the grid
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
     * Take user inputs, if the input is "n", quit the program. Otherwise update the status of the entities
     */
    public void updateGrid(){
//        //take input from user
//        String input = s.nextLine();
//        //if the user types "n" then quit the program
//        if (input.equals("n")){
//            isSimulationRunning = false;
//        } else {
//            //otherwise update the grid
//            updateEntities(predators, PREDATOR);
//            updateEntities(preys, PREY);
//        }

        updateEntities(predators, PREDATOR);
        updateEntities(preys, PREY);
    }
    
    public void initializeGrid(){
        //double for loop, first set every grid to nothing
        for (int row = 0; row < grid.length; row++){
            for (int column = 0; column < grid[0].length; column++){
                grid[row][column] = NONE;
            }
        }
        //randomly spawn preys and predators
        spawnEntity(INITIALPREYS, PREY);
        spawnEntity(INITIALPREDATORS, PREDATOR);
        spawnEntity(INITIALHABITAT, HABITAT);
        spawnEntity(INITIALFOOD, FOOD);
        
    }
    
    private void spawnEntity(int initialValue, int target){
        //keep track of how many entity has spawned
        int entitySpawned = 0;
        //keep spawning until the count hits the intial value
        while (entitySpawned < initialValue){
            //get a random position on the grid
            int randRow = (int)(Math.floor(Math.random() * GRIDSIZE));
            int randColumn = (int)(Math.floor(Math.random() * GRIDSIZE));
            //if there is nothing there, set it to the entity
            if (grid[randRow][randColumn] == NONE){
                grid[randRow][randColumn] = target;
                if (target == PREY){
                    //add this prey to the preys arraylist
                    preys.add(new int[]{randRow, randColumn});
                } else if (target == PREDATOR){
                    //same thing for predators
                    predators.add(new int[]{randRow, randColumn});
                    //initialize hunger tracker for each predator
                    predatorHunger.add(0);
                }
                //add one to count if it successfully spawn the entity
                entitySpawned++;
            }
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
                // Move towards the prey
                moveEntity(position, predatorDirection[0], predatorDirection[1], target);
                // eat the prey
                removeEntity(position[0], position[1], PREY);
                //set the hunger back to 0
                predatorHunger.set(predators.indexOf(position), 0);
            } else {
                // If no prey found, check if there are food around
                int[] foodDirection = detectSurrounding(position, FOOD);
                //if there are food around
                if (foodDirection[0] != 0 || foodDirection[1] != 0) {
                    // Move towards the food
                    moveEntity(position, foodDirection[0], foodDirection[1], target);
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
    
    public void addEntity(int row, int col, int target){
        grid[row][col] = target;
        if (target == PREY){
            preys.add(new int[]{row, col});
        } else if (target == PREDATOR){
            predators.add(new int[]{row, col});
            predatorHunger.add(0);
        }
    }
    
    public void removeEntity(int row, int col, int target){
        if (target == PREY){
            for (int i = 0; i < preys.size(); i++) {
                int[] prey = preys.get(i);
                //if the prey is on the given position
                if (prey[0] == row && prey[1] == col) {
                    //then remove it
                    preys.remove(i);
                    //end the loop
                    break;
                }
            }
        } else if (target == PREDATOR){
            //check every predator
            for (int i = 0; i < predators.size(); i++) {
                int[] predator = predators.get(i);
                //if the predator is on the given position
                if (predator[0] == row && predator[1] == col) {
                    //remove it
                    predators.remove(i);
                    predatorHunger.remove(i);  // Remove the corresponding hunger tracker
                    grid[row][col] = NONE;  // Clear the predator from the grid
                    break;
                }
            }
        }
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

