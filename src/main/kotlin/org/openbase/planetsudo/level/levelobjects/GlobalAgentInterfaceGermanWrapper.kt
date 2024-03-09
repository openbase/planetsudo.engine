package org.openbase.planetsudo.level.levelobjects

open class GlobalAgentInterfaceGermanWrapper(private val agent: GlobalAgentInterface<*>) {

    /**
     * Gibt an, ob der Agent noch lebt.
     *
     * @return true oder false.
     */
    val istAmLeben: Boolean get() = agent.isAlive

    /**
     * Gibt zurück, ob der Agent beim Mutterschiff ist.
     *
     * @return true oder false.
     */
    val istAmMutterschiff: Boolean get() = agent.isAtMothership

    /**
     * Gibt zurück, ob der Agent ein Commander ist oder nicht.
     *
     * @return true oder false
     */
    val istCommander: Boolean get() = agent.isCommander

    /**
     * Liefert die aktuell verfügbaren Aktionspunkte des Agenten.
     *
     * @return Die Aktionspunkte als ganze Zahl. (z.B. 2051)
     */
    val aktionspunkte: Int get() = agent.actionPoints

    /**
     * Gibt die Richtung des Agenten in Grad wieder. Osten = 0° Süden = 90°
     * Westen = 180° Norden = 270°
     *
     * @return Den Winkel von 0 - 360° als ganze Zahl. (z.B. 128)
     */
    val winkel: Int get() = agent.angle

    /**
     * Zeigt den aktuellen Tonic Bestand des Agenten an.
     *
     * 0 = Kein Tonic
     * 1 = Reicht für einen Shift
     * 2 = Reicht für zwei Shifts
     * 3 = Reicht für drei Shifts oder ermöglicht es den Agenten unsichtbar zu machen.
     *
     * @return Die aktuelle Tonic Menge als ganz Zahl zwischen 0 - 3.
     */
    val tonic: Int get() = agent.tonic

    /**
     * Zeigt den aktuellen Tonic Bestand des Agenten in Prozent.
     *
     *   0 % = Kein Tonic
     *  33 % = Reicht für einen Shift
     *  66 % = Reicht für zwei Shifts
     * 100 % = Reicht für drei Shifts oder ermöglicht es den Agenten unsichtbar zu machen.
     *
     * @return Die aktuelle Tonic Menge als ganz Zahl zwischen 0 - 100.
     */
    val tonicInProzent: Int get() = agent.tonicInPercent

    /**
     *  Gibt an, ob der Agent die maximale Anzahl an Tonic besitzt und somit genug Tonic hat um sich unsichtbar zu machen.
     */
    val istTonicAmLimit: Boolean get() = agent.isTonicAtLimit

    /**
     * Gibt zurück, ob der Agent mindestens ein Tonic besitzt.
     *
     * @return true oder false.
     */
    val hatTonic: Boolean get() = agent.hasTonic

    /**
     * Gibt zurück, ob der Agent genug Tonic besitzt, um sich unsichtbar zu machen.
     *
     * @return true oder false.
     */
    val hatTonicFuerUnsichbarkeit: Boolean get() = agent.hasTonicForInvisibility

    /**
     * Zeigt den verbliebenen Treibstoff des Agenten an.
     *
     * @return Den verbliebenen Treibstoff als ganz Zahl.
     */
    val treibstoff: Int get() = agent.fuel

    /**
     * Gibt den verbliebenen Treibstoff in Prozent an.
     *
     * @return Treibstoffwert in Prozent als ganze Zahl. (z.B. 47 bei 47%
     * verbliebenen Treibstoff)
     */
    val treibstoffInProzent: Int get() = agent.fuelInPercent

    /**
     * Gibt das maximal Treibstoffvolumen eines Agenten an.
     *
     * @return
     */
    val treibstoffVolumen: Int get() = agent.fuelVolume

    /**
     * Gibt zurück, ob der Agent aktuell Treibstoff besitzt.
     *
     * @return true oder false.
     */
    val hatTreibstoff: Boolean get() = agent.hasFuel

    /**
     * Gibt den Typ der Resource wieder, welche der Agent berührt.
     *
     * @return Den Ressourcenwert
     */
    val ressourcenTyp: Resource.ResourceType get() = agent.resourceType

    /**
     * Gibt zurück, ob der Agent eine Resource berührt und diese somit
     * aufgeben kann.
     *
     * @return true oder false.
     */
    val istRessourceAmBeruehren: Boolean get() = agent.isTouchingResource

    /**
     * Gibt zurück, ob der Agent eine Resource vom Typ `type` berührt und diese somit aufheben kann.
     *
     * @param type
     * @return true oder false.
     */
    fun istRessourceAmBeruehren(type: Resource.ResourceType): Boolean = agent.isTouchingResource(type)

    /**
     * Gibt die Art der Resource an, die der Agent aktuell trägt.
     * Wird aktuell keine Resource getragen, so wird ResourceType.UNKNOWN zurück gegeben.
     */
    val resourceVomTyp: Resource.ResourceType get() = agent.carryingResourceType

    /**
     * Gibt an, ob der Agent eine Resource trägt.
     *
     * @return true oder false
     */
    val istEineResourceAmTragen: Boolean get() = agent.isCarryingResource

    /**
     * Gibt an, ob der Agent eine Resource vom `type` trägt.
     *
     * @param type
     * @return true oder false.
     */
    fun istEineResourceAmTragenVomTypen(type: Resource.ResourceType): Boolean = agent.isCarryingResource(type)

    /**
     * Abfrage, ob der Agent sich aktuell im Kampf befindet oder nicht.
     *
     * @return true oder false.
     */
    val istAmKämpfen: Boolean get() = agent.isFighting

    /**
     * Gibt an, ob der Agent unter Beschuss steht.
     *
     * @return true oder false.
     */
    val wirdAngegriffen: Boolean get() = agent.isUnderAttack

    /**
     * Gibt zurück, ob der Agent gerade shifted.
     */
    val istAmSchiften: Boolean get() = agent.isShifting

    /**
     * Abfrage, ob dieser agent Support angefordert hat.
     * Um zu erfahren, ob ein anderer Agent Hilfe benötigt muss das Mutterschiff befragt werden.
     *
     * @return true oder false
     */
    val hatUnterstuetzungAngefordert: Boolean get() = agent.hasRequestedSupport
}
