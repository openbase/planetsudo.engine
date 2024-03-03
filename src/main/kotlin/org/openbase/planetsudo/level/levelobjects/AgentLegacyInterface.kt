package org.openbase.planetsudo.level.levelobjects

import org.openbase.planetsudo.game.SwatTeam

interface AgentLegacyInterface : AgentSpecialInterface {
    /**
     * Gibt zurück, ob der Agent den Turm sieht.
     *
     * @return true oder false
     */
    @Deprecated("NOT YET SUPPORTED")
    fun seeTower(): Boolean

    /**
     * Überprüft, ob dieser Agent einen Turm aufbauen könnte.
     * Bedenke das nur Commander einen Turm trägt!
     * @return
     */
    @Deprecated("NOT YET SUPPORTED")
    val hasTower: Boolean

    /**
     * Errichtet den Turm an der Position des Commander.
     * Diese Aktion kann nur durch den Commander durchgeführt werden!
     *
     * Es gibt hierbei zwei Arten von Türmen:
     * - Einen Verteidigungsturm (DefenceTower) der feindliche Agenten für alle Agenten des Teams sichtbar macht und diese angreift.
     * - Einen Beobachtungsturm (ObservationTower) der Ressourcen in Reichweite für alle Agenten des Teams sichtbar macht und zudem Agenten in Reichweite mit Energie beliefert.
     *
     * @param type Hier rüber kannst du den Turmtypen auswählen welcher errichtet werden soll.
     */
    @Deprecated("NOT YET SUPPORTED")
    fun constructTower(type: Tower.TowerType)

    /**
     * Baut einen Turm wieder ab der zuvor aufgestellt wurde.
     * Diese Aktion kann nur vom Commander durchgeführt werden, und zwar nur dann, wenn er in unmittelbarer Nähe des Turms ist.
     */
    @Deprecated("NOT YET SUPPORTED")
    fun deconstructTower()

    /**
     * Überprüft, ob dieser Agent in mindestens einer der übergebenen SwatTeams enthalten ist.
     *
     * @param swatTeams
     * @return
     */
    @Deprecated("Use swat rule declaration instead.")
    fun isMemberOfSwatTeam(swatTeams: Set<SwatTeam>): Boolean

    /**
     * Gibt an, ob der Agent eine Resource vom `type` trägt.
     *
     * @param type
     * @return true oder false.
     */
    @Deprecated("Typo fixed", replaceWith = ReplaceWith("isCarryingResource"))
    fun isCarringResource(type: Resource.ResourceType): Boolean = isCarryingResource(type)

    /**
     * Zeigt an, ob der Agent eine Resource trägt.
     *
     * @return true oder false
     */
    @Deprecated("Typo fixed", replaceWith = ReplaceWith("isCarryingResource"))
    val isCarringResource: Boolean get() = isCarryingResource
}
