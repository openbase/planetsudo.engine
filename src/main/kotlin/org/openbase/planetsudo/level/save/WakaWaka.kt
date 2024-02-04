/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.level.save

import org.openbase.planetsudo.geometry.Base2D
import org.openbase.planetsudo.geometry.Direction2D
import org.openbase.planetsudo.level.AbstractLevel
import org.openbase.planetsudo.level.ResourceRandomRectangle
import org.openbase.planetsudo.level.levelobjects.Resource.ResourceType
import java.awt.Color
import java.awt.Polygon

/**
 *
 * @author lena
 */
class WakaWaka : AbstractLevel() {
    public override fun loadLevelBorderPolygon() = Polygon().apply {
        addPoint(50, 50)
        addPoint(350, 50)
        addPoint(350, 250) //
        addPoint(450, 350)
        addPoint(700, 350)
        addPoint(700, 200)
        addPoint(600, 300)
        addPoint(550, 300)
        addPoint(450, 200) //
        addPoint(450, 50)
        addPoint(800, 50)
        addPoint(800, 100)
        addPoint(850, 100)
        addPoint(850, 150)
        addPoint(900, 150)
        addPoint(900, 100)
        addPoint(950, 100)
        addPoint(950, 50)
// 		addPoint(1250, 50);
// 		addPoint(1250, 250);
// 		addPoint(1150, 300);
// 		addPoint(1050, 250);
// 		addPoint(950, 250);
// 		addPoint(900, 350);
// 		addPoint(900, 450);
// 		addPoint(1000, 450);
// 		addPoint(1000, 450);
// 		addPoint(1000, 450);
// 		addPoint(950, 400);
// 		addPoint(950, 350);
// 		addPoint(1000, 300);
// 		addPoint(1050, 300);
// 		addPoint(1150, 350);
// 		addPoint(1150, 400);
// 		addPoint(1250, 500);
// 		addPoint(1250, 550);
// 		addPoint(1200, 600);
// 		addPoint(1150, 600);
// 		addPoint(1150, 550);
// 		addPoint(1100, 550);
// 		addPoint(1100, 650);
// 		addPoint(1200, 650);
// 		addPoint(1300, 600);
// 		addPoint(1300, 500);
// 		addPoint(1250, 400);
// 		addPoint(1350, 300);
// 		addPoint(1500, 300);
        addPoint(1500, 50)
        addPoint(1500, 600)
        addPoint(1450, 600)
        addPoint(1450, 650)
        addPoint(1400, 650)
        addPoint(1400, 700)
        addPoint(1450, 700)
        addPoint(1450, 750)
        addPoint(1500, 750)
        addPoint(1500, 1100)
        addPoint(1350, 1100) //
        addPoint(1250, 1000)
        addPoint(1250, 950)
        addPoint(1350, 850)
        addPoint(1200, 850)
        addPoint(1200, 1100)
        addPoint(1300, 1200) //
        addPoint(1500, 1200)
        addPoint(1500, 1500)
        addPoint(1200, 1500)
        // 	addPoint(1200, 1300);
        // 	addPoint(1100, 1200);
        // 	addPoint(800, 1200);
        // 	addPoint(800, 1300);
        // 	addPoint(950, 1250);
        // 	addPoint(1000, 1250);
        // 	addPoint(1100, 1350);
        addPoint(1100, 1500)
        addPoint(750, 1500)
        addPoint(650, 1400)
        addPoint(550, 1500)
        addPoint(150, 1500)
        addPoint(250, 1400)
        addPoint(300, 1450)
        addPoint(400, 1350)
        addPoint(350, 1300)
        addPoint(450, 1200)
        addPoint(550, 1300)
        addPoint(550, 1050)
        addPoint(600, 1000)
        addPoint(850, 1000)
        addPoint(850, 850)
        addPoint(800, 850)
        addPoint(750, 950)
        addPoint(600, 800)
        addPoint(700, 750)
        addPoint(700, 700)
        addPoint(550, 700)
        addPoint(550, 950)
        addPoint(500, 1000)
        addPoint(250, 1000)
        addPoint(350, 1100)
        addPoint(250, 1200)
        addPoint(200, 1150)
        addPoint(100, 1250)
        addPoint(150, 1300)
        addPoint(50, 1400)
        addPoint(50, 1000)
        addPoint(150, 900)
        addPoint(50, 800)
        addPoint(50, 450)
        // 	addPoint(200, 450);
        // 	addPoint(300, 550);
        // 	addPoint(300, 600);
        // 	addPoint(200, 700);
        // 	addPoint(350, 700);
        // 	addPoint(350, 450);
        // 	addPoint(250, 350);
        addPoint(50, 350)
        addPoint(50, 50)
    }

    override fun loadHomePositions() = listOf(
        Base2D(100.0, 100.0, Direction2D.DOWN),
        Base2D(1450.0, 1450.0, Direction2D.DOWN),
    )

    override fun loadResourcePlacement() = listOf(
        ResourceRandomRectangle(50, 50, 1500, 1500, 50, ResourceType.Normal),
        ResourceRandomRectangle(450, 50, 1500, 1100, 25, ResourceType.DoublePoints),
        ResourceRandomRectangle(1000, 50, 1500, 550, 10, ResourceType.ExtremPoint),
        ResourceRandomRectangle(50, 50, 1500, 1100, 20, ResourceType.ExtraAgentFuel),
        ResourceRandomRectangle(50, 50, 1500, 1100, 10, ResourceType.ExtraMothershipFuel),
    )

    override fun loadLevelColor(): Color = Color(10, 125, 0)

    override fun loadLevelWallPolygons(): List<Polygon> = emptyList<Polygon>()
}
