package com.mochilo.bouncing;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

public class ImageHelper extends JComponent {

	private Image image;

	public ImageHelper(Image image) {
		this.image = image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}

}
