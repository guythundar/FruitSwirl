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
		
		t = new FlxText(0, 0, 600, "FruitSwirl Demo -- Version Pre-Alpha");
		
		g = new Grid();
		g.makeFirstBoard();
		g.initGPoints();
		
		Rg.spinner.alignToGridMember(g.getFirstVisible());
		
		//render
		add(bg);
		add(t);
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
			FlxPoint toMoveToPos = g.checkOverlap(mpos);
			if ( toMoveToPos != null){
				boolean canRotate = Rg.spinner.move(toMoveToPos);
				if (canRotate){
					FlxG.log("let's rotate");
					g.rotateFruits(Rg.spinner.getScreenXY());
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