package jp.tnw.a18;

import java.awt.image.BufferedImage;

public class StgObject {

	// リソース読み込む処理関係
	final int ARRIMAGE_MAX = 100;
	int fileType;
	int widthBlockQty;
	int heightBlockQty;
	BufferedImage Image;
	BufferedImage[] arrImage = new BufferedImage[ARRIMAGE_MAX];

	// 表示処理関係
	final double TIME_UNIT = 1.0 / 60.0;
	final int UNIT_MAX = 10000;
	double dblTimerG;									// Timer for Generation
	double[] dblTimerA = new double[UNIT_MAX];			// Timer for Animation
	int[] intTimerF = new int[UNIT_MAX];				// Timer for Animation Flag
	boolean blnAniSwitch[] = new boolean[UNIT_MAX];		// アニメの実行スイッチ
	int intOffsetIndex[] = new int[UNIT_MAX];			// コマ
	double dblOffsetX[] = new double[UNIT_MAX];			// X座標補正
	double dblOffsetY[] = new double[UNIT_MAX];			// Y座標補正
	double dblSpdX[] = new double[UNIT_MAX];			// X速度
	double dblSpdY[] = new double[UNIT_MAX];			// Y速度
	double dblAccSpdX[] = new double[UNIT_MAX];			// X加速度
	double dblAccSpdY[] = new double[UNIT_MAX];			// Y加速度
	int intFlag[] = new int[UNIT_MAX];						// フラグ

}
