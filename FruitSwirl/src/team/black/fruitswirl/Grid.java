package team.black.fruitswirl;


import java.awt.Point;
import java.util.ArrayList;

import org.flixel.*;

public class Grid {

	public static final int FRUITS_PER_ROW = 14;
	public static final int FRUITS_PER_COL = 10;
	
	/**
	 * fruits is the container for both the visible and invisible fruits that
	 * are active on the grid. When adding the grid to be rendered by Flixel,
	 * you must add fruits and not the grid itself.
	 * 
	 */
	public FlxGroup fruits = new FlxGroup();
	
	/**
	 * currentFruits contains a integar that identifies the type of Fruit that
	 * is in the grid. It does not contain any reference to the Fruit itself or
	 * its drawing location, though both can be inferred.
	 * 
	 */
	public int[][] currentFruits = new int[FRUITS_PER_ROW][FRUITS_PER_COL*2];
	
	/**
	 * gPoints is a set of FlxPoints that are used to check where the player has
	 * clicked/tapped relative to the Grid. All gPoints are positioned to be in
	 * the center of 4 Fruits on the visible grid.
	 * 
	 * To init the gPoints, call initGPoints();
	 *  
	 */
	public ArrayList<FlxPoint> gPoints = new ArrayList<FlxPoint>();
	
	/**
	 * minPoint and maxPoint are the respective start and end point at which
	 * visible fruits and the spinner are allowed to align. minPoint can be seen
	 * as the x and y location of the grid, though the grid doesn't have either.
	 * maxPoint can be used to calculate the size of the visible grid with
	 * respect to minPoint.
	 *
	 */
	private FlxPoint minPoint = new FlxPoint(),
			         maxPoint = new FlxPoint();
	
	public Grid(){
		//calculate the min point for visible fruits to appear
		minPoint.x = ((FlxG.width/2)-((FRUITS_PER_ROW*Fruit.SIZE_X)/2));
		minPoint.y = ((FlxG.height/2)-((FRUITS_PER_COL*Fruit.SIZE_Y)/2));
		
		//calculate the max point for visible fruits to appear
		maxPoint.x = minPoint.x + ( (FRUITS_PER_ROW-1) * Fruit.SIZE_X);
		maxPoint.y = minPoint.y + ( (FRUITS_PER_COL-1) * Fruit.SIZE_Y);
	}
	
	public void initGPoints(){
		for ( int i = 0; i < FRUITS_PER_ROW-1; i++){
			for ( int j = 0; j < FRUITS_PER_COL-1; j++ ){				
				gPoints.add(new FlxPoint(minPoint.x + (i * Fruit.SIZE_X),
						                 minPoint.y + (j * Fruit.SIZE_Y)));
			}
		}
		
	}
	
	private void clearChoices(){
		for ( int i = 0; i < FRUITS_PER_ROW; i++){
			for ( int j = 0; j < FRUITS_PER_COL*2; j++){
				currentFruits[i][j] = -1;
			}
		}
	}
	
	
	public void makeFirstBoard(){
		fruits.clear();
		clearChoices();
		
		int choice = -1;
		boolean ok = false;
		
		
		for ( int i = 0; i < FRUITS_PER_ROW; i++ ){
			for ( int j = -FRUITS_PER_COL; j < FRUITS_PER_COL; j++ ){				
				while(!ok){
					
					choice = Rg.rng.nextInt(5);
					
					//case first fruit
					if ( i == 0 && j == -FRUITS_PER_COL )
						ok = true;
					else{
						//this is going to be ugly, its needs to be handled better somehow
						int ci = i, cj = j + FRUITS_PER_COL;
						//horizontal cases
						if ( ((ci - 1 > -1 && choice == currentFruits[ci-1][cj]) && (ci + 1 < FRUITS_PER_ROW && choice == currentFruits[ci+1][cj])) ||
							 ((ci - 1 > -1 && choice == currentFruits[ci-1][cj]) && (ci - 2 > -1 && choice == currentFruits[ci-2][cj])) ||
							 ((ci + 1 < FRUITS_PER_ROW && choice == currentFruits[ci+1][cj]) && (ci + 2 < FRUITS_PER_ROW && choice == currentFruits[ci+2][cj]))
							)
							continue;
						//vertical cases
						else if( ((cj - 1 > -1 && choice == currentFruits[ci][cj-1]) && (cj + 1 < FRUITS_PER_COL*2 && choice == currentFruits[ci][cj+1])) ||
								 ((cj - 1 > -1 && choice == currentFruits[ci][cj-1]) && (cj - 2 > -1 && choice == currentFruits[ci][cj-2])) ||
								 ((cj + 1 < FRUITS_PER_COL*2 && choice == currentFruits[ci][cj+1]) && (cj + 2 < FRUITS_PER_COL*2 && choice == currentFruits[ci][cj+2]))
								)
							continue;
						else ok = true;
					}
					
				}
				currentFruits[i][j+FRUITS_PER_COL] = choice;
				ok = false;
				
				float pi = minPoint.x + (i * Fruit.SIZE_X),
					  pj = minPoint.y + (j * Fruit.SIZE_Y);				
				if ( choice == 0 )
					fruits.add(new Apple(pi,pj));
				else if ( choice == 1 )
					fruits.add(new Grape(pi,pj));
				else if ( choice == 2 )
					fruits.add(new Orange(pi, pj));
				else if ( choice == 3 )
					fruits.add(new Watermelon(pi, pj));
				else fruits.add(new Lemon(pi, pj));
				
				
			}
		}
		
		//hide the off screen members
		for ( int i = 0; i < fruits.length; i++){
			Fruit f = (Fruit) fruits.members.get(i);
			if (f.y < minPoint.y)
				f.visible = false;
		}
		
	}
	
	
	/*
	 *        1
	 *       /|\
	 *   4 <--+--> 2
	 *       \|/
	 *        3
	 * 
	 */
	private boolean nextMatchs(int direction, Point cloc){
		int cur = currentFruits[cloc.x][cloc.y];
		try {
			switch(direction){
			case 1: return currentFruits[cloc.x][cloc.y-1] == cur; 
			case 2: return currentFruits[cloc.x+1][cloc.y] == cur;
			case 3: return currentFruits[cloc.x][cloc.y+1] == cur;
			case 4: return currentFruits[cloc.x-1][cloc.y] == cur;
			default: return false;
			}
		} catch (ArrayIndexOutOfBoundsException e){
			return false;
		}
	}
	
	//y * w + x
	public void checkBoard(){
		for ( int i = 0; i < FRUITS_PER_ROW; i++ ){
			for ( int j = FRUITS_PER_COL; j < FRUITS_PER_COL * 2; j++ ){
				if ( nextMatchs(1, new Point(i,j)) && nextMatchs(1, new Point(i,j-1)) ){
					
				}
			}
		}
	}

	public Fruit getFirstVisible() {
		for ( int i = 0; i < fruits.length; i++){
			if ( fruits.members.get(i).visible )
				return (Fruit) fruits.members.get(i);
		}
		return null;
	}
	
	private int pointToFruitInd(FlxPoint p){
		for ( int i = 0; i < fruits.members.size; i++ ){
			Fruit f = (Fruit) fruits.members.get(i);
			if ( f.x == p.x && f.y == p.y )
				return i;
		}
		return -1;
	}
	
	public void rotateFruits(FlxPoint spinPos){
		/* find the fruits and set there state to rotate		
		 * 
		 * A B
		 * D C
		 * 
		 * A RIGHT
		 * B DOWN
		 * C LEFT
		 * D UP
		 * 
		 */
		
		Fruit fa = (Fruit)fruits.members.get(pointToFruitInd(spinPos));
		fa.setRotateDirection(Fruit.ROTATE_RIGHT);
		fa.setCurrentState(Fruit.STATE_ROTATE);
		fa.startPathing();
		
		spinPos.x += Fruit.SIZE_X;
		Fruit fb = (Fruit)fruits.members.get(pointToFruitInd(spinPos));
		fb.setRotateDirection(Fruit.ROTATE_DOWN);
		fb.setCurrentState(Fruit.STATE_ROTATE);
		fb.startPathing();
		
		spinPos.y += Fruit.SIZE_Y;
		Fruit fc = (Fruit)fruits.members.get(pointToFruitInd(spinPos));
		fc.setRotateDirection(Fruit.ROTATE_LEFT);
		fc.setCurrentState(Fruit.STATE_ROTATE);
		fc.startPathing();
		
		spinPos.x -= Fruit.SIZE_X;
		Fruit fd = (Fruit)fruits.members.get(pointToFruitInd(spinPos));
		fd.setRotateDirection(Fruit.ROTATE_UP);
		fd.setCurrentState(Fruit.STATE_ROTATE);
		fd.startPathing();
		
		//replace collide indexes
//		Point ref = Rg.spinner.getCollidePos();
//		
//		int[] tempVals = new int[4];
//		tempVals[0] = currentFruits[ref.x][ref.y];
//		tempVals[1] = currentFruits[ref.x+1][ref.y];
//		tempVals[2] = currentFruits[ref.x+1][ref.y+1];
//		tempVals[3] = currentFruits[ref.x][ref.y+1];
//		
//		currentFruits[ref.x][ref.y] = tempVals[1];
//		currentFruits[ref.x][ref.y] = tempVals[2];
//		currentFruits[ref.x][ref.y] = tempVals[3];
//		currentFruits[ref.x][ref.y] = tempVals[0];		
		
	}
	
	public FlxPoint checkOverlap(FlxPoint mpos) {
			double shortestDist = 999999999;
			FlxPoint alignPoint = new FlxPoint(0,0);
			for ( int i = 0; i < gPoints.size(); i++){
				double distance = Math.abs(Math.sqrt( Math.pow((mpos.x - gPoints.get(i).x),2) + Math.pow((mpos.y - gPoints.get(i).y),2)));
				if ( distance < shortestDist ){
					shortestDist = distance;
					alignPoint = gPoints.get(i);
				}
			}
			return alignPoint;
	}
	
}

