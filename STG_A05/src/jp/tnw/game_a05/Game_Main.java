package jp.tnw.game_a05;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class Game_Main implements MouseListener,MouseMotionListener,KeyListener{

	//グローバル変数（どこでも使えるよ)
		final float FrameTime=1.0f/60.0f;//0.031f;
		final int X_Size=960;//ｹﾞｰﾑｳｨﾝﾄﾞの横ｻｲｽﾞ
		final int Y_Size=540;//ｹﾞｰﾑｳｨﾝﾄﾞの縦ｻｲｽﾞ
		JFrame	Wind=new JFrame("ｱﾆﾒｰｼｮﾝ試験");//JFrame の初期化(ﾒﾆｭｰﾊﾞｰの表示ﾀｲﾄﾙ
		BufferStrategy	offimage;//ﾀﾞﾌﾞﾙﾊﾞｯﾌｧでちらつき防止
		BufferedImage bgPNG;
		Insets sz;//ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ

		Font	f=new	Font("Default",Font.BOLD,24);//使用するフォントクラス宣言
		Font	f2=new	Font("Default",Font.PLAIN,12);//使用するフォントクラス宣言
		Random rn = new Random();

		int mx,my,mb,keyPress; //mouse coords

		DecimalFormat df = new DecimalFormat("#.#");
		 //setRoundingMode(RoundingMode.CEILING);

		//部品化したｸﾗｽを使うためにインスタンスを作る--------------
		Haikei BG = new Haikei();
		Jiki Player = new Jiki();
		//Jtama bullet = new Jtama();
		Jtama bullet1 = new Jtama2();
		Jtama bullet2 = new Jtama3();
		Jtama bullet3 = new Jtama4();
		Jtama bullet4 = new Jtama5();
		
		Zako_01 Zako = new Zako_01();

		Item ISpawn = new Item();
		Bomb Explode = new Bomb();


		//----------------------------------
		//	初期化用の関数(コンストラクタ)
		//	・Window生成
		//	・Window大きさの指定
		//	・Windowの場所
		//----------------------------------
		Game_Main(){
			//Windowの初期化---------------------
			Wind.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//閉じﾎﾞﾀﾝ許可
			Wind.setBackground(new Color(0,0,0));//色指定
			Wind.setResizable(false);//ｻｲｽﾞ変更不可false
			Wind.setVisible(true);//表示or非表示

			sz=Wind.getInsets();//ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ
			Wind.setSize(X_Size+sz.left+sz.right, Y_Size+sz.top+sz.bottom);//ｳｨﾝﾄﾞｳのｻｲｽﾞ
			Wind.setLocationRelativeTo(null);//中央に表示

			//ちらつき防止の設定(ﾀﾞﾌﾞﾙﾊﾞｯﾌｧﾘﾝｸﾞ)-------------------------------------
			Wind.setIgnoreRepaint(true);//JFrameの標準書き換え処理無効
			Wind.createBufferStrategy(2);//2でﾀﾞﾌﾞﾙ
			offimage=Wind.getBufferStrategy();

			//ﾏｳｽｲﾍﾞﾝﾄ初期化をGame_Mainのｺﾝｽﾄﾗｸﾀで実装---------------------
			Wind.addMouseMotionListener(this);
			Wind.addMouseListener(this);
			Wind.addKeyListener(this);

			//ファイルの読み込み
			
			bullet1.JK = Player;
			bullet2.JK = Player;
			bullet3.JK = Player;
			bullet4.JK = Player;
			
			Player.JT[0]=bullet1;
			Player.JT[1]=bullet2;
			Player.JT[2]=bullet3;
			Player.JT[3]=bullet4;

			Zako.JK = Player;
			Zako.JT[0] = bullet1;
			Zako.JT[1] = bullet2;
			Zako.JT[2] = bullet3;
			Zako.JT[3] = bullet4;
			
			Zako.BM = Explode;
			Zako.IT = ISpawn;

			ISpawn.JK = Player;
			
			
			//ﾀｲﾏｰﾀｽｸ起動----------------------------
			Timer	TM=new	Timer();//ﾀｲﾏｰｸﾗｽの実体化
			TM.schedule(new timer_TSK(), 17, 17);//17ms後から 17msおきに
							//どこ？　　　17[ms]=プログラムが動き出す最初の時間
							//            17[ms]その後は17[ms]繰り返し

		}//Main_Game end

		//---------------------------
		//	ﾀｲﾏｰｸﾗｽ　1/60秒で1回動作
		//	extends=継承
		//---------------------------
		class	timer_TSK extends	TimerTask{

			public void run() {//無限ループのプログラム

				//計算やｱﾆﾒなどの処理
				Player.move(mx, my);// pass mouse X and Y to player object
				
				bullet1.move();
				bullet2.move();
				bullet3.move();
				bullet4.move();
				Zako.move();
				
				Explode.UpDate();
				ISpawn.move();

				Graphics	g2=offimage.getDrawGraphics();//ｸﾞﾗﾌｨｯｸ初期化
				Graphics2D 	g = (Graphics2D) g2;

				if(offimage.contentsLost()==false)
				{//利可能??
					g.translate(sz.left, sz.top);//ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ補正
					g.clearRect(0, 0, X_Size, Y_Size);//画面ｸﾘｱ(左上X、左上Y、右下x、右下y)
					g.setColor(Color.WHITE);//色指定
					g.fillRect(0, 0, X_Size, Y_Size);//塗りつぶし
					g.drawImage(bgPNG,0,0,960,540,Wind);

				//--------------------------------------------------
					//絵や文字を表示
					BG.draw(g, Wind);
					Player.draw(g, Wind);

					bullet1.draw(g, Wind);
					bullet2.draw(g, Wind);
					bullet3.draw(g, Wind);
					bullet4.draw(g, Wind);
					
					ISpawn.draw(g, Wind);
					
					Zako.draw(g, Wind);
					Explode.draw(g, Wind);

					if(keyPress==KeyEvent.VK_ADD && Player.JT[Player.PW+1]!=null){
						keyPress=0;
						Player.PW++;
					}
					if(keyPress==KeyEvent.VK_SUBTRACT && Player.PW>0){
						keyPress=0;
						Player.PW--;
					}

					//for (int i =0;i<fuling.Max;i++){
					//	if(fuling.Flag[i]!=99){
					//		if(Math.sqrt(Math.pow(fuling.X[i]-fuling.Y[i])+Math.pow(hero.X-hero.Y))
					//
					//	}


					//文字の表示(文字の原点は左下）
					g.setColor(new Color(153, 0, 255));//色指定
					g.setFont(f);
					g.drawString("Shooting Game::モーテンソン エミル",20,30);
					g.setFont(f2);
					g.drawString("HP	:" + Player.HP,20,40);
					g.drawString("Power	:" + Player.PW,20,50);
					g.drawString("Item0	:" + Player.ItemGet[0],20,60);
					g.drawString("Item1	:" + Player.ItemGet[1],20,70);
					g.drawString("Item2	:" + Player.ItemGet[2],20,80);
					g.drawString("Item3	:" + Player.ItemGet[3],20,90);
					g.drawString("Item4	:" + Player.ItemGet[4],20,100);
					g.drawString("Item5	:" + Player.ItemGet[5],20,110);
					g.drawString("Item6	:" + Player.ItemGet[6],20,120);
					g.drawString("Item7	:" + Player.ItemGet[7],20,130);
					g.drawString("Item8	:" + Player.ItemGet[8],20,140);
					g.drawString("Item9	:" + Player.ItemGet[9],20,150);
					g.drawString("Item10:" + Player.ItemGet[10],20,160);
					g.drawString("Item11:" + Player.ItemGet[11],20,170);

				//---------------------------------------------------
					offimage.show();//ﾀﾞﾌﾞﾙﾊﾞｯﾌｧの切り替え
					g.dispose();//ｸﾞﾗﾌｨｯｸｲﾝｽﾀﾝｽの破棄

				}//if end ｸﾞﾗﾌｨｯｸOK??

			}//run end
		}//timer task class end

		public static void main(String[] args) {
			// TODO 自動生成されたメソッド・スタブ

			//ゲームメインクラスのインスタンスを生成----------------
			Game_Main GM=new Game_Main();

		}

		//-----------------
		// implementsで実装されたマウスとキーボード
		//

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ

		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			keyPress = e.getKeyCode();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			//keyRelease = e.getKeyCode();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO 自動生成されたメソッド・スタブ

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			Insets sz=Wind.getInsets();
			mx = e.getX()-sz.left;
			my = e.getY()-sz.top;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO 自動生成されたメソッド・スタブ

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			mb = e.getButton();// 押されたボタン
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			mb = 0;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO 自動生成されたメソッド・スタブ

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO 自動生成されたメソッド・スタブ

		}
}
