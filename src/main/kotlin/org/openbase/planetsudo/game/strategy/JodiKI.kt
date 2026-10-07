package org.openbase.planetsudo.game.strategy /*-
 * #%L
 * PlanetSudo Artificial Intelligence
 * %%
 * Copyright (C) 2009 - 2024 openbase.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import org.openbase.planetsudo.level.LevelSize
import org.openbase.planetsudo.level.levelobjects.AgentInterface
import org.openbase.planetsudo.level.levelobjects.Resource
import org.openbase.planetsudo.level.levelobjects.Tower
import org.openbase.planetsudo.level.levelobjects.WallDistance

/**
 * Diese Klasse ist eine Vorlage für eine KI im Spiel: PlanetSudo
 * ============================================================
 * @author [Jodi_Pa]
 *
 * Insgesamt gibt es drei verschiedene Level für die KI-Strategy. Um so höher das Level,
 * um so mehr Funktionen stehen für die Entwicklung einer Strategie zur Verfügung.
 * Bitte steiger dich langsamen in die Entwicklung deiner Strategie und beginne mit dem Level 1.
 * Nur so verliest du nicht den Überblick über wichtige Basisfunktionen und kannst deine KI-Strategie
 * Schritt für Schritt weiterentwickeln.
 *
 * - StrategyLevel1: Alle Basisfunktionen die du für die Entwicklung einer KI-Strategie benötigst, mit dem
 *                   Hauptfokus auf Energie tanken und Ressourcen sammeln.
 * - StrategyLevel2: Erweitert die Basisfunktionen um gegen feindliche Agenten zu kämpfen und den Planeten zu erobern.
 * - StrategyLevel3: Schaltet alle Spezialfunktionen frei, um eine mächtige Sudo KI-Strategie zu entwickeln. Wichtig ist
 *                   hierbei die Basisfunktionen nicht zu vernachlässigen, da diese weiterhin die Grundlage für eine gute
 *                   KI-Strategie bilden. Zudem ist es wichtig, dass du dich mit den Spezialfunktionen vertraut machst.
 *                   Nur ein gutes Zusammenspiel aller Funktionen führt zu einer mächtigen und individuellen Strategie.
 */
class JodiKI(agent: AgentInterface) : StrategyLevel3(agent) {

    /**
     * Über diese Methode kannst du angeben wie viele Agenten dein Team besitzen soll.
     * ===============================================================================
     * Bedenke, dass dein Mutterschiff die Aktionspunkte (APs) gleichmäßig auf deine Agenten verteilt.
     * Somit ist ein Team mit vielen Agenten sehr effizient beim Ressourcen sammeln und beim Planeten erforschen,
     * allerdings stehen einem einzelnen Agenten weniger Aktionspunkte für den Kampf
     * mit feindlichen Agenten zur Verfügung wodurch kleine Teams im Kampf stärker sind.
     *
     * @return Anzahl der Agenten
     */
    override fun loadAgentCount() = 6

    /**
     * Hier kannst du die Regeln für deine Agenten definieren.
     *
     * Prioritäten festlegen
     * =====================
     * Die Reihenfolge der Registrierung bestimmt die Priorität der Regeln.
     * So besitzt die erste Regel die geringste und die zuletzt registrierte Regel die höchste Priorität.
     *
     *  geringere Priorität
     *           ⇧
     *         Regel
     *           ⇩
     *   höhere Priorität
     *
     * Swat Teams
     * ==========
     * Swat Teams können verwendet werden, um Regeln nur für bestimmte Agenten zu beschreiben.
     * Swat Teams müssen zuvor über die "loadSwatTeams" Methode erstellt werden.
     * Anschließend kann das "all" der Regeldefinition durch "swat X" ersetzt werden.
     * Eine Regel kann mehreren, durch "and" verbundene Swat Teams zugeteilt werden.
     * Zudem können Swat Teams mit dem "NOT_" prefix von einer Regel ausgeschlossen werden.
     *
     * z.B. "Just Go" swat ALPHA inCase {true } then {agent.go()}
     * od{er "Just Go" swat ALPHA and FOXTROT inCase {true } then agent.go()}
     */
    override fun loadRules() {
        // -------------------------------------------->
        "Just Go" nonCommander inCase { true } then { agent.go() }
        "LowResourceFound" nonCommander inCase { agent.seeResource(Resource.ResourceType.Normal) or agent.seeResource(Resource.ResourceType.DoublePoints) } then { agent.goToResource() }
        "LowResourcePickUp" nonCommander inCase { agent.isTouchingResource(Resource.ResourceType.Normal) or agent.isTouchingResource(Resource.ResourceType.DoublePoints) } then { agent.pickupResource() }
        "AFuelResourceFound" nonCommander inCase { agent.seeResource(Resource.ResourceType.ExtraAgentFuel) && agent.fuelInPercent < 90 } then { agent.goToResource() }
        "AFuelResourcePickUp" nonCommander inCase { agent.isTouchingResource(Resource.ResourceType.ExtraAgentFuel) && agent.fuelInPercent < 90 } then { agent.pickupResource() }
        "MFuelResourceFound" nonCommander inCase { agent.seeResource(Resource.ResourceType.ExtraMothershipFuel) && mothership.fuelInPercent < 80 } then { agent.goToResource() }
        "MFuelResourcePickUp" nonCommander inCase { agent.isTouchingResource(Resource.ResourceType.ExtraMothershipFuel) && mothership.fuelInPercent < 80 } then { agent.pickupResource() }
        "TResourceFound1" nonCommander inCase { agent.seeResource(Resource.ResourceType.Tonic) && !agent.isTonicAtLimit && !agent.seeTeamAgent } then { agent.goToResource(Resource.ResourceType.Tonic) }
        "TResourceFound2" nonCommander inCase { agent.seeResource(Resource.ResourceType.Tonic) && !agent.isTonicAtLimit && agent.seeTeamAgent && !teamAgent.isTouchingResource(Resource.ResourceType.Tonic) } then { agent.goToResource(Resource.ResourceType.Tonic) }
        "TResourcePickUp1" nonCommander inCase { agent.isTouchingResource(Resource.ResourceType.Tonic) && mothership.isMarkerDeployed && !agent.isTonicAtLimit && !agent.seeTeamAgent } then { agent.pickupResource() }
        "TResourcePickUp2" nonCommander inCase { agent.isTouchingResource(Resource.ResourceType.Tonic) && mothership.isMarkerDeployed && !agent.isTonicAtLimit && agent.seeTeamAgent && !teamAgent.isTouchingResource(Resource.ResourceType.Tonic) } then { agent.pickupResource() }
        "TResourcePickUp3" nonCommander inCase { agent.isTouchingResource(Resource.ResourceType.Tonic) && !mothership.isMarkerDeployed && !agent.isTonicAtLimit && !agent.seeTeamAgent } then { agent.pickupResource() }
        "TResourcePickUp4" nonCommander inCase { agent.isTouchingResource(Resource.ResourceType.Tonic) && !mothership.isMarkerDeployed && !agent.isTonicAtLimit && agent.seeTeamAgent && !teamAgent.isTouchingResource(Resource.ResourceType.Tonic) } then { agent.pickupResource() }
        "CarryingResource" nonCommander inCase { agent.isCarryingResource } then { agent.goToMothership() }
        "5ResourceFound" nonCommander inCase { agent.seeResource(Resource.ResourceType.ExtremPoint) && !agent.isCarryingResource(Resource.ResourceType.ExtremPoint) && agent.seeTeamAgent && !teamAgent.isTouchingResource(Resource.ResourceType.ExtremPoint) } then { agent.goToResource() }
        "5ResourcePickUp" nonCommander inCase { agent.isTouchingResource(Resource.ResourceType.ExtremPoint) && !agent.isCarryingResource(Resource.ResourceType.ExtremPoint) && agent.seeTeamAgent && !teamAgent.isTouchingResource(Resource.ResourceType.ExtremPoint) } then { agent.pickupResource() }
        "GoToMarker" nonCommander inCase { mothership.isMarkerDeployed && !agent.seeMarker() && !agent.isCarryingResource } then { agent.goToMarker() }
        "IsAtMarker" nonCommander inCase { mothership.isMarkerDeployed && agent.seeMarker() && !agent.seeResource } then { mothership.clearMarker() }
        "DeliverResource" nonCommander inCase { agent.isCarryingResource && agent.isAtMothership } then { agent.transferResourceToMothership(); agent.turnAround(); agent.turnRandom(30) }
        "Cancel Support" nonCommander inCase { agent.hasRequestedSupport } then {agent.cancelSupport()}
        "Attack Mothership" nonCommander inCase { agent.seeEnemyMothership } then { agent.fightWithEnemyMothership(); agent.go() }
        "Mark Mothership" nonCommander inCase { agent.seeEnemyMothership && !agent.hasRequestedSupport } then { agent.requestSupport() }
        "HelpAgent" nonCommander inCase { mothership.needSomeoneSupport() && !agent.hasRequestedSupport } then { agent.releaseResource(); agent.goToSupportAgent()}
        "Help Stranded" nonCommander inCase { agent.seeLostTeamAgent() && agent.fuelInPercent > 60 } then { agent.spendTeamAgentFuel(300) }
        "GoToRefuel" nonCommander inCase { agent.fuelInPercent < 30 && !agent.isAtMothership && mothership.hasFuel()} then { agent.cancelSupport(); agent.goToMothership() }
        "Refuel" nonCommander inCase { agent.isAtMothership && agent.fuelInPercent < 95 && agent.actionPoints > 100 && mothership.hasFuel()} then { agent.orderFuel(100) }
        // "CounterSearch" nonCommander inCase { agent.isUnderAttack && agent.} then { agent.turnAround() }
        "CounterAttack" nonCommander inCase { agent.seeEnemyAgent } then { agent.fightWithEnemyAgent(); if (!agent.hasRequestedSupport) { agent.requestSupport() }; if (agent.isCarryingResource) { agent.releaseResource() } }
        "Invisible" nonCommander inCase { agent.hasTonicForInvisibility } then { agent.makeInvisible() }
        "Cry for Help" nonCommander inCase { agent.isUnderAttack && !agent.hasRequestedSupport } then { agent.releaseResource(); agent.requestSupport() }
        "GameOverSoon" nonCommander inCase { agent.isGameOverSoon && !agent.isAtMothership } then { agent.goToMothership() }
        "AvoidWall" nonCommander inCase { agent.isCollisionDetected } then { agent.turnRandom() }

        "Explore" commander inCase { !agent.isCollisionDetected } then { go() }
        "Correct far Straight" commander inCase { agent.seeWallAtRight(90, WallDistance.FAR) && agent.seeWallAtRight(45, WallDistance.FAR) } then { agent.goRight(2) }
        "Go Straight" commander inCase { agent.seeWallAtRight(90, WallDistance.CLOSE) } then { agent.go() }
        "Correct near Straight" commander inCase { agent.seeWallAtRight(60, WallDistance.VERY_CLOSE) } then { agent.goLeft(2) }
        "Turn Right" commander inCase { !agent.seeWallAtRight(70, WallDistance.CLOSE) && agent.seeWallAtRight(110, WallDistance.VERY_FAR) } then { agent.goRight(5) }
        "Turn Sharp Right" commander inCase { !agent.seeWallAtRight(110, WallDistance.CLOSE) && !agent.seeWallAtRight(90, WallDistance.FAR) && !agent.seeWallAtRight(45, WallDistance.FAR) && agent.seeWallAtRight(135, WallDistance.VERY_FAR) } then { agent.goRight(45) }
        "Turn Left" commander inCase { agent.seeWallAtRight(0, WallDistance.FAR) } then { agent.goLeft(15) }
        "Turn Sharp Left" commander inCase { agent.seeWallAtRight(0, WallDistance.FAR) && !agent.seeWallAtLeft(45, WallDistance.FAR) } then { agent.goLeft(30) }
        // "Go Straight Narrow" commander inCase { agent.seeWallAtRight(90, WallDistance.CLOSE) && agent.seeWallAtLeft(60, WallDistance.CLOSE) } then {agent.goRight(10)}
        "AFuelResourcePickUp" commander inCase { agent.isTouchingResource(Resource.ResourceType.ExtraAgentFuel) && agent.fuelInPercent < 90 } then { agent.pickupResource() }
        // "TResourceFound" commander inCase { agent.seeResource(Resource.ResourceType.Tonic) && !agent.isTonicAtLimit && !agent.seeTeamAgent } then { agent.goToResource(Resource.ResourceType.Tonic) }
        // "TResourceFound" commander inCase { agent.seeResource(Resource.ResourceType.Tonic) && !agent.isTonicAtLimit && agent.seeTeamAgent && !teamAgent.isTouchingResource(Resource.ResourceType.Tonic) } then { agent.goToResource(Resource.ResourceType.Tonic) }
        "TResourceNotice" commander inCase { agent.seeResource(Resource.ResourceType.Tonic) && !mothership.isMarkerDeployed } then { agent.deployMarker() }
        "TResourcePickUp1" commander inCase { agent.isTouchingResource(Resource.ResourceType.Tonic) && !agent.isTonicAtLimit && !agent.hasTower } then { agent.pickupResource(); agent.deployMarker() }
        "TResourcePickUp2" commander inCase { agent.isTouchingResource(Resource.ResourceType.Tonic) && !agent.isTonicAtLimit && !agent.hasTower && mothership.isMarkerDeployed } then { agent.pickupResource() }
        "IsAtMarker" commander inCase { mothership.isMarkerDeployed && agent.seeMarker() && !agent.seeResource } then { mothership.clearMarker() }
        "GoToRefuel" commander inCase { agent.fuelInPercent < 20 && mothership.hasFuel() } then { agent.goToMothership() }
        "Help Stranded" commander inCase { agent.seeLostTeamAgent() && agent.fuelInPercent > 50 } then { agent.spendTeamAgentFuel(300) }
        "Cancel Support" commander inCase { agent.hasRequestedSupport } then {agent.cancelSupport()}
        "CounterSearch" commander inCase { agent.isUnderAttack } then { agent.turnAround() }
        "Cry for Help" commander inCase { agent.isUnderAttack && !agent.hasRequestedSupport } then { agent.releaseResource(); agent.requestSupport()}
        "Refuel" commander inCase { agent.isAtMothership && agent.fuelInPercent < 95 && agent.actionPoints > 100 && mothership.hasFuel()} then { agent.orderFuel(100) }
        "HelpAgent" commander inCase { mothership.needSomeoneSupport() && !agent.hasRequestedSupport } then { agent.releaseResource(); agent.goToSupportAgent() }
        "Attack Mothership" commander inCase { agent.seeEnemyMothership } then { agent.fightWithEnemyMothership(); agent.go() }
        "Mark Mothership" commander inCase { agent.seeEnemyMothership && !agent.hasRequestedSupport } then { agent.requestSupport() }
        "CounterAttack" commander inCase { agent.seeEnemyAgent } then { agent.fightWithEnemyAgent(); if (!agent.hasRequestedSupport) { agent.requestSupport() }; if (agent.isCarryingResource) { agent.releaseResource() } }
        "Invisible" commander inCase { agent.hasTonicForInvisibility } then { agent.makeInvisible() }
        "GameOverSoon" commander inCase { agent.isGameOverSoon && !agent.isAtMothership } then { agent.goToMothership() }
        "Scan Map" commander inCase { !agent.hasTower && tower.levelSize == LevelSize.UNKNOWN } then { tower.scanLevelSize() }
        "Place Tower" commander inCase { agent.hasTower && agent.actionPoints > 1500 } then { agent.constructTower(Tower.TowerType.ObservationTower) }
        "Not Die" commander inCase { agent.isCollisionDetected } then { agent.turnLeft(5) }
    }

    /**
     * Hier kannst du Swat Teams aus mehreren Agenten bilden.
     * =====================================================
     * Die Agenten werden hierbei über ihre Nummern hinzugefügt. Sind beispielsweise 4 Agenten in der Strategie angegeben,
     * so sind diese über die Nummern 0 - 3 referenzierbar wobei Agent 0 immer für den Commander steht.
     * Bitte beachte somit, dass die Agentennummer nicht größer als Agentenanzahl - 1 sein darf.
     * Ein Agent darf durchaus mehrere Swat Teams zugeteilt werden.
     *
     * ACHTUNG: Die default Swats ALL und COMMANDER können anhand dieser Methode nicht modifiziert werden!
     */
    override fun loadSwatTeams() {
    }
}
