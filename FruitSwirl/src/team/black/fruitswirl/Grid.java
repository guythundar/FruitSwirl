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
	 * currentFruits
	 */
	public Fruit[][] currentFruits = new Fruit[FRUITS_PER_ROW][FRUITS_PER_COL*2];
	
	
	/**
	 * currentChoices contains a int that identifies the type of Fruit that
	 * is in the grid. It does not contain any reference to the Fruit itself or
	 * its drawing location, though both can be inferred.
	 * 
	 */
	public int[][] currentChoices = new int[FRUITS_PER_ROW][FRUITS_PER_COL*2];
	
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
				currentChoices[i][j] = -1;
			}
		}
	}
	
	
	public void makeFirstBoard(){
		getFirstChoices();
		choicesToFruits();
		updateDrawables();
		
		//hide the off screen members
		for ( int i = 0; i < fruits.length; i++){
			Fruit f = (Fruit) fruits.members.get(i);
			if (f.y < minPoint.y)
				f.visible = false;
		}
	}
	
	
	private void getFirstChoices(){
		fruits.clear();
		clearChoices();
		
		int choice = -1;
		boolean ok = false;
		
		
		for ( int i = 0; i < FRUITS_PER_ROW; i++ ){
			for ( int j = 0; j < FRUITS_PER_COL*2; j++ ){				
				while(!ok){
					
					choice = Rg.rng.nextInt(5);
					
					//case first fruit
					if ( i == 0 && j == 0 )
						ok = true;
					else{
						//this is going to be ugly, its needs to be handled better somehow
						//horizontal cases
						if ( ((i - 1 > -1 && choice == currentChoices[i-1][i]) && (i + 1 < FRUITS_PER_ROW && choice == currentChoices[i+1][i])) ||
							 ((i - 1 > -1 && choice == currentChoices[i-1][i]) && (i - 2 > -1 && choice == currentChoices[i-2][i])) ||
							 ((i + 1 < FRUITS_PER_ROW && choice == currentChoices[i+1][i]) && (i + 2 < FRUITS_PER_ROW && choice == currentChoices[i+2][i]))
							)
							continue;
						//vertical cases
						else if( ((i - 1 > -1 && choice == currentChoices[i][i-1]) && (i + 1 < FRUITS_PER_COL*2 && choice == currentChoices[i][i+1])) ||
								 ((i - 1 > -1 && choice == currentChoices[i][i-1]) && (i - 2 > -1 && choice == currentChoices[i][i-2])) ||
								 ((i + 1 < FRUITS_PER_COL*2 && choice == currentChoices[i][i+1]) && (i + 2 < FRUITS_PER_COL*2 && choice == currentChoices[i][i+2]))
								)
							continue;
						else ok = true;
					}
					
				}
				currentChoices[i][j] = choice;
				ok = false;
				
			}
		}
				
	}
	
	private void choicesToFruits(){
		for ( int i = 0; i < FRUITS_PER_ROW; i++ ){
			for ( int j = 0; j < FRUITS_PER_COL*2; j++ ){				
				int choice = currentChoices[i][j];
				float pi = minPoint.x + (i * Fruit.SIZE_X),
					  pj = minPoint.y + ((j-FRUITS_PER_COL) * Fruit.SIZE_Y);				
				if ( choice == 0 )
					currentFruits[i][j] = new Apple(pi,pj);
				else if ( choice == 1 )
					currentFruits[i][j] = new Grape(pi,pj);
				else if ( choice == 2 )
					currentFruits[i][j] = new Orange(pi,pj);
				else if ( choice == 3 )
					currentFruits[i][j] = new Watermelon(pi,pj);
				else currentFruits[i][j] = new Lemon(pi,pj);
			}
		}
	}
	
	/**
	 * updateDrawables takes the members of int[][] currentFruits and copies
	 * them to FlxGroup fruits. This operation is semi-expensive as it will
	 * cause the entire grid to have to be re-rendered.
	 * 
	 */
	
	public void updateDrawables(){
		fruits.clear();
		for ( int i = 0; i < FRUITS_PER_ROW; i++ ){
			for ( int j = 0; j < FRUITS_PER_COL*2; j++ ){
				fruits.add(currentFruits[i][j]);
			}
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
		int cur = currentChoices[cloc.x][cloc.y];
		try {
			switch(direction){
			case 1: return currentChoices[cloc.x][cloc.y-1] == cur; 
			case 2: return currentChoices[cloc.x+1][cloc.y] == cur;
			case 3: return currentChoices[cloc.x][cloc.y+1] == cur;
			case 4: return currentChoices[cloc.x-1][cloc.y] == cur;
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

	
	public void rotateFruits(){
		
		Point ref = Rg.spinner.collidePos;
		
		Fruit fa = currentFruits[ref.x][ref.y];
		fa.setRotateDirection(Fruit.ROTATE_RIGHT);
		fa.setCurrentState(Fruit.STATE_ROTATE);
		fa.startPathing();
		
		Fruit fb = currentFruits[ref.x+1][ref.y];
		fb.setRotateDirection(Fruit.ROTATE_DOWN);
		fb.setCurrentState(Fruit.STATE_ROTATE);
		fb.startPathing();
		
		Fruit fc = currentFruits[ref.x+1][ref.y+1];
		fc.setRotateDirection(Fruit.ROTATE_LEFT);
		fc.setCurrentState(Fruit.STATE_ROTATE);
		fc.startPathing();
		
		Fruit fd = currentFruits[ref.x][ref.y+1];
		fd.setRotateDirection(Fruit.ROTATE_UP);
		fd.setCurrentState(Fruit.STATE_ROTATE);
		fd.startPathing();
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

