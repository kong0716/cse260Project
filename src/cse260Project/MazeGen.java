package cse260Project;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class MazeGen {
	public static void main(String []args) {
		Random randomNumber = new Random();
		Scanner in = new Scanner(System.in);
		int row = 50;
		int col = 50;
		//Creates the initial maze
		Cell [][] maze= new Cell[row][col];
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				maze[i][j] = new Cell(i,j);
			}
		}
		List<Cell> frontier = new LinkedList<Cell>();
		frontier.add(pickEntrance(maze));
		List<Cell> path = new LinkedList<Cell>();
		boolean openedPath = false;
		while(!frontier.isEmpty()) {
			//Use a flag to find if you need to cut a path
			int random = randomNumber.nextInt(frontier.size());
			
			if(frontier.get(random) == null) {//Avoids null Cells
				frontier.remove(random);
			}
			if(random == frontier.size()) {
				random--;
			}
			if(frontier.isEmpty()) {
				break;
			}
			Cell temp = frontier.remove(random);
			if(!frontier.contains(returnTopAdjacent(maze, temp))) {
				if(!path.contains(returnTopAdjacent(maze, temp))) {
					frontier.add(returnTopAdjacent(maze, temp));
				}else if(!openedPath){
					temp.setPathUP(true);
					returnTopAdjacent(maze, temp).setPathDOWN(true);
					openedPath = true;
				}
			}
			if(!frontier.contains(returnBotAdjacent(maze, temp))) {
				if(!path.contains(returnBotAdjacent(maze, temp))) {
					frontier.add(returnBotAdjacent(maze, temp));
				}else if(!openedPath){
					temp.setPathDOWN(true);
					returnBotAdjacent(maze, temp).setPathUP(true);
					openedPath = true;
				}
			}
			if(!frontier.contains(returnLeftAdjacent(maze, temp))) {
				if(!path.contains(returnLeftAdjacent(maze, temp))) {
					frontier.add(returnLeftAdjacent(maze, temp));
				}else if(!openedPath){
					temp.setPathLEFT(true);
					returnLeftAdjacent(maze, temp).setPathRIGHT(true);
					openedPath = true;
				}
			}
			if(!frontier.contains(returnRightAdjacent(maze, temp))) {
				if(!path.contains(returnRightAdjacent(maze, temp))) {
					frontier.add(returnRightAdjacent(maze, temp));
				}else if(!openedPath){
					temp.setPathRIGHT(true);
					returnRightAdjacent(maze, temp).setPathLEFT(true);
					openedPath = true;
				}
			}
			System.out.println(temp.toString());
			path.add(temp);
			printMaze(maze);
			openedPath = false;
		}
		printMaze(maze);
	}
	public static void printMaze(Cell[][] maze) {
		System.out.println("Maze");
		System.out.print(" ");
		for(int i = 0; i < maze.length; i++) {
			System.out.print("_ ");
		}
		for(int i = 0; i < maze.length; i++) {
			System.out.println("");
			for(int j = 0; j < maze[i].length; j++) {
				if(maze[i][j].isPathLEFT()) {
					System.out.print(" ");
				}
				if(!maze[i][j].isPathLEFT()) {
					System.out.print("|");
				}
				if(maze[i][j].isPathDOWN()) {
					System.out.print(" ");
				}
				if(!maze[i][j].isPathDOWN()) {
					System.out.print("_");
				}
				if(j == maze[i].length-1 && !maze[i][j].isPathRIGHT()) {
					System.out.print("|");
				}
			}
		}
	}
	public static Cell pickEntrance(Cell[][] maze) {
		maze[0][0].isStartCELL();
		return maze[10][10];
	}
	public static Cell returnTopAdjacent(Cell[][] maze, Cell cell) {
		if(cell.getRow() > 0) {
			return maze[cell.getRow()-1][cell.getCol()];
		}
		return null;
	}
	public static Cell returnBotAdjacent(Cell[][] maze, Cell cell) {
		if(cell.getRow() < maze.length-1) {
			return maze[cell.getRow()+1][cell.getCol()];
		}
		return null;
	}
	public static Cell returnLeftAdjacent(Cell[][] maze, Cell cell) {
		if(cell.getCol() > 0) {
			return maze[cell.getRow()][cell.getCol()-1];
		}
		return null;
	}
	public static Cell returnRightAdjacent(Cell[][] maze, Cell cell) {
		if(cell.getCol() < maze[cell.getRow()].length-1) {
			return maze[cell.getRow()][cell.getCol()+1];
		}
		return null;
	}
}
