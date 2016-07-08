package jp.tnw.a18;

//使いたいｸﾗｽの取り込み
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GameMain {

	// グロバール変数

	final float FrameTime = 1.0f / 60.0f;// 0.031f;
	final int X_Size = 1280;// ｹﾞｰﾑｳｨﾝﾄﾞの横ｻｲｽﾞ
	final int Y_Size = 720;// ｹﾞｰﾑｳｨﾝﾄﾞの縦ｻｲｽﾞ
	int Angel = 10;
	BufferedImage img;

	JFrame Wind = new JFrame("A18ﾁｮｳｶﾝﾌ");// JFrame の初期化(ﾒﾆｭｰﾊﾞｰの表示ﾀｲﾄﾙ
	BufferStrategy offimage;// ﾀﾞﾌﾞﾙﾊﾞｯﾌｧでちらつき防止
	Insets sz;// ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ

	Font f = new Font("Default", Font.BOLD, 24);// 使用するフォントクラス宣言

	// ■FOR OTHER CLASSES 実例化■

	// Unit test = new Unit("Image/Zako", "1", 10, 9);
	// UnitJump test2 = new UnitJump("Image/Zako", "1", 10, 9);
	UnitCircle circle = new UnitCircle();

	// -----------------------------
	// 初期化用の関数
	// ・Window生成
	// ・Window大きさの指定
	// ・Windowの場所

	// -----------------------------
	GameMain() {

		Wind.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 閉じﾎﾞﾀﾝ許可
		Wind.setBackground(new Color(0, 0, 0));// 色指定
		Wind.setResizable(false);// ｻｲｽﾞ変更不可
		Wind.setVisible(true);// 表示or非表示
		sz = Wind.getInsets();// ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ
		Wind.setSize(X_Size + sz.left + sz.right, Y_Size + sz.top + sz.bottom);// ｳｨﾝﾄﾞｳのｻｲｽﾞ
		Wind.setLocationRelativeTo(null);// 中央に表示

		// ちらつき防止の設定(ﾀﾞﾌﾞﾙﾊﾞｯﾌｧﾘﾝｸﾞ)-------------------------------------
		Wind.setIgnoreRepaint(true);// JFrameの標準書き換え処理無効
		Wind.createBufferStrategy(2);// 2でﾀﾞﾌﾞﾙ
		offimage = Wind.getBufferStrategy();

		// ファイルの読み込み
		// ■LOAD■
		try {

			img = ImageIO.read(getClass().getResource("Image/hum.png"));

		} catch (IOException e) {

			e.printStackTrace();

		}
		// test.loadImage();
		// test2.loadImage();
		circle.loadImage("Image/Zako", 10, 9);

		// ﾀｲﾏｰﾀｽｸ起動----------------------------
		Timer TM = new Timer();// ﾀｲﾏｰｸﾗｽの実体化
		TM.schedule(new timer_TSK(), 17, 17);// 17ms後から 17msおきに
		// どこ？ 17[ms]=プログラムが動き出す最初の時間
		// 17[ms]その後は17[ms]繰り返し

	}// Main_Game end

	// ---------------------------
	// ﾀｲﾏｰｸﾗｽ 1/60秒で1回動作
	// extends=継承
	// ---------------------------
	class timer_TSK extends TimerTask {

		public void run() {

			// ■UPDATE■

			// test.update();
			// test2.update();

			circle.update();

			Graphics g2 = offimage.getDrawGraphics();// ｸﾞﾗﾌｨｯｸ初期化
			Graphics2D g = (Graphics2D) g2;

			if (offimage.contentsLost() == false) {//
				g.translate(sz.left, sz.top);// ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ補正
				g.clearRect(0, 0, X_Size, Y_Size);// 画面ｸﾘｱ(左上X、左上Y、右下x、右下y)
				g.setColor(Color.BLACK);// 色指定
				g.fillRect(0, 0, X_Size, Y_Size);// 塗りつぶし
//				Rectangle r = new Rectangle(0, 0, img.getWidth(), img.getHeight());
//				g.setPaint(new TexturePaint(img, r));
//				Rectangle rect = new Rectangle(15,15,400,400);
//				AffineTransform transform = new AffineTransform();
//				transform.rotate(Angel * Math.PI / 180, 640, 360);
//				Angel++;
//				g.setTransform(transform);
//				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
//		                   RenderingHints.VALUE_INTERPOLATION_BICUBIC);
//			    g.fill(rect);
				// --------------------------------------------------
				// 絵や文字を表示
				// ■DRAW■

				// test.drawImage(g, Wind, 0, 0);
				// test2.drawImage(g, Wind, 0, 0);
				circle.drawImage(g, Wind, 0, 0);

				// ---------------------------------------------------
				offimage.show();// ﾀﾞﾌﾞﾙﾊﾞｯﾌｧの切り替え
				g.dispose();// ｸﾞﾗﾌｨｯｸｲﾝｽﾀﾝｽの破棄

			} // if end ｸﾞﾗﾌｨｯｸOK??

		}// run end

	}// timer task class end
		// -----------------------------------
		// Main ここからプログラムが動く
		// -----------------------------------

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		// Main_GameのｸﾗｽをGMという名前で動かします
		// 動かす前に初期化してから動かす！！

		GameMain GM = new GameMain();

	}

}
