package org.openbase.planetsudo.level.levelobjects

interface GlobalAgentInterface<DE : GlobalAgentInterfaceGermanWrapper> {

    val de get() = GlobalAgentInterfaceGermanWrapper(this)

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
     * Gibt zurück, ob der Agent ein Commander ist oder nicht.
     *
     * @return true oder false
     */
    val isCommander: Boolean

    /**
     * Liefert die aktuell verfügbaren Aktionspunkte des Agenten.
     *
     * @return Die Aktionspunkte als ganze Zahl. (z.B. 2051)
     */
    val actionPoints: Int

    /**
     * Gibt die Richtung des Agenten in Grad wieder. Osten = 0° Süden = 90°
     * Westen = 180° Norden = 270°
     *
     * @return Den Winkel von 0 - 360° als ganze Zahl. (z.B. 128)
     */
    val angle: Int

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
    val tonic: Int

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
    val tonicInPercent: Int

    /**
     *  Gibt an, ob der Agent die maximale Anzahl an Tonic besitzt und somit genug Tonic hat um sich unsichtbar zu machen.
     */
    val isTonicAtLimit: Boolean get() = tonic == Agent.MAX_TONIC

    /**
     * Gibt zurück, ob der Agent mindestens ein Tonic besitzt.
     *
     * @return true oder false.
     */
    val hasTonic: Boolean
        get() = tonic > 0

    /**
     * Gibt zurück, ob der Agent genug Tonic besitzt, um sich unsichtbar zu machen.
     *
     * @return true oder false.
     */
    val hasTonicForInvisibility: Boolean
        get() = tonic == Agent.MAX_TONIC

    /**
     * Zeigt den verbliebenen Treibstoff des Agenten an.
     *
     * @return Den verbliebenen Treibstoff als ganz Zahl.
     */
    val fuel: Int

    /**
     * Gibt den verbliebenen Treibstoff in Prozent an.
     *
     * @return Treibstoffwert in Prozent als ganze Zahl. (z.B. 47 bei 47%
     * verbliebenen Treibstoff)
     */
    val fuelInPercent: Int

    /**
     * Gibt das maximal Treibstoffvolumen eines Agenten an.
     *
     * @return
     */
    val fuelVolume: Int

    /**
     * Gibt zurück, ob der Agent aktuell Treibstoff besitzt.
     *
     * @return true oder false.
     */
    val hasFuel: Boolean

    /**
     * Gibt den Typ der Resource wieder, welche der Agent berührt.
     *
     * @return Den Ressourcenwert
     */
    val resourceType: Resource.ResourceType

    /**
     * Gibt zurück, ob der Agent eine Resource berührt und diese somit
     * aufgeben kann.
     *
     * @return true oder false.
     */
    val isTouchingResource: Boolean

    /**
     * Gibt zurück, ob der Agent eine Resource vom Typ `type` berührt und diese somit aufheben kann.
     *
     * @param type
     * @return true oder false.
     */
    fun isTouchingResource(type: Resource.ResourceType): Boolean

    /**
     * Gibt die Art der Resource an, die der Agent aktuell trägt.
     * Wird aktuell keine Resource getragen, so wird ResourceType.UNKNOWN zurück gegeben.
     */
    val carryingResourceType: Resource.ResourceType

    /**
     * Gibt an, ob der Agent eine Resource trägt.
     *
     * @return true oder false
     */
    val isCarryingResource: Boolean

    /**
     * Gibt an, ob der Agent eine Resource vom `type` trägt.
     *
     * @param type
     * @return true oder false.
     */
    fun isCarryingResource(type: Resource.ResourceType): Boolean

    /**
     * Abfrage, ob der Agent sich aktuell im Kampf befindet oder nicht.
     *
     * @return true oder false.
     */
    val isFighting: Boolean

    /**
     * Gibt an, ob der Agent unter Beschuss steht.
     *
     * @return true oder false.
     */
    val isUnderAttack: Boolean

    /**
     * Gibt zurück, ob der Agent gerade shifted.
     */
    val isShifting: Boolean

    /**
     * Abfrage, ob dieser agent Support angefordert hat.
     * Um zu erfahren, ob ein anderer Agent Hilfe benötigt muss das Mutterschiff befragt werden.
     *
     * @return true oder false
     */
    val hasRequestedSupport: Boolean
}
