package cse260Project;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private Node parent;
	private List <Node> children = new ArrayList<Node>();
	private int posX;
	private int posY;
	public Node(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
	public Node() {
		// TODO Auto-generated constructor stub
	}
	public boolean hasParent() {
		if(parent.hasChild(this)) {
			return true;
		}
		return false;
	}
	public List<Node> getChildren(){
		return this.children;
	}
	public void addChildren(Node child) {
		this.children.add(child);
	}
	public boolean hasChild(Node child) {
		for(Node x : this.children) {
			if(x.getPosX() == child.getPosX() && x.getPosY() == child.getPosY()) {
				return true;
			}
		}
		return false;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public int getPosX() {
		return posX;
	}
	public int getPosY() {
		return posY;
	}
}
