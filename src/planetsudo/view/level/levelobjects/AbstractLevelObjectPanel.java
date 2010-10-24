/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.view.level.levelobjects;

import data.Direction2D;
import data.ImageLoader;
import exceptions.NotValidException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import logging.Logger;
import math.RandomGenerator;
import planetsudo.level.levelobjects.AbstractLevelObject;
import view.components.draw.AbstractResourcePanel;

/**
 *
 * @author divine
 */
public abstract class AbstractLevelObjectPanel<R extends AbstractLevelObject, PRP extends AbstractResourcePanel> extends AbstractResourcePanel<R, PRP> {

	protected BufferedImage image;

	public AbstractLevelObjectPanel(R resource, PRP parentResourcePanel, String imageURI) {
		super(resource, parentResourcePanel);
		if(imageURI != null) {
			try {
				this.image = ImageLoader.getInstance().loadImage(imageURI);
			} catch (IOException ex) {
				Logger.error(this, "Could not load "+this+" image.", ex);
			}
		}
	}

	protected void paintShape(Graphics2D g2) {
		switch(resource.getShape()) {
			case Oval:
				g2.fillOval((int)resource.getPosition().getX()-((int)resource.getWidth()/2), (int)resource.getPosition().getY()-((int)resource.getHeight()/2), (int)resource.getWidth(), (int)resource.getHeight());
				break;
			case Rec:
				g2.fillRect((int)resource.getPosition().getX()-((int)resource.getWidth()/2), (int)resource.getPosition().getY()-((int)resource.getHeight()/2), (int)resource.getWidth(), (int)resource.getHeight());
				break;
		}
	}

	protected void paintImage(Graphics2D g2) {
		g2.drawImage(image, getSkaleImageToBoundsTransformation(), parentPanel);
	}

	protected void paintImageRotated(Direction2D direction, Graphics2D g2) {
		AffineTransform transformation = getSkaleImageToBoundsTransformation();
		transformation = rotateTransformation(direction, image.getWidth(), image.getHeight(), transformation);
		g2.drawImage(image, transformation, parentPanel);
	}

	private Dimension2D dimension2D = new Dimension();
	public AffineTransform rotateTransformation(Direction2D direction, int width, int height, AffineTransform affineTransform) {
		dimension2D.setSize(width, height);
		return getRotationTransformation(direction, dimension2D, affineTransform);
	}

	public AffineTransform getRotationTransformation(Direction2D direction, Dimension2D dimension, AffineTransform affineTransform) {
		affineTransform.rotate(Math.toRadians(direction.getAngle()+90),
								dimension.getWidth() / 2.0,
								dimension.getHeight() / 2.0);
		return affineTransform;
	}

	public AffineTransform getSkaleImageToBoundsTransformation() {
		return new AffineTransform(
					boundingBox.getWidth()/image.getWidth(), 0,
					0, boundingBox.getHeight()/image.getHeight(),
					boundingBox.getX(), boundingBox.getY());
	}

	@Override
	public String toString() {
		return getClass().getSimpleName()+"["+resource.getID()+"]";
	}

	private int xa1 = (int) resource.getPosition().getX();
	private int xa2 = (int) resource.getPosition().getX();
	private int xa3 = (int) resource.getPosition().getX();
	private int ya1 = (int) resource.getPosition().getY();
	private int ya2 = (int) resource.getPosition().getY();
	private int ya3 = (int) resource.getPosition().getY();
	private int xb1 = (int) resource.getPosition().getX();
	private int xb2 = (int) resource.getPosition().getX();
	private int xb3 = (int) resource.getPosition().getX();
	private int yb1 = (int) resource.getPosition().getY();
	private int yb2 = (int) resource.getPosition().getY();
	private int yb3 = (int) resource.getPosition().getY();

	protected void paintExplosion(Graphics2D g22) {
		try {
			xa3=xa2;
			ya3=ya2;
			xa2=xa1;
			ya2=ya1;
			xa1=xb3;
			ya1=yb3;
			xb3=xb2;
			yb3=yb2;
			xb2=xb1;
			yb2=yb1;
			xb1 = RandomGenerator.getRandom((int) boundingBox.getMinX(), (int) boundingBox.getMaxX());
			yb1 = RandomGenerator.getRandom((int) boundingBox.getMinY(), (int) boundingBox.getMaxY());

			g22.setColor(Color.YELLOW);
			g22.fillOval(xa3-9, ya3-9, 18, 18);
			g22.setColor(Color.ORANGE);
			g22.fillOval(xa1-6, ya1-6, 12, 12);
			g22.fillOval(xa2-6, ya2-6, 12, 12);
			g22.fillOval(xa3-6, ya3-6, 12, 12);
			g22.setColor(Color.RED);
			//g22.drawOval(xa3-3, ya3-3, 6, 6);
			g22.fillOval(xa2-3, ya2-3, 6, 6);
			g22.fillOval(xa1-3, ya1-3, 6, 6);

			g22.setColor(Color.YELLOW);
			g22.fillOval(xb3-9, yb3-9, 18, 18);
			g22.setColor(Color.ORANGE);
			g22.fillOval(xb1-6, yb1-6, 12, 12);
			g22.fillOval(xb2-6, yb2-6, 12, 12);
			g22.fillOval(xb3-6, yb3-6, 12, 12);
			g22.setColor(Color.RED);
			//g22.drawOval(xb3-3, yb3-3, 6, 6);
			g22.fillOval(xb2-3, yb2-3, 6, 6);
			g22.fillOval(xb1-3, yb1-3, 6, 6);

		} catch (NotValidException ex) {
			java.util.logging.Logger.getLogger(AbstractLevelObjectPanel.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
