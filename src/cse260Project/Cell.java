package cse260Project;

public class Cell {
	private boolean startCELL;
	private boolean endCELL;
	private boolean pathUP;
	private boolean pathDOWN;
	private boolean pathLEFT;
	private boolean pathRIGHT;
	private int row;
	private int col;
	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public boolean isStartCELL() {
		return startCELL;
	}
	public void setStartCELL(boolean startCELL) {
		this.startCELL = startCELL;
	}
	public boolean isEndCELL() {
		return endCELL;
	}
	public void setEndCELL(boolean endCELL) {
		this.endCELL = endCELL;
	}
	public boolean isPathUP() {
		return pathUP;
	}
	public void setPathUP(boolean pathUP) {
		this.pathUP = pathUP;
	}
	public boolean isPathDOWN() {
		return pathDOWN;
	}
	public void setPathDOWN(boolean pathDOWN) {
		this.pathDOWN = pathDOWN;
	}
	public boolean isPathLEFT() {
		return pathLEFT;
	}
	public void setPathLEFT(boolean pathLEFT) {
		this.pathLEFT = pathLEFT;
	}
	public boolean isPathRIGHT() {
		return pathRIGHT;
	}
	public void setPathRIGHT(boolean pathRIGHT) {
		this.pathRIGHT = pathRIGHT;
	}
	public String toString() {
		return "\nCell is at row " + this.getRow() + " and column " + this.getCol() + " and has PathUP: " + this.isPathUP() + " and has PathDOWN: " + this.isPathDOWN() + " and has PathLEFT: " + this.isPathLEFT() + " and has PathRIGHT: " + this.isPathRIGHT();
	}
	
}
