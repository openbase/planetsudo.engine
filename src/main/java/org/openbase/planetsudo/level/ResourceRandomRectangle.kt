/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.level

import org.openbase.jul.exception.InvalidStateException
import org.openbase.planetsudo.geometry.Point2D
import org.openbase.planetsudo.level.levelobjects.Resource
import org.openbase.planetsudo.level.levelobjects.Resource.ResourceType
import org.openbase.planetsudo.main.GUIController
import org.openbase.planetsudo.util.RandomGenerator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.Rectangle
import java.awt.geom.Rectangle2D
import java.beans.PropertyChangeEvent

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class ResourceRandomRectangle(
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    override val resourceCount: Int,
    private val type: ResourceType,
) : Rectangle2D.Double(
    x.toDouble(),
    y.toDouble(),
    width.toDouble(),
    height.toDouble()
),
    ResourcePlacement {
    override fun getResources(level: AbstractLevel): ArrayList<Resource> {
        val resources = ArrayList<Resource>()
        for (i in 0 until resourceCount) {
            GUIController.setEvent(PropertyChangeEvent(this, GUIController.LOADING_STEP, null, -1))
            // Point2D resourcePosition = new Point2D(RandomGenerator.getRandom((int) x, (int) getWidth()), RandomGenerator.getRandom((int) y, (int) height));
            resources.add(Resource(level.generateNewResourceID(), type, level, this))
        }
        return resources
    }

    override fun calcRandomLevelPosition(level: AbstractLevel): Point2D {
// 		double widthCast = (int) level.getLevelBorderPolygon().getBounds().getWidth()/LevelView.RASTER_SIZE;
// 		double heightCast = (int) level.getLevelBorderPolygon().getBounds().height/LevelView.RASTER_SIZE;
// 		double width, height;
//
//
// 		if(widthCast < level.getLevelBorderPolygon().getBounds().getWidth()/LevelView.RASTER_SIZE) {
// 			width = ((int) heightCast)+1;
// 		} else {
// 			width = (int) widthCast;
// 		}
// 		if(heightCast < level.getLevelBorderPolygon().getBounds().height/LevelView.RASTER_SIZE) {
// 			height = ((int) heightCast)+1;
// 		} else {
// 			height = (int) heightCast;
// 		}

// 		int index = 0;
//
// 		while(true) {
// 			try {
// 				index = RandomGenerator.getRandom(0, width * height);
// 			} catch (InvalidStateException ex) {
// 				logger.error("Could not generate resource position.", ex);
// 			}
//
// 			int x = index % width;
// 			int y = index / width;
//
// 			int xLevelPosition = (int) level.x+(x*LevelView.RASTER_SIZE+LevelView.RASTER_SIZE/2);
// 			int yLevelPosition = (int) level.y+(y*LevelView.RASTER_SIZE+LevelView.RASTER_SIZE/2);
//
// 			Rectangle2D rasterLevelRectangle = new Rectangle2D.Double(xLevelPosition-LevelView.RASTER_SIZE/2, yLevelPosition-LevelView.RASTER_SIZE/2, LevelView.RASTER_SIZE, LevelView.RASTER_SIZE);
// 			Rectangle2D agentBoundsRectangle = new Rectangle2D.Double(xLevelPosition-Agent.AGENT_SIZE, yLevelPosition-Agent.AGENT_SIZE, Agent.AGENT_SIZE*2, Agent.AGENT_SIZE*2);
//
// 			boolean partOfWall = level.containsWall(rasterLevelRectangle);
// 			boolean nextToWall = level.containsWall(agentBoundsRectangle);
//
// 			if(!(partOfWall || nextToWall)) {
// 				return new Point2D(xLevelPosition, yLevelPosition);
// 			}
// 		}

        var resourceXPos = 0
        var resourceYPos = 0
        var tries = 0
        while (true) {
            try {
                resourceXPos = RandomGenerator.getRandom(minX.toInt(), maxX.toInt())
                resourceYPos = RandomGenerator.getRandom(minY.toInt(), maxY.toInt())
            } catch (ex: InvalidStateException) {
                logger.warn("Could not place Resource[$type]! Bad resoure bounds!", ex)
                break
            }
            if (!level.containsWall(
                    Rectangle(
                        resourceXPos - Resource.RESOURCE_SIZE,
                        resourceYPos - Resource.RESOURCE_SIZE,
                        Resource.RESOURCE_SIZE * 2,
                        Resource.RESOURCE_SIZE * 2
                    )
                )
            ) {
                break
            } else {
                tries++
                if (tries > 1000) {
                    logger.warn("Could not place Resource[$type]! Bad map design!")
                    break
                }
            }
        }
        return Point2D(resourceXPos.toDouble(), resourceYPos.toDouble())
    }

    override fun translateIntoBase(base: Point2D) {
        x -= base.x
        y -= base.y
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ResourceRandomRectangle::class.java)
    }
}
