/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.game

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
enum class GameObjectImages(imagesURL: String) {
    Default("mothership.png"),
    Mothership("mothership2.png"),
    Agent("agent-HD.png"),
    AgentCommander("commander-HD.png"),
    ResourceNormal("resource1.png"),
    ResourceDoublePoints("resource3.png"),
    ResourceExtremPoint("resource6.png"),
    ResourceExtraAgentFuel("resource5.png"),
    ResourceExtraMothershipFuel("resource7.png"),
    ResourceMine("mine.png"),
    Tower("tower.png"),
    DefenceTowerTop("observation-tower-top.png"),
    ObservationTowerTop("defence-tower-top.png"),
    ;

    @JvmField
    val imagesURL: String

    init {
        this.imagesURL = "${GameObjectImages.IMAGE_DIRECTORY}/$imagesURL"
    }

    companion object {
        const val IMAGE_DIRECTORY: String = "img"
    }
}
