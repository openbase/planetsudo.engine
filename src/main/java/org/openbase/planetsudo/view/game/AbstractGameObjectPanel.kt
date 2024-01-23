/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.view.game

import org.openbase.jul.visual.swing.engine.draw2d.AbstractResourcePanel
import org.openbase.jul.visual.swing.engine.draw2d.ResourceDisplayPanel
import org.openbase.jul.visual.swing.engine.draw2d.ResourcePanel
import org.openbase.planetsudo.game.AbstractGameObject
import java.awt.Polygon
import java.awt.event.MouseEvent

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
abstract class AbstractGameObjectPanel<R : AbstractGameObject, PRP : ResourcePanel> :
    AbstractResourcePanel<R, PRP> {
    constructor(
        resource: R,
        placementPolygon: Polygon,
        objectType: ObjectType,
        imageURI: String?,
        parentPanel: ResourceDisplayPanel<out ResourcePanel>
    ) : super(resource, placementPolygon, objectType, imageURI, parentPanel)

    constructor(
        resource: R,
        placementPolygon: Polygon,
        objectType: ObjectType,
        parentPanel: ResourceDisplayPanel<out ResourcePanel>
    ) : super(resource, placementPolygon, objectType, parentPanel)

    constructor(
        resource: R,
        placementPolygon: Polygon,
        objectType: ObjectType,
        imageURI: String?,
        parentResourcePanel: PRP,
        drawLayer: DrawLayer?
    ) : super(resource, placementPolygon, objectType, imageURI, parentResourcePanel, drawLayer)

    constructor(
        resource: R,
        placementPolygon: Polygon,
        objectType: ObjectType,
        parentResourcePanel: PRP,
        drawLayer: DrawLayer?
    ) : super(resource, placementPolygon, objectType, parentResourcePanel, drawLayer)

    override fun notifyMouseClicked(evt: MouseEvent) {}

    override fun notifyMouseEntered() {}
}
