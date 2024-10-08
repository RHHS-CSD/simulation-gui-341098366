/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package conway.s.game.of.life;
import java.util.*;

/**
 *
 * @author maxya
 */
public class ConwayCopy {

    /**
     * @param args the command line arguments
     */
    
    public ConwayCopy(){
        
    }
    
    
    /**
     * return an integer value depending on user input to start the game
     * @param kb
     * @return integer value representing whether the user choose a random starting sequence or a common starting sequence
     */
    public static int startGame(Scanner kb){
        //get input from user
        System.out.println("Random Start (0) or Common Starting Sequence (1)?: ");
        int input = kb.nextInt();
        int output = 0;
        //return value based on user input
        switch (input) {
            case 0:
                output = 0;
                break;
            case 1:
                output = 1;
                break;
            default:
                //exit when invalid input
                System.out.println("Invalid Input. Self-destructing");
                System.exit(0);
        }
        return output;
    }
    
    /**
     * creates an array based on user inputs
     * @param kb
     * @return return created array
     */
    public static int[][] initiateArray(Scanner kb){
        //get user input
        System.out.println("rows: ");
        int rows=kb.nextInt();
        System.out.println("columns: ");
        int columns=kb.nextInt();
        //create array
        int[][] cells = new int[rows+2][columns+2];
        return cells;
    }
    
    /**
     * Assign dead, alive or special cell values to an array
     * @param cellState
     * @param rn 
     * @param b 
     */
    public static void assignRandomStates(int[][] cellState, Random rn, boolean b){
        //assign random states (alive or dead)
        for (int i=1;i<cellState.length-1;i++){
            for (int j=1;j<cellState[i].length-1;j++){
                switch (rn.nextInt(60)) {
                    case 0:
                        //special cell (Bomb)
                        if (b==true){
                            cellState[i][j]=2;
                            break;
                        }
                    case 1:
                        //special cell (Healer)
                        if (b==true){
                            cellState[i][j]=3;
                            break;
                        }
                    default:
                        cellState[i][j]=rn.nextInt(2);
                        break;
                }
                
            }
        }
    }
    
    /**
     * Copies an array
     * @param cells
     * @return Array identical to array passed in the parameters
     */
    public static int[][] copyArray(int[][] cells){
        //copy array given in parameters
        int[][] copy = new int[cells.length][cells[0].length];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                copy[i][j] = cells[i][j];
            }
        }
        return copy;
    }
    
    //prints the grid to the screen
    private static void printGrid(int[][] cells){
        for (int i = 1; i < cells.length-1; i++) {
            for (int j = 1; j < cells[i].length-1; j++) {
                //display each cell on the screen  
                System.out.print(cells[i][j]+" ");
            }
            System.out.println();
        }
    }
    
    /**
     * Kills all cells on border for formatting reasons
     * @param cells 
     */
    public static void border(int[][] cells){
        for (int i=0; i< cells.length;i=i+cells.length-1){
            for (int j =0; j<cells.length;j++){
                cells[i][j]=0;
            }
        }
        for (int i=0; i<cells.length;i++){
            for (int j=0;j<cells.length;j=j+cells.length-1){
                cells[i][j]=0;
            }
        }
    }
    
    /**
     * Checks the number of live neighbours around a cell in a 2d array
     * @param cells
     * @param cellNeighbours
     * @param neighbourCount
     * @return number of live neighbours for each index of a 2d array
     */
    public static int[][] liveNeighbours(int[][] cells, int[][] cellNeighbours, int neighbourCount){
        for (int i = 1; i < cells.length-1; i++) {
            for (int j = 1; j < cells[i].length-1; j++) {
                neighbourCount=0;
                //check all possible neighbours to see if they are alive or dead
                if (cells[i-1][j-1] >= 1){
                    neighbourCount++;
                }
                if (cells[i-1][j] >= 1){
                    neighbourCount++;
                }
                if (cells[i-1][j+1] >= 1){
                    neighbourCount++;
                }
                if (cells[i][j-1] >= 1){
                    neighbourCount++;
                }
                if (cells[i][j+1] >= 1){
                    neighbourCount++;
                }
                if (cells[i+1][j-1] >= 1){
                    neighbourCount++;
                }
                if (cells[i+1][j] >= 1){
                    neighbourCount++;
                }
                if (cells[i+1][j+1] >= 1){
                    neighbourCount++;
                }
                cellNeighbours[i][j] = neighbourCount;
            }
        }
        return cellNeighbours;
    }
    
    /**
     * kills or revives cells based on number of live neighbours
     * @param cells
     * @param cellsCopy
     * @param cellNeighbours 
     * @param rn 
     */
    public static void kill(int[][] cells, int[][] cellsCopy, int[][] cellNeighbours, Random rn, boolean b){
        for (int i = 1; i < cells.length-1; i++) {
            for (int j = 1; j < cells[i].length-1; j++) {
                //revive cell if live neighbours = 3
                if (cells[i][j] == 0 && cellNeighbours[i][j]==3){
                    switch (rn.nextInt(60)) {
                        case 0:
                            if (b==true){
                                cellsCopy[i][j] = 2;
                                break;
                            }
                        case 1:
                            if (b==true){
                                cellsCopy[i][j] = 3;
                                break;
                            }
                        default:
                            cellsCopy[i][j] = 1;
                            break;
                    }
                }
                //kill live cells if over or underpopulation
                else if(cells[i][j] ==1){
                    if (cellNeighbours[i][j]<2 || cellNeighbours[i][j]>3){
                        cellsCopy[i][j] =0;
                    }
                }
            }
        }
    }
    /**
     * Check for bomber cells and explode accordingly
     * @param cells
     * @param cellsCopy 
     */
    public static void bomb(int[][] cells, int[][] cellsCopy){
        for (int i = 1; i < cells.length-1; i++) {
            for (int j = 1; j < cells[i].length-1; j++) {
                if (cells[i][j] == 2){
                    //blow up all cells in a 3x3 radius
                    for (int a = -1; a < 2; a++) {
                        for (int b = -1; b < 2; b++) {
                            cellsCopy[i+a][j+b] =0;
                        }
                    }
                }
            }
        }
    }
    /**
     * Check for healer cells and revive accordingly
     * @param cells
     * @param cellsCopy 
     */
    public static void heal(int[][] cells, int[][] cellsCopy) {
        for (int i = 1; i < cells.length - 1; i++) {
            for (int j = 1; j < cells[i].length - 1; j++) {
                if (cells[i][j] == 3) {
                    //revive all cells in a 3x3 radius, then healer cell dies
                    for (int a = -1; a < 2; a++) {
                        for (int b = -1; b < 2; b++) {
                            cellsCopy[i + a][j + b] = 1;
                        }
                    }
                    cellsCopy[i][j]=0;
                }
            }
        }
    }
    /**
     * Creates a premade cell sequence (Blinker)
     * @param cells
     * @return array with premade cell sequence (Blinker)
     */
    public static int[][] sequenceOne(int[][] cells){

        for (int i=2;i<5;i++){
            cells[2][i] = 1;
        }
        return cells;
    }
    /**
     * Creates a premade cell sequence (Blinker v2)
     * @param cells
     * @return array with premade cell sequence (Blinker v2)
     */
    public static int[][] sequenceTwo(int[][] cells){
        for (int i=1;i<6;i++){
            if (i%2==0){
                for (int j=1;j<6;j+=2){
                    cells[i][j]=1;
                }
            }
            else if(i%2==1){
                for (int j=2;j<6;j+=2){
                    cells[i][j]=1;
                }
            }
        }
        return cells;
    }
    /**
     * Creates a premade cell sequence (Glider)
     * @param cells
     * @return array with premade cell sequence (Glider)
     */
    public static int[][] sequenceThree(int[][] cells){
        cells[1][1]=1;
        cells[2][2]=1;
        cells[2][3]=1;
        cells[3][1]=1;
        cells[3][2]=1;
        return cells;
    }
    /**
     * Counts number of live cells
     *
     * @param cells
     * @return integer representing number of cells alive
     */
    public static int countLive(int cells[][]){
        int liveCells = 0;
        for (int i = 1; i < cells.length - 1; i++) {
            for (int j = 1; j < cells.length - 1; j++) {
                if (cells[i][j] != 0) {
                    //count number of cells that aren't dead
                    liveCells++;
                }
            }
        }
        return liveCells;
    }
    /**
     * resets an array
     *
     * @param cells
     * @return empty array filled with zeroes
     */
    public static int[][] reset(int[][] cells){
        //fill array with zeroes
        for (int i = 1; i < cells.length - 1; i++) {
            for (int j = 1; j < cells.length - 1; j++) {
                cells[i][j] = 0;
            }
        }
        return cells;
    }
}
