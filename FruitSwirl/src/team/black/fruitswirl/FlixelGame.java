package team.black.fruitswirl;

import org.flixel.FlxGame;

public class FlixelGame extends FlxGame
{
	public FlixelGame()
	{
		super(500, 250, PlayState.class, 1, 50, 50, false);
	}
}
