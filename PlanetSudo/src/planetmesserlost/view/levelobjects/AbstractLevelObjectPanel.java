/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetmesserlost.view.levelobjects;

import data.Direction2D;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import logging.Logger;
import planetmesserlost.levelobjects.AbstractLevelObject;
import view.components.draw.AbstractResourcePanel;

/**
 *
 * @author divine
 */
public abstract class AbstractLevelObjectPanel<R extends AbstractLevelObject, PRP extends AbstractResourcePanel> extends AbstractResourcePanel<R, PRP> {

	private BufferedImage image;

	public AbstractLevelObjectPanel(R resource, PRP parentResourcePanel, String imageURI) {
		super(resource, parentResourcePanel);
		try {
			this.image = ImageIO.read(new File(imageURI));
		} catch (IOException ex) {
			Logger.error(this, "Could not read image["+imageURI+"]", ex);
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
		g2.drawImage(image, getTransformation(), parentPanel);
	}

	protected void paintImageRotated(Direction2D direction, Graphics2D g2) {
		AffineTransform transformation = getTransformation();
		transformation.rotate(	(Math.toRadians(direction.getAngle()+90)),
								image.getWidth() / 2.0,
								image.getHeight() / 2.0);
		g2.drawImage(image, transformation, parentPanel);
	}

	public AffineTransform getTransformation() {
		return new AffineTransform(
					boundingBox.getWidth()/image.getWidth(), 0,
					0, boundingBox.getHeight()/image.getHeight(),
					boundingBox.getX(), boundingBox.getY());
	}
}
