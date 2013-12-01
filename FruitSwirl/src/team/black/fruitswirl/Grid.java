package team.black.fruitswirl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.flixel.*;
import org.flixel.plugin.tweens.TweenPlugin;
import org.flixel.plugin.tweens.TweenSprite;

import aurelienribon.tweenengine.Tween;

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
	public Fruit[][] currentFruits = 
			new Fruit[FRUITS_PER_ROW][FRUITS_PER_COL*2];
	
	
	
	/**
	 * A collection of drawable points that snap to the grid based on minPoint.
	 * 
	 */
	public FlxPoint[][] gridPoints = 
			new FlxPoint[FRUITS_PER_ROW][FRUITS_PER_COL*2];
	
	
	public FlxSprite bg;
	
	/**
	 * currentChoices contains a int that identifies the type of Fruit that
	 * is in the grid. It does not contain any reference to the Fruit itself or
	 * its drawing location, though both can be inferred.
	 * 
	 */
	public int[][] currentChoices = new int[FRUITS_PER_ROW][FRUITS_PER_COL*2];
	
	/**
	 * gravityPoints is a set of FlxPoints that are used to check where the player has
	 * clicked/tapped relative to the Grid. All gravityPoints are positioned to be in
	 * the center of 4 Fruits on the visible grid.
	 * 
	 * To init the gravityPoints, call initGPoints();
	 *  
	 */
	public ArrayList<FlxPoint> gravityPoints = new ArrayList<FlxPoint>();
	
	/**
	 * minPoint and maxPoint are the respective start and end point at which
	 * visible fruits and the spinner are allowed to align. minPoint can be seen
	 * as the x and y location of the grid, though the grid doesn't have either.
	 * maxPoint can be used to calculate the size of the visible grid with
	 * respect to minPoint.
	 *
	 */
	public FlxPoint minPoint = new FlxPoint(),
			         maxPoint = new FlxPoint();
	
	public Grid(){
		//calculate the min point for visible fruits to appear
		minPoint.x = ((FlxG.width/2)-((FRUITS_PER_ROW*Fruit.SIZE_X)/2));
		minPoint.y = ((FlxG.height/2)-((FRUITS_PER_COL*Fruit.SIZE_Y)/2));
		
		//calculate the max point for visible fruits to appear
		maxPoint.x = minPoint.x + ( (FRUITS_PER_ROW-1) * Fruit.SIZE_X);
		maxPoint.y = minPoint.y + ( (FRUITS_PER_COL-1) * Fruit.SIZE_Y);
		
		//build the gridPoints
		for ( int i = 0; i < FRUITS_PER_ROW; i++){
			for ( int j = 0; j < FRUITS_PER_COL*2; j++){
				float x = minPoint.x + ( i * Fruit.SIZE_X ),
					  y = minPoint.y + ( ( j - FRUITS_PER_COL ) * Fruit.SIZE_Y);
				gridPoints[i][j] = new FlxPoint(x,y);
			}
		}
		
		//load bgGraphic
		bg = new FlxSprite(minPoint.x - 3, minPoint.y -2);
		bg.loadGraphic("retina_wood_bg.png");
	}
	
	public void initGravityPoints(){
		for ( int i = 0; i < FRUITS_PER_ROW-1; i++){
			for ( int j = 0; j < FRUITS_PER_COL-1; j++ ){				
				gravityPoints.add(new FlxPoint(minPoint.x + (i * Fruit.SIZE_X),
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
		setOffscreenVisible(false);
	}
	
	public void setOffscreenVisible(boolean b){
		for ( int i = 0; i < fruits.length; i++){
			Fruit f = (Fruit) fruits.members.get(i);
			if (f.y < minPoint.y)
				f.visible = b;
		}
	}
	
	private void getFirstChoices(){
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
						if ( ((i - 1 > -1 && choice == currentChoices[i-1][j]) && (i + 1 < FRUITS_PER_ROW && choice == currentChoices[i+1][j])) ||
							 ((i - 1 > -1 && choice == currentChoices[i-1][j]) && (i - 2 > -1 && choice == currentChoices[i-2][j])) ||
							 ((i + 1 < FRUITS_PER_ROW && choice == currentChoices[i+1][j]) && (i + 2 < FRUITS_PER_ROW && choice == currentChoices[i+2][j]))
							)
							continue;
						//vertical cases
						else if( ((j - 1 > -1 && choice == currentChoices[i][j-1]) && (j + 1 < FRUITS_PER_COL*2 && choice == currentChoices[i][j+1])) ||
								 ((j - 1 > -1 && choice == currentChoices[i][j-1]) && (j - 2 > -1 && choice == currentChoices[i][j-2])) ||
								 ((j + 1 < FRUITS_PER_COL*2 && choice == currentChoices[i][j+1]) && (j + 2 < FRUITS_PER_COL*2 && choice == currentChoices[i][j+2]))
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
				Fruit f = currentFruits[i][j];
				f.x = gridPoints[i][j].x;
				f.y = gridPoints[i][j].y;
				fruits.add(f);
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
		
		//get the current location of the Spinner in Collide Points
		Point ref = Rg.spinner.getCollidePos();
		
		//signal the drawables to start animating
		FlxPoint p = new FlxPoint(currentFruits[ref.x+1][ref.y].x,
									currentFruits[ref.x+1][ref.y].y );
		Tween.to(currentFruits[ref.x][ref.y], TweenSprite.XY, 1f)
	     .target(p.x, p.y).start(TweenPlugin.manager);
		
		p = new FlxPoint(currentFruits[ref.x+1][ref.y+1].x,
				currentFruits[ref.x+1][ref.y+1].y );
		Tween.to(currentFruits[ref.x+1][ref.y], TweenSprite.XY, 1f)
		     .target(p.x, p.y).start(TweenPlugin.manager);
		
		p = new FlxPoint(currentFruits[ref.x][ref.y+1].x,
				currentFruits[ref.x][ref.y+1].y );
		Tween.to(currentFruits[ref.x+1][ref.y+1], TweenSprite.XY, 1f)
	     .target(p.x, p.y).start(TweenPlugin.manager);
		
		p = new FlxPoint(currentFruits[ref.x][ref.y].x,
				currentFruits[ref.x][ref.y].y );
		Tween.to(currentFruits[ref.x][ref.y+1], TweenSprite.XY, 1f)
	     .target(p.x, p.y).start(TweenPlugin.manager);
		
		swapFruits(ref.x, ref.y, ref.x+1, ref.y);
		swapFruits(ref.x, ref.y, ref.x+1, ref.y+1);
		swapFruits(ref.x, ref.y, ref.x, ref.y+1);
	}
	
	private void swapFruits(int ox, int oy, int nx, int ny){
		Fruit t = currentFruits[ox][oy];
		currentFruits[ox][oy] = currentFruits[nx][ny];
		currentFruits[nx][ny] = t;		
	}
	
	public FlxPoint checkOverlap(FlxPoint mpos) {
		double shortestDist = Double.MAX_VALUE;
		FlxPoint alignPoint = new FlxPoint();
		for ( int i = 0; i < gravityPoints.size(); i++){
			double distance = Math.abs(Math.sqrt(Math.pow((mpos.x - 
										gravityPoints.get(i).x),2) +
										Math.pow((mpos.y-gravityPoints.get(i).y)
										,2)));
			if ( distance < shortestDist ){
				shortestDist = distance;
				alignPoint = gravityPoints.get(i);
			}
		}
		return alignPoint;
	}

	public Point getCollidePosFromPoint(FlxPoint moveHere) {
		Point p = new Point();
		for ( int i = 0; i < FRUITS_PER_ROW; i++){
			for ( int j = 0; j < FRUITS_PER_COL*2; j++){
				if ( moveHere.x == gridPoints[i][j].x &&
					 moveHere.y == gridPoints[i][j].y){
					p.setXY(i, j);
					break;
				}
			}
		}		
		return p;
	}
	
	public ArrayList<ArrayList<Point>> findLineups(){
		ArrayList<ArrayList<Point>> l = new ArrayList<ArrayList<Point>>();
		
		
		
		return l;	
	}
	
}

