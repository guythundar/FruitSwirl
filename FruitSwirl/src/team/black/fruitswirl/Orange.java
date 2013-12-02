package team.black.fruitswirl;

public class Orange extends Fruit {

	public Orange(float _ix, float _iy) {
		super(_ix, _iy);
//		makeTestSprite(0xFFFF7F00);
		loadGraphic("orange_alpha.png");
	}

	@Override
	public void update(){
		super.update();
	}

}
