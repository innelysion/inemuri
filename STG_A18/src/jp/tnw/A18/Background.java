package jp.tnw.A18;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Background {

	BufferedImage Image;
	double dblOffsetX;
	double dblOffsetY;
	int intFlag;

	Background() {

	}

	public void loadimage() {

		try {

			Image = ImageIO.read(getClass().getResource("Image/bg_04.png"));

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	public void drawimage(Graphics Graphic, JFrame Window) {

		Graphic.drawImage(Image, 0, 0, 800, 600, 0, 0, 800, 600, Window);

	}

	public void update() {

	}

}
