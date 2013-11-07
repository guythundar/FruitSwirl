package team.black.fruitswirl;

import org.flixel.FlxG;
import org.flixel.FlxSprite;
import org.flixel.FlxState;
import org.flixel.FlxText;

public class PlayState extends FlxState
{
	private Grid g;
	private FlxSprite bg;
	private FlxText t;
	
	@Override
	public void create()
	{
		bg = new FlxSprite(0,0);
		bg.makeGraphic(FlxG.width, FlxG.height, 0xFF000000);
		add(bg);
		
		t = new FlxText(0, 0, 600, "FruitSwirl Demo. Mind the bugs." + Rg.rng.nextInt());
		add(t);
		
		g = new Grid();

		//need to render grid elements. Should probably fix this later
		add(g.fruits);
		
		g.makeFirstBoard();
		
		
		
	}
	
	@Override
	public void update(){
		int fingers_down = 0;
		for(int i = 0; i < 10; i++){
			if(FlxG.mouse.justReleased(i))
				fingers_down++;
		}
		
		if (fingers_down == 3)
			g.makeFirstBoard();
		else if ( fingers_down == 2 )
			g.toggleVis();
	}
	

}