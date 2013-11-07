package team.black.fruitswirl;

import org.flixel.*;

public class Grid {

	public static final int FRUITS_PER_ROW = 14;
	public static final int FRUITS_PER_COL = 10;
	
	public FlxGroup fruits = new FlxGroup();
	
	public Grid(){
		
	}
	
	
	public void makeFirstBoard(){
		fruits.clear();
		
		int[][] choices = new int[FRUITS_PER_ROW][FRUITS_PER_COL*2];
		for ( int i = 0; i < FRUITS_PER_ROW; i++){
			for ( int j = 0; j < FRUITS_PER_COL*2; j++){
				choices[i][j] = -1;
			}
		}
		
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
						if ( ((ci - 1 > -1 && choice == choices[ci-1][cj]) && (ci + 1 < FRUITS_PER_ROW && choice == choices[ci+1][cj])) ||
							 ((ci - 1 > -1 && choice == choices[ci-1][cj]) && (ci - 2 > -1 && choice == choices[ci-2][cj])) ||
							 ((ci + 1 < FRUITS_PER_ROW && choice == choices[ci+1][cj]) && (ci + 2 < FRUITS_PER_ROW && choice == choices[ci+2][cj]))
							)
							continue;
						//vertical cases
						else if( ((cj - 1 > -1 && choice == choices[ci][cj-1]) && (cj + 1 < FRUITS_PER_COL*2 && choice == choices[ci][cj+1])) ||
								 ((cj - 1 > -1 && choice == choices[ci][cj-1]) && (cj - 2 > -1 && choice == choices[ci][cj-2])) ||
								 ((cj + 1 < FRUITS_PER_COL*2 && choice == choices[ci][cj+1]) && (cj + 2 < FRUITS_PER_COL*2 && choice == choices[ci][cj+2]))
								)
							continue;
						else ok = true;
					}
					
				}
				choices[i][j+FRUITS_PER_COL] = choice;
				ok = false;
				
				float pi = ((FlxG.width / 2) - ( (FRUITS_PER_ROW * Fruit.SIZE_X ) / 2) ) + (i * Fruit.SIZE_X),
					  pj = ((FlxG.height / 2) - ( (FRUITS_PER_COL * Fruit.SIZE_Y ) / 2) ) + (j * Fruit.SIZE_Y);
				
				
				if ( choice == 0 )
					fruits.add(new Apple(pi,pj,i,j));
				else if ( choice == 1 )
					fruits.add(new Grape(pi,pj,i,j));
				else if ( choice == 2 )
					fruits.add(new Orange(pi, pj,i,j));
				else if ( choice == 3 )
					fruits.add(new Watermelon(pi, pj,i,j));
				else fruits.add(new Lemon(pi, pj,i,j));
				
			}
		}
		
		//hide the off screen members
		for ( int i = 0; i < fruits.length; i++){
			Fruit f = (Fruit) fruits.members.get(i);
			if (f.gridy < 0)
				f.visible = false;
		}
		
	}

	public void toggleVis() {
		if ( fruits.members.get(0).visible ){
			//hide the off screen members
			for ( int i = 0; i < fruits.length; i++){
				Fruit f = (Fruit) fruits.members.get(i);
				if (f.gridy < 0)
					f.visible = false;
			}
		} else {
			fruits.setAll("visible", true);
		}
		
	}
	
	
}
