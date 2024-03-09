package org.openbase.planetsudo.level.levelobjects

open class AgentBasicInterfaceGermanWrapper(private val agent: AgentBasicInterface<*>) :
    GlobalAgentInterfaceGermanWrapper(agent) {
    /**
     * Gibt an, ob ein Zusammenstoß mit einer Wand bevorsteht.
     *
     * @return true oder false.
     */
    val istKollisionErkannt get() = agent.isCollisionDetected

    /**
     * Gibt zurück, ob der Agent Bewegungsunfähig ist. (Zerstört
     * oder ohne Treibstoff)
     *
     * @return true oder false.
     */
    val istBewegungsunfaehig get() = agent.isDisabled

    /**
     * Der Agent übergibt die Resource dem Mutterschiff, sofern er sich am Mutterschiff befindet.
     * Treibstoff: 1
     * Aktionspunkte: 10
     * Gewinn:
     *     ResourceType.Normal              ->   10 Punkte
     *     ResourceType.DoublePoints        ->   20 Punkte
     *     ResourceType.ExtremPoint         ->   50 Punkte
     *     ResourceType.ExtraMothershipFuel ->   20 % Mutterschiff Treibstoff
     */
    fun uebertrageRessourceAnMutterschiff() = agent.transferResourceToMothership()

    /**
     * Der Agent bewegt sich geradeaus.
     * Aktionspunkte: 3 (+ 3 wenn resource geladen)
     * Treibstoff: 1
     */
    fun gehe() = agent.go()

    /**
     * Der Agent bewegt sich geradeaus und dreht sich anschließend um `beta`° nach links.
     * Aktionspunkte: 3 (+ 3 wenn resource geladen)
     * Treibstoff: 1
     *
     *
     * @param beta Drehwinkel
     */
    fun geheLinks(beta: Int) = agent.goLeft(beta)

    /**
     * Der Agent bewegt sich geradeaus und dreht sich anschließend um `beta`° nach rechts.
     * Aktionspunkte: 3 (+ 3 wenn resource geladen)
     * Treibstoff: 1
     *
     * @param beta Drehwinkel
     */
    fun geheRechts(beta: Int) = agent.goRight(beta)

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
    fun geheZuMutterschiff() = agent.goToMothership()

    /**
     * Der Agent bewegt sich zur Resource.
     *
     * Achtung: Der Agent bewegt sich erst geradeaus und dreht sich danach.
     *
     * Aktionspunkte: 4 (+ 4 wenn resource geladen)
     * Treibstoff: 1
     */
    fun geheZuRessource() = agent.goToResource()

    /**
     * Der Agent fordert so viel Treibstoff vom Mutterschiff an bis sein Tank zu `percent` Prozent voll ist.
     *
     * @param percent Der anzufordernde Treibstoffwert in Prozent als ganze Zahl
     * zwischen 0 - 100.
     * Aktionspunkte: 20 + 2 für jeden getankten Treibstoff.
     */
    fun bestelleTreibstoff(percent: Int) = agent.orderFuel(percent)

    /**
     * Der Befehl zum Aufnehmen einer Resource, wenn der Agent sie anfassen kann.
     * Treibstoff: 1
     * Aktionspunkte: 10 +
     *     ResourceType.Normal              ->  200 AP
     *     ResourceType.DoublePoints        ->  400 AP
     *     ResourceType.ExtremPoint         ->  700 AP
     *     ResourceType.Tonic               -> 1000 AP
     *     ResourceType.ExtraAgentFuel      ->  400 AP
     *     ResourceType.ExtraMothershipFuel ->  400 AP
     *     ResourceType.Mine                ->    0 AP
     *
     * Gewinn:
     *     ResourceType.Normal              ->    1 Resource wird getragen
     *     ResourceType.DoublePoints        ->    1 Resource wird getragen
     *     ResourceType.ExtremPoint         ->    1 Resource wird getragen
     *     ResourceType.Tonic               ->    1 Tonic
     *     ResourceType.ExtraAgentFuel      ->   10 % Agenten Treibstoff
     *     ResourceType.ExtraMothershipFuel ->    1 Resource wird getragen
     *     ResourceType.Mine                ->    1 Tod
     *
     */
    fun nehmeRessourceAuf() = agent.pickupResource()

    /**
     * Wenn der Agent eine Resource trägt, lässt er sie wieder fallen.
     * Treibstoff: 0
     * Aktionspunkte: 0
     */
    fun ressourceFallenLassen() = agent.releaseResource()

    /**
     * Der Agent dreht sich einmal im Kreis. Sieht er während der Drehung eine
     * Resource, unterbricht er und schaut in ihre Richtung.
     * Aktionspunkte: 10 pro 20° Drehung
     * Treibstoff: 1 pro 20° Drehung
     */
    fun sucheRessource() = agent.searchResources()

    /**
     * Der Agent bewegt sich zur Resource vom angegebenen Typen.
     *
     * Achtung: Der Agent bewegt sich erst geradeaus und dreht sich danach.
     *
     * Aktionspunkte: 4 + (+ 4 wenn resource geladen)
     * Treibstoff: 1
     * @param resourceType
     */
    fun geheZuRessource(resourceType: Resource.ResourceType) = agent.goToResource(resourceType)

    /**
     * Zeigt an, ob der Agent eine Resource sehen kann.
     *
     * @return true oder false.
     */
    val seheRessource get() = agent.seeResource

    /**
     * Zeigt an, ob der Agent eine Resource vom angegebenen Typen sehen kann.
     *
     * @return true oder false.
     */
    fun seheRessource(resourceType: Resource.ResourceType): Boolean = agent.seeResource(resourceType)

    /**
     * Zeigt an, ob der Agent einen Agenten des eigenen Teams sieht.
     *
     * @return true oder false.
     */
    val seheTeamAgent get() = agent.seeTeamAgent

    /**
     * Liefert Informationen über einen Agenten deines Teams in der Nähe.
     */
    val teamAgent get() = agent.teamAgent

    /**
     * Zeigt an, ob sich in Sicht des Agenten ein Teammitglied ohne Treibstoff
     * befindet.
     *
     * @return true oder false.
     */
    fun seheVerlorenenTeamAgent(): Boolean = agent.seeLostTeamAgent()

    /**
     * Der Agent spendet einem Teammitglied Treibstoff
     *
     * @param value Treibstoff. Ein Agent ist Bewegungsunfhig, solange er
     * Treibstoff spendet!
     * Aktionspunkte: value * 2
     * Treibstoff: 1 + value
     * @param value Der zu spendende Treibstoff.
     */
    fun spendeTreibstoffAnTeamAgent(value: Int) = agent.spendTeamAgentFuel(value)

    /**
     * Der Agent dreht sich um 180 Grad in die entgegengesetzte Richtung.
     * Aktionspunkte: 1
     * Treibstoff: 1
     */
    fun dreheUm() = agent.turnAround()

    /**
     * Der Agent dreht sich um Winkel beta nach links.
     *
     * @param beta Gewünschter Winkel in Grad (0 - 360)
     * Aktionspunkte: 1
     * Treibstoff: 1
     */
    fun dreheLinks(beta: Int) = agent.turnLeft(beta)

    /**
     * Der Agent dreht sich in eine zufällige Richtung.
     * Aktionspunkte: 1
     * Treibstoff: 1
     */
    fun dreheZufaellig() = agent.turnRandom()

    /**
     * Der Agent dreht sich in eine beliebige, zufällige Richtung mit dem
     * Öffnungswinkel.
     *
     * `opening`°. Aktionspunkte: 1
     * Treibstoff: 1
     *
     * @param opening
     */
    fun dreheZufaellig(opening: Int) = agent.turnRandom(opening)

    /**
     * Der Agent dreht sich um Winkel `beta`° nach rechts.
     *
     * @param beta Gewünschter Winkel in Grad (0 - 360)
     * Aktionspunkte: 1
     * Treibstoff: 1
     */
    fun dreheRechts(beta: Int) = agent.turnRight(beta)

    /**
     * Der Agent dreht sich zu einer nahen Resource.
     * Falls er keine findet, dreht er sich nicht
     *
     * Aktionspunkte: 1
     * Treibstoff: 1
     */
    fun dreheZuRessource() = agent.turnToResource()

    /**
     * Der Agent dreht sich zu einer nahen Resource eines bestimmten Typs.
     * Falls er keine findet, dreht er sich nicht
     *
     * Aktionspunkte: 1
     * Treibstoff: 1
     * @param resourceType Der Typ der Resource
     */
    fun dreheZuRessource(resourceType: Resource.ResourceType) = agent.turnToResource(resourceType)
}
