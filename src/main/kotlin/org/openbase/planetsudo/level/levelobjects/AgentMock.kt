package org.openbase.planetsudo.level.levelobjects

import org.openbase.planetsudo.game.SwatTeam

class AgentMock : AgentInterface {
    override fun cancelSupport() {
        TODO("Not yet implemented")
    }

    override fun deliverResourceToMothership() {
        TODO("Not yet implemented")
    }

    override fun deployMarker() {
        TODO("Not yet implemented")
    }

    override fun deployMine() {
        TODO("Not yet implemented")
    }

    override fun fightWithAdversaryAgent() {
        TODO("Not yet implemented")
    }

    override fun fightWithAdversaryMothership() {
        TODO("Not yet implemented")
    }

    override val actionPoints: Int
        get() = TODO("Not yet implemented")
    override val angle: Int
        get() = TODO("Not yet implemented")
    override val carryingResourceType: Resource.ResourceType
        get() = TODO("Not yet implemented")
    override val fuel: Int
        get() = TODO("Not yet implemented")
    override val fuelInPercent: Int
        get() = TODO("Not yet implemented")
    override val fuelVolume: Int
        get() = TODO("Not yet implemented")
    override val resourceType: Resource.ResourceType
        get() = TODO("Not yet implemented")
    override val teamPoints: Int
        get() = TODO("Not yet implemented")

    override fun go() {
        TODO("Not yet implemented")
    }

    override fun goLeft(beta: Int) {
        TODO("Not yet implemented")
    }

    override fun goRight(beta: Int) {
        TODO("Not yet implemented")
    }

    override fun goToMarker() {
        TODO("Not yet implemented")
    }

    override fun goToMothership() {
        TODO("Not yet implemented")
    }

    override fun goToResource() {
        TODO("Not yet implemented")
    }

    override fun goToResource(resourceType: Resource.ResourceType) {
        TODO("Not yet implemented")
    }

    override fun goToSupportAgent() {
        TODO("Not yet implemented")
    }

    override fun hasFuel(): Boolean {
        TODO("Not yet implemented")
    }

    override fun hasMine(): Boolean {
        TODO("Not yet implemented")
    }

    override val isAlive: Boolean
        get() = TODO("Not yet implemented")
    override val isAtMothership: Boolean
        get() = TODO("Not yet implemented")
    override val isCarryingResource: Boolean
        get() = TODO("Not yet implemented")

    override fun isCarryingResource(type: Resource.ResourceType): Boolean {
        TODO("Not yet implemented")
    }

    override val isCollisionDetected: Boolean
        get() = TODO("Not yet implemented")
    override val isCommander: Boolean
        get() = TODO("Not yet implemented")
    override val isDisabled: Boolean
        get() = TODO("Not yet implemented")
    override val isFighting: Boolean
        get() = TODO("Not yet implemented")
    override val isSupportOrdered: Boolean
        get() = TODO("Not yet implemented")
    override val isGameOverSoon: Boolean
        get() = TODO("Not yet implemented")
    override val isTouchingResource: Boolean
        get() = TODO("Not yet implemented")

    override fun isTouchingResource(type: Resource.ResourceType): Boolean {
        TODO("Not yet implemented")
    }

    override val isUnderAttack: Boolean
        get() = TODO("Not yet implemented")

    override fun orderFuel(percent: Int) {
        TODO("Not yet implemented")
    }

    override fun orderSupport() {
        TODO("Not yet implemented")
    }

    override fun pickupResource() {
        TODO("Not yet implemented")
    }

    override fun releaseResource() {
        TODO("Not yet implemented")
    }

    override fun repairMothership() {
        TODO("Not yet implemented")
    }

    override fun searchResources() {
        TODO("Not yet implemented")
    }

    override fun seeAdversaryAgent(): Boolean {
        TODO("Not yet implemented")
    }

    override fun seeTeamAgent(): Boolean {
        TODO("Not yet implemented")
    }

    override fun seeAdversaryMothership(): Boolean {
        TODO("Not yet implemented")
    }

    override fun seeLostTeamAgent(): Boolean {
        TODO("Not yet implemented")
    }

    override fun seeMarker(): Boolean {
        TODO("Not yet implemented")
    }

    override fun seeTower(): Boolean {
        TODO("Not yet implemented")
    }

    override fun seeResource(): Boolean {
        TODO("Not yet implemented")
    }

    override fun seeResource(resourceType: Resource.ResourceType): Boolean {
        TODO("Not yet implemented")
    }

    override fun spendTeamAgentFuel(value: Int) {
        TODO("Not yet implemented")
    }

    override fun turnAround() {
        TODO("Not yet implemented")
    }

    override fun turnLeft(beta: Int) {
        TODO("Not yet implemented")
    }

    override fun turnRandom() {
        TODO("Not yet implemented")
    }

    override fun turnRandom(opening: Int) {
        TODO("Not yet implemented")
    }

    override fun turnRight(beta: Int) {
        TODO("Not yet implemented")
    }

    override fun kill() {
        TODO("Not yet implemented")
    }

    override fun isMemberOfSwatTeam(swatTeams: Collection<SwatTeam>): Boolean {
        TODO("Not yet implemented")
    }

    override fun erectTower(type: Tower.TowerType) {
        TODO("Not yet implemented")
    }

    override fun dismantleTower() {
        TODO("Not yet implemented")
    }

    override fun hasTower(): Boolean {
        TODO("Not yet implemented")
    }
}
