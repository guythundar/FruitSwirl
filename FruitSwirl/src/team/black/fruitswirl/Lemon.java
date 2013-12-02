package team.black.fruitswirl;

public class Lemon extends Fruit {

	public Lemon(float _ix, float _iy) {
		super(_ix, _iy);
//		makeTestSprite(0xFFFFFF00);
		loadGraphic("lemon_alpha.png");
	}

	@Override
	public void update(){
		super.update();
	}

}
