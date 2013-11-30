package team.black.fruitswirl;

import org.flixel.FlxG;
import org.flixel.FlxPoint;
import org.flixel.FlxSprite;
import org.flixel.FlxState;
import org.flixel.FlxText;

public class PlayState extends FlxState
{
	private Grid g;
	private FlxSprite bg;
	private FlxText t;
	private boolean justTapped = false;
	private FlxPoint mpos = new FlxPoint(0,0);
	
	
	@Override
	public void create()
	{
		//prep
		bg = new FlxSprite(0,0);
		bg.makeGraphic(FlxG.width, FlxG.height, 0xFF000000);
		
		t = new FlxText(0, 0, 600, "FruitSwirl Alpha");
		
		g = new Grid();
		g.makeFirstBoard();
		g.initGravityPoints();
		
		Rg.spinner.alignToGridMember(g.getFirstVisible());
		
		//render
		add(bg);
		add(t);
		add(g.bg);
		add(Rg.spinner);
		add(g.fruits);
		
		
		
	}
	
	@Override
	public void update(){
		super.update();
		int fingers_down = 0;		
		justTapped = FlxG.mouse.justPressed();
		if ( FlxG.mouse.pressed() ){
			mpos = FlxG.mouse.getWorldPosition();
			justTapped = false;
		}
		
		for(int i = 0; i < 10; i++){
			if(FlxG.mouse.justReleased(i)){
				fingers_down++;
			}
		}
		
		if ( fingers_down == 1 ){
			FlxPoint moveHere = g.checkOverlap(mpos);
			if ( moveHere != null ){
				boolean canRotate = Rg.spinner.move(moveHere, 
						                            g.getCollidePos(moveHere));
				if (canRotate){
					FlxG.log("Playstate", "--- Start Rotate ---");
					g.rotateFruits();
					FlxG.log("Playstate", "--- End Rotate ---");
				}
			}
			justTapped = false;
		}
		
		if (fingers_down == 3){
			g.makeFirstBoard();
			Rg.spinner.alignToGridMember(g.getFirstVisible());
			justTapped = false;
		}
		
	}
	

}