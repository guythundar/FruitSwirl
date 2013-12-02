package team.black.fruitswirl;

public class Grape extends Fruit {

	public Grape(float _ix, float _iy) {
		super(_ix, _iy);
//		makeTestSprite(0xFF800080);
		loadGraphic("grape_alpha.png");
	}

	@Override
	public void update(){
		super.update();
	}

}
