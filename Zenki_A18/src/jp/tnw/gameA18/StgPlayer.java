package jp.tnw.gameA18;

public class StgPlayer extends StgObject{

	int UNIT_MAX = 1;

	boolean isHitable[] = new boolean[UNIT_MAX];
	double hitCir[] = new double[UNIT_MAX];
	double hitBoxW[] = new double[UNIT_MAX];
	double hitBoxH[] = new double[UNIT_MAX];

	StgPlayer(){

		imageIndex[0] = 9;
		isVisible[0] = true;
		dX[0] = 512;
		dY[0] = 400;

	}

	public void update(){

		dX[0] = Input.M_X - 48;
		dY[0] = Input.M_Y - 48;

	}

}
