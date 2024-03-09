package org.openbase.planetsudo.level.levelobjects

open class AgentBattleInterfaceGermanWrapper(private val agent: AgentBattleInterface<*>)
    : AgentBasicInterfaceGermanWrapper(agent) {

    /**
     * Zeigt an, ob der Agent einen feindlichen Agenten sieht.
     *
     * @return true oder false.
     */
    val seheGegerischenAgent get() = agent.seeEnemyAgent

    /**
     * Liefert Informationen über einen feindlichen Agenten in der Nähe.
     */
    val gegnerischerAgent get() = agent.enemyAgent

    /**
     * Der Agent dreht sich zu einem feindlichen Agenten in Sichtweite.
     * Falls kein feindlicher Agent in der Nähe ist, kostet diese Aktion nur APs.
     *
     * Optional kann ein Winkel `beta`° angegeben werden, um den Agenten zusätzlich weiter rechts rum zu drehen.
     *
     * Aktionspunkte: 1
     * Treibstoff: 1
     */
    fun dreheZuGegnerischemAgent(beta: Int = 0) = agent.turnToEnemyAgent(beta)

    /**
     * Gehe zu einem feindlichen Agenten in der Nähe.
     *
     * Achtung: Der Agent bewegt sich erst geradeaus und dreht sich danach.
     *
     * Aktionspunkte: 4 + (+ 4 wenn resource geladen)
     * Treibstoff: 1
     */
    fun geheZuGegnerischemAgent() = agent.goToEnemyAgent()

    /**
     * Der Befehl zum Bekämpfen eines feindlichen Agenten.
     * Wenn ein Agent einen anderen erfolgreich angreift, bekommt er eine Treibstoffbelohnung.
     * Aktionspunkte: 21
     * Treibstoff: 1
     */
    fun kaempfeMitGegnerischemAgent() = agent.fightWithEnemyAgent()

    /**
     * Der Befehl zum Angreifen des feindlichen Mutterschiffs.
     * Aktionspunkte: 30
     * Treibstoff: 1
     */
    fun kaempfeMitGegnerischemMutterschiff() = agent.fightWithEnemyMothership()

    /**
     * Gibt zurück, ob der Agent seine Mine noch trägt.
     *
     * @return true oder false.
     */
    val hatMine get() = agent.hasMine

    /**
     * Der Agent legt seine Mine an seiner aktuellen Position ab.
     * Aktionspunkte: 50
     * Treibstoff: 5
     */
    fun legeMine() = agent.deployMine()

    /**
     * Zeigt an, ob der Agent das feindliche Mutterschiff sieht.
     *
     * @return true oder false.
     */
    val seheGegnerischesMutterschiff get() = agent.seeEnemyMothership

    /**
     * Der Befehl zum Reparieren des eigenen Mutterschiffs. Hierzu muss sich der
     * Agent am Mutterschiff befinden!
     * Aktionspunkte: 30
     * Treibstoff: 1
     */
    fun reparireMutterschiff() = agent.repairMothership()
}
