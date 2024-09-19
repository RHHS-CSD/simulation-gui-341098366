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
public class ConwaySGameOfLife {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        Random rn = new Random();
        
        int[][] cells;
        int[][] cellsCopy;
        int[][] cellNeighbours;
        int neighbourCount=0;
        
        cells = initiateArray(kb);
        assignStates(cells, rn);
        cellsCopy=copyArray(cells);
        cellNeighbours=copyArray(cells);
        
        while (true){
            printGrid(cells);
            System.out.println();
            cellNeighbours=liveNeighbours(cells,cellNeighbours,neighbourCount);
            printGrid(cellNeighbours);
            System.out.println();
            kill(cells,cellsCopy,cellNeighbours);
            cells = copyArray(cellsCopy);
            kb.nextInt();
        }
        
    }
    
    public static int[][] initiateArray(Scanner kb){
        int rows=kb.nextInt();
        int columns=kb.nextInt();
        int[][] cells = new int[rows+2][columns+2];
        return cells;
    }
    
    public static void assignStates(int[][] cellState, Random rn){
        for (int i=1;i<cellState.length-1;i++){
            for (int j=1;j<cellState[i].length-1;j++){
                cellState[i][j]=rn.nextInt(2);
            }
        }
    }
    
    public static int[][] copyArray(int[][] cells){
        int[][] copy = new int[cells.length][cells[0].length];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                copy[i][j] = cells[i][j];
            }
        }
        return copy;
    }
    
    public static void printGrid(int[][] cells){
        for (int i = 1; i < cells.length-1; i++) {
            for (int j = 1; j < cells[i].length-1; j++) {
                System.out.print(cells[i][j]+" ");
            }
            System.out.println();
        }
    }
    
    public static int[][] liveNeighbours(int[][] cells, int[][] cellNeighbours, int neighbourCount){
        for (int i = 1; i < cells.length-1; i++) {
            for (int j = 1; j < cells[i].length-1; j++) {
                neighbourCount=0;
                if (cells[i-1][j-1] == 1){
                    neighbourCount++;
                }
                if (cells[i-1][j] == 1){
                    neighbourCount++;
                }
                if (cells[i-1][j+1] == 1){
                    neighbourCount++;
                }
                if (cells[i][j-1] == 1){
                    neighbourCount++;
                }
                if (cells[i][j+1] == 1){
                    neighbourCount++;
                }
                if (cells[i+1][j-1] == 1){
                    neighbourCount++;
                }
                if (cells[i+1][j] == 1){
                    neighbourCount++;
                }
                if (cells[i+1][j+1] == 1){
                    neighbourCount++;
                }
                cellNeighbours[i][j] = neighbourCount;
            }
        }
        return cellNeighbours;
    }
    
    public static void kill(int[][] cells, int[][] cellsCopy, int[][] cellNeighbours){
        for (int i = 1; i < cells.length-1; i++) {
            for (int j = 1; j < cells[i].length-1; j++) {
                if (cells[i][j] == 0 && cellNeighbours[i][j]==3){
                    cellsCopy[i][j] = 1;
                }
                else if(cells[i][j] ==1){
                    if (cellNeighbours[i][j]<2 || cellNeighbours[i][j]>3){
                        cellsCopy[i][j] =0;
                    }
                }
            }
        }
    }
}
