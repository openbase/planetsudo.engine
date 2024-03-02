package org.openbase.planetsudo.level.levelobjects

interface AgentBasicInterface : GlobalAgentInterface {
    /**
     * Gibt an, ob ein Zusammenstoß mit einer Wand bevorsteht.
     *
     * @return true oder false.
     */
    val isCollisionDetected: Boolean

    /**
     * Gibt zurück, ob der Agent Bewegungsunfähig ist. (Zerstört
     * oder ohne Treibstoff)
     *
     * @return true oder false.
     */
    val isDisabled: Boolean

    /**
     * Gibt zurück, ob der Agent eine Resource berührt und diese somit
     * aufgeben kann.
     *
     * @return true oder false.
     */
    val isTouchingResource: Boolean

    /**
     * Gibt an, ob der Agent noch lebt.
     *
     * @return true oder false.
     */
    val isAlive: Boolean

    /**
     * Gibt zurück, ob der Agent beim Mutterschiff ist.
     *
     * @return true oder false.
     */
    val isAtMothership: Boolean

    /**
     * Der Agent übergibt die Resource dem Mutterschiff.
     * Treibstoff: 1
     * Aktionspunkte: 10
     */
    fun deliverResourceToMothership()

    /**
     * Der Agent bewegt sich geradeaus.
     * Aktionspunkte: 3 (+ 3 wenn resource geladen)
     * Treibstoff: 1
     */
    fun go()

    /**
     * Der Agent bewegt sich geradeaus und dreht sich anschließend um `beta`° nach links.
     * Aktionspunkte: 3 (+ 3 wenn resource geladen)
     * Treibstoff: 1
     *
     *
     * @param beta Drehwinkel
     */
    fun goLeft(beta: Int)

    /**
     * Der Agent bewegt sich geradeaus und dreht sich anschließend um `beta`° nach rechts.
     * Aktionspunkte: 3 (+ 3 wenn resource geladen)
     * Treibstoff: 1
     *
     * @param beta Drehwinkel
     */
    fun goRight(beta: Int)

    /**
     * Der Agent bewegt sich auf das Mutterschiff zu. Hierbei wird ein Weg
     * berechnet, welcher auf kürzester Distanz zum Mutterschiff
     * fährt. Um Hindernisse bewegt sich der Agent hierbei automatisch
     * herum.
     *
     * Achtung: Der Agent bewegt sich erst geradeaus und dreht sich danach.
     *
     * Aktionspunkte: 4 (+ 4 wenn resource geladen)
     * Treibstoff: 1
     */
    fun goToMothership()

    /**
     * Der Agent bewegt sich zur Resource.
     *
     * Achtung: Der Agent bewegt sich erst geradeaus und dreht sich danach.
     *
     * Aktionspunkte: 4 (+ 4 wenn resource geladen)
     * Treibstoff: 1
     */
    fun goToResource()

    /**
     * Der Agent bewegt sich zur Resource vom angegebenen Typen.
     *
     * Achtung: Der Agent bewegt sich erst geradeaus und dreht sich danach.
     *
     * Aktionspunkte: 4 + (+ 4 wenn resource geladen)
     * Treibstoff: 1
     * @param resourceType
     */
    fun goToResource(resourceType: Resource.ResourceType)

    /**
     * Der Agent fordert `percent` Prozent Treibstoff vom Mutterschiff an.
     *
     * @param percent Der anzufordernde Treibstoffwert in Prozent als ganze Zahl
     * von 0 - 100.
     * Aktionspunkte: 20 + 2 für jeden getankten Treibstoff.
     */
    fun orderFuel(percent: Int)

    /**
     * Der Befehl zum Aufnehmen einer Resource, wenn der Agent sie anfassen kann.
     * Treibstoff: 1
     * Aktionspunkte: 10 + Normal(200), DoublePoints(400), ExtremPoint(700), ExtraAgentFuel(400), ExtraMothershipFuel(400), Mine(200)
     */
    fun pickupResource()

    /**
     * Wenn der Agent eine Resource trägt, lässt er sie wieder fallen.
     * Treibstoff: 0
     * Aktionspunkte: 0
     */
    fun releaseResource()

    /**
     * Der Agent dreht sich einmal im Kreis. Sieht er während der Drehung eine
     * Resource, unterbricht er und schaut in ihre Richtung.
     * Aktionspunkte: 10 pro 20° Drehung
     * Treibstoff: 1 pro 20° Drehung
     */
    fun searchResources()

    /**
     * Zeigt an, ob der Agent einen Agenten des eigenen Teams sieht.
     *
     * @return true oder false.
     */
    val seeTeamAgent: Boolean

    /**
     * Zeigt an, ob sich in Sicht des Agenten ein Teammitglied ohne Treibstoff
     * befindet.
     *
     * @return true oder false.
     */
    fun seeLostTeamAgent(): Boolean

    /**
     * Zeigt an, ob der Agent eine Resource sehen kann.
     *
     * @return true oder false.
     */
    val seeResource: Boolean

    /**
     * Zeigt an, ob der Agent eine Resource vom angegebenen Typen sehen kann.
     *
     * @return true oder false.
     */
    fun seeResource(resourceType: Resource.ResourceType): Boolean

    /**
     * Der Agent spendet einem Teammitglied Treibstoff
     *
     * @param value Treibstoff. Ein Agent ist Bewegungsunfhig, solange er
     * Treibstoff spendet!
     * Aktionspunkte: value * 2
     * Treibstoff: 1 + value
     * @param value Der zu spendende Treibstoff.
     */
    fun spendTeamAgentFuel(value: Int)

    /**
     * Der Agent dreht sich um 180 Grad in die entgegengesetzte Richtung.
     * Aktionspunkte: 1
     * Treibstoff: 1
     */
    fun turnAround()

    /**
     * Der Agent dreht sich um Winkel beta nach links.
     *
     * @param beta Gewünschter Winkel in Grad (0 - 360)
     * Aktionspunkte: 1
     * Treibstoff: 1
     */
    fun turnLeft(beta: Int)

    /**
     * Der Agent dreht sich in eine zufällige Richtung.
     * Aktionspunkte: 1
     * Treibstoff: 1
     */
    fun turnRandom()

    /**
     * Der Agent dreht sich in eine beliebige, zufällige Richtung mit dem
     * Öffnungswinkel.
     *
     * `opening`°. Aktionspunkte: 1
     * Treibstoff: 1
     *
     * @param opening
     */
    fun turnRandom(opening: Int)

    /**
     * Der Agent dreht sich um Winkel `beta`° nach rechts.
     *
     * @param beta Gewünschter Winkel in Grad (0 - 360)
     * Aktionspunkte: 1
     * Treibstoff: 1
     */
    fun turnRight(beta: Int)

    /**
     * Der Agent dreht sich zu einer nahen Resource.
     * Falls er keine findet, dreht er sich nicht
     *
     * Aktionspunkte: 1
     * Treibstoff: 1
     */
    fun turnToResource()

    /**
     * Der Agent dreht sich zu einer nahen Resource eines bestimmten Typs.
     * Falls er keine findet, dreht er sich nicht
     *
     * Aktionspunkte: 1
     * Treibstoff: 1
     * @param resourceType Der Typ der Resource
     */
    fun turnToResource(resourceType: Resource.ResourceType)
}
