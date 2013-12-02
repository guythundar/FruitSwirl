package team.black.fruitswirl;

public class Watermelon extends Fruit {

	public Watermelon(float _ix, float _iy) {
		super(_ix, _iy);
		//makeTestSprite(0xFF00FF00);
		loadGraphic("watermelon_alpha.png");
	}

	@Override
	public void update(){
		super.update();
	}

}
