package org.openbase.planetsudo.level.levelobjects

interface AgentBattleInterface : AgentBasicInterface {

    /**
     * Liefert Informationen über einen feindlichen Agenten in der Nähe.
     */
    val adversaryAgent: GlobalAgentInterface

    /**
     * Der Agent legt seine Mine an seiner aktuellen Position ab.
     * Aktionspunkte: 50
     * Treibstoff: 5
     */
    fun deployMine()

    /**
     * Der Befehl zum Bekämpfen eines feindlichen Agenten.
     * Wenn ein Agent einen anderen erfolgreich angreift, bekommt er eine Treibstoffbelohnung.
     * Aktionspunkte: 21
     * Treibstoff: 1
     */
    fun fightWithAdversaryAgent()

    /**
     * Der Befehl zum Angreifen des feindlichen Mutterschiffs.
     * Aktionspunkte: 30
     * Treibstoff: 1
     */
    fun fightWithAdversaryMothership()

    /**
     * Gehe zu einem feindlichen Agenten in der Nähe.
     *
     * Achtung: Der Agent bewegt sich erst geradeaus und dreht sich danach.
     *
     * Aktionspunkte: 4 + (+ 4 wenn resource geladen)
     * Treibstoff: 1
     */
    fun goToAdversaryAgent()

    /**
     * Gibt zurück, ob der Agent seine Mine noch trägt.
     *
     * @return true oder false.
     */
    val hasMine: Boolean

    /**
     * Der Befehl zum Reparieren des eigenen Mutterschiffs. Hierzu muss sich der
     * Agent am Mutterschiff befinden!
     * Aktionspunkte: 30
     * Treibstoff: 1
     */
    fun repairMothership()

    /**
     * Zeigt an, ob der Agent einen feindlichen Agenten sieht.
     *
     * @return true oder false.
     */
    val seeAdversaryAgent: Boolean

    /**
     * Zeigt an, ob der Agent das feindliche Mutterschiff sieht.
     *
     * @return true oder false.
     */
    val seeAdversaryMothership: Boolean

    /**
     * Der Agent dreht sich zu einem feindlichen Agenten in Sichtweite.
     * Falls kein feindlicher Agent in der Nähe ist, kostet diese Aktion nur APs.
     *
     * Optional kann ein Winkel `beta`° angegeben werden, um den Agenten zusätzlich weiter rechts rum zu drehen.
     *
     * Aktionspunkte: 1
     * Treibstoff: 1
     */
    fun turnToAdversaryAgent(beta: Int = 0)
}
