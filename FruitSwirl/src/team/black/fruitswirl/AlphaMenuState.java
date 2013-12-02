package team.black.fruitswirl;

import org.flixel.FlxButton;
import org.flixel.FlxG;
import org.flixel.FlxSprite;
import org.flixel.FlxState;
import org.flixel.FlxText;
import org.flixel.event.IFlxButton;

public class AlphaMenuState extends FlxState {

	public FlxSprite teamblacklogo, gamelogo, bg;
	public FlxButton playBtn;
	public FlxText t;
	public double fadeTimer = 0;
	
	@Override
	public void create() {
		bg = new FlxSprite(0, 0);
		bg.makeGraphic(FlxG.width, FlxG.height, 0x111111);
		
		t = new FlxText(0, 0, 600, Rg.gameTitle);
		
		teamblacklogo = new FlxSprite();
		teamblacklogo.loadGraphic("teamblacklogo.png");
		teamblacklogo.x = (FlxG.width/2) - 104;
		teamblacklogo.y = (FlxG.height/2) - 85;
		
		gamelogo = new FlxSprite();
		gamelogo.loadGraphic("fruitswirllogo.png");
		gamelogo.x = (FlxG.width/2) - 190;
		gamelogo.y = (FlxG.height/2) - 50;
		gamelogo.visible = false;
		
		playBtn = new FlxButton(FlxG.width/2 - 35, 110, "Play", playIBtn);
		playBtn.y += 50;
		playBtn.visible = false;
		
		add(bg);
		add(t);
		add(gamelogo);
		add(playBtn);
		add(teamblacklogo);
	}
	
	@Override
	public void update() {
		super.update();
		if ( fadeTimer < 2 ){
			fadeTimer += FlxG.elapsed;
		} else {
			teamblacklogo.visible = false;
			playBtn.visible = gamelogo.visible = true;
		}
	}
	
	IFlxButton playIBtn = new IFlxButton() {
		
		@Override
		public void callback() {
			FlxG.switchState(new PlayState());
		}
	};

}
