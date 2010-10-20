/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.view.level.levelobjects;

import data.Direction2D;
import data.ImageLoader;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import logging.Logger;
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
}
