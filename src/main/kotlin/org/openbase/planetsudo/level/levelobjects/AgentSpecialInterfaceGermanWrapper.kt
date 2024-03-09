package org.openbase.planetsudo.level.levelobjects

open class AgentSpecialInterfaceGermanWrapper(private val agent: AgentSpecialInterface<out AgentSpecialInterfaceGermanWrapper>)
    : AgentBattleInterfaceGermanWrapper(agent) {

    /**
     * Gibt an, ob ein Zusammenstoß mit einer Wand zur linken Seite bevorsteht.
     *
     * @return true oder false.
     */
    fun istKollisionLinksErkannt(beta: Int) = agent.isCollisionDetectedAtLeft(beta)

    /**
     * Gibt an, ob ein Zusammenstoß mit einer Wand zur rechten Seite bevorsteht.
     *
     * @return true oder false.
     */
    fun istKollisionRechtsErkannt(beta: Int) = agent.isCollisionDetectedAtRight(beta)

    /**
     * Abfrage, ob der Agent aktuell unsichtbar ist.
     */
    val istUnsichtbar get() = agent.isInvisible

    /**
     * Abfrage, ob der Agent aktuell sichtbar ist.
     */
    val istSichtbar: Boolean get() = !istUnsichtbar

    /**
     * Sofern der Agent 3 Tonic besitzt, kann er sich hiermit unsichtbar machen.
     * Unsichtbar heißt, dass kein feindlicher Agent (außer der feindliche Commander) diesen Agenten sehen oder angreifen kann.
     * Ein Agent ist so lange unsichtbar, bis er eine feindliche Einheit angreift oder eine Mine setzt.
     */
    fun unsichtbarMachen() = agent.makeInvisible()

    /**
     * Diese Abfrage checkt das bevorstehende Ende des Spiels. Wenn es bald
     * vorbei ist, gibt sie true zurück, dann sollte man schleunigst zum
     * Mutterschiff.
     *
     * @return true oder false
     */
    val istSpielBaldVorbei get() = agent.isGameOverSoon

    /**
     * Gibt die Punkte des Teams wieder.
     *
     * @return Den Punktestand als ganze Zahl (z.B. 47)
     */
    val teamPunkte get() = agent.teamPoints

    /**
     * Setzt eine Markierung, die für das ganze Team sichtbar ist. Alle Agenten
     * des Teams können sich auf diese Markierung zubewegen.
     * Treibstoff: 1
     * Aktionspunkte: 5
     */
    fun setzeMarkierung() = agent.deployMarker()

    /**
     * Gibt zurück, ob der Agent den Marker sieht.
     *
     * @return true oder false
     */
    fun seheMarkierung() = agent.seeMarker()

    /**
     * Der Agent bewegt sich in Richtung einer Markierung, falls dieser gesetzt
     * wurde. Hierbei wird ein Weg berechnet, welcher auf kürzester Distanz
     * zum Marker fährt. Um Hindernisse bewegt sich der Agent hierbei
     * automatisch herum.
     *
     * Achtung: Der Agent bewegt sich erst geradeaus und dreht sich danach.
     *
     * Aktionspunkte: 4 (+ 4 wenn resource geladen)
     * Treibstoff: 1
     */
    fun geheZuMarkierung() = agent.goToMarker()

    /**
     * Befehl zum Aussenden des Hilferufs.
     * Treibstoff: 0
     * Aktionspunkte: 5
     */
    fun fordereHilfeAn() = agent.requestSupport()

    /**
     * Gehe zu einem Agenten der Hilfe benötigt.
     *
     * Achtung: Der Agent bewegt sich erst geradeaus und dreht sich danach.
     *
     * Aktionspunkte: 4 + (+ 4 wenn resource geladen)
     * Treibstoff: 1
     */
    fun geheZuHilfeBenoetigendemAgenten() = agent.goToSupportAgent()

    /**
     * Der angeforderte Support des Agenten wird beendet.
     * Treibstoff: 1
     * Aktionspunkte: 5
     */
    fun brecheHilfeAb() = agent.cancelSupport()

    /**
     * Mit einem Shift bewegt sich der Agent für die nächsten Schritte (agent.go...) besonders schnell, sofern er genug Tonic besitzt.
     * Dies kann z. B. strategisch genutzt werden, um feindlichen Angriffen zu entkommen, schneller Hilfe leisten zu können oder
     * Ressourcen schneller zu transportieren.
     *
     * Aktionspunkte: 1
     * Tonic: 1
     */
    fun shifte() = agent.shift()

    /**
     * Der Agent läuft amok.
     */
    fun toeteDich() = agent.kill()
}
