package org.openbase.planetsudo.level.levelobjects

interface AgentSpecialInterface : AgentBattleInterface {

    /**
     * Abfrage, ob der Agent aktuell unsichtbar ist.
     */
    val isInvisible: Boolean

    /**
     * Abfrage, ob der Agent aktuell sichtbar ist.
     */
    val isVisible: Boolean get() = !isInvisible

    /**
     * Sofern der Agent 3 Tonic besitzt, kann er sich hiermit unsichtbar machen.
     * Unsichtbar heißt, dass kein feindlicher Agent (außer der feindliche Commander) diesen Agenten sehen oder angreifen kann.
     * Ein Agent ist so lange unsichtbar, bis er eine feindliche Einheit angreift oder eine Mine setzt.
     */
    fun makeInvisible()

    /**
     * Diese Abfrage checkt das bevorstehende Ende des Spiels. Wenn es bald
     * vorbei ist, gibt sie true zurück, dann sollte man schleunigst zum
     * Mutterschiff.
     *
     * @return true oder false
     */
    val isGameOverSoon: Boolean

    /**
     * Gibt die Punkte des Teams wieder.
     *
     * @return Den Punktestand als ganze Zahl (z.B. 47)
     */
    val teamPoints: Int

    /**
     * Setzt eine Markierung, die für das ganze Team sichtbar ist. Alle Agenten
     * des Teams können sich auf diese Markierung zubewegen.
     * Treibstoff: 1
     * Aktionspunkte: 5
     */
    fun deployMarker()

    /**
     * Gibt zurück, ob der Agent den Marker sieht.
     *
     * @return true oder false
     */
    fun seeMarker(): Boolean

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
    fun goToMarker()

    /**
     * Befehl zum Aussenden des Hilferufs.
     * Treibstoff: 0
     * Aktionspunkte: 5
     */
    fun requestSupport()

    /**
     * Gehe zu einem Agenten der Hilfe benötigt.
     *
     * Achtung: Der Agent bewegt sich erst geradeaus und dreht sich danach.
     *
     * Aktionspunkte: 4 + (+ 4 wenn resource geladen)
     * Treibstoff: 1
     */
    fun goToSupportAgent()

    /**
     * Der angeforderte Support des Agenten wird beendet.
     * Treibstoff: 1
     * Aktionspunkte: 5
     */
    fun cancelSupport()

    /**
     * Mit einem Shift bewegt sich der Agent für die nächsten Schritte (agent.go...) besonders schnell, sofern er genug Tonic besitzt.
     * Dies kann z. B. strategisch genutzt werden, um feindlichen Angriffen zu entkommen, schneller Hilfe leisten zu können oder
     * Ressourcen schneller zu transportieren.
     *
     * Aktionspunkte: 1
     * Tonic: 1
     */
    fun shift()

    /**
     * Der Agent läuft amok.
     */
    fun kill()
}
