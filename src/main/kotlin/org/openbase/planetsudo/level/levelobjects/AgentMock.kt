package org.openbase.planetsudo.level.levelobjects

import org.openbase.planetsudo.game.SwatTeam

class AgentMock : AgentInterface {
    override fun cancelSupport() {
        error("Mock does not offer any functionality.")
    }

    override fun deliverResourceToMothership() {
        error("Mock does not offer any functionality.")
    }

    override fun deployMarker() {
        error("Mock does not offer any functionality.")
    }

    override fun deployMine() {
        error("Mock does not offer any functionality.")
    }

    override fun fightWithAdversaryAgent() {
        error("Mock does not offer any functionality.")
    }

    override fun fightWithAdversaryMothership() {
        error("Mock does not offer any functionality.")
    }

    override val actionPoints: Int
        get() = error("Mock does not offer any functionality.")
    override val angle: Int
        get() = error("Mock does not offer any functionality.")
    override val fuel: Int
        get() = error("Mock does not offer any functionality.")
    override val fuelInPercent: Int
        get() = error("Mock does not offer any functionality.")
    override val tonic: Int
        get() = error("Mock does not offer any functionality.")
    override val tonicInPercent: Int
        get() = error("Mock does not offer any functionality.")
    override val fuelVolume: Int
        get() = error("Mock does not offer any functionality.")
    override val resourceType: Resource.ResourceType
        get() = error("Mock does not offer any functionality.")
    override val teamPoints: Int
        get() = error("Mock does not offer any functionality.")

    override fun go() {
        error("Mock does not offer any functionality.")
    }

    override fun goLeft(beta: Int) {
        error("Mock does not offer any functionality.")
    }

    override fun goRight(beta: Int) {
        error("Mock does not offer any functionality.")
    }

    override fun goToMarker() {
        error("Mock does not offer any functionality.")
    }

    override fun goToMothership() {
        error("Mock does not offer any functionality.")
    }

    override fun goToResource() {
        error("Mock does not offer any functionality.")
    }

    override fun goToResource(resourceType: Resource.ResourceType) {
        error("Mock does not offer any functionality.")
    }

    override fun goToSupportAgent() {
        error("Mock does not offer any functionality.")
    }

    override fun goToAdversaryAgent() {
        error("Mock does not offer any functionality.")
    }

    override fun hasFuel(): Boolean {
        error("Mock does not offer any functionality.")
    }

    override fun hasMine(): Boolean {
        error("Mock does not offer any functionality.")
    }

    override val isAlive: Boolean
        get() = error("Mock does not offer any functionality.")
    override val isAtMothership: Boolean
        get() = error("Mock does not offer any functionality.")
    override val isCarryingResource: Boolean
        get() = error("Mock does not offer any functionality.")

    override fun isCarryingResource(type: Resource.ResourceType): Boolean {
        error("Mock does not offer any functionality.")
    }

    override val isInvisible: Boolean
        get() = error("Mock does not offer any functionality.")
    override val isCollisionDetected: Boolean
        get() = error("Mock does not offer any functionality.")
    override val isCommander: Boolean
        get() = error("Mock does not offer any functionality.")
    override val isDisabled: Boolean
        get() = error("Mock does not offer any functionality.")
    override val isFighting: Boolean
        get() = error("Mock does not offer any functionality.")
    override val isSupportOrdered: Boolean
        get() = error("Mock does not offer any functionality.")
    override val isGameOverSoon: Boolean
        get() = error("Mock does not offer any functionality.")
    override val isTouchingResource: Boolean
        get() = error("Mock does not offer any functionality.")

    override fun isTouchingResource(type: Resource.ResourceType): Boolean {
        error("Mock does not offer any functionality.")
    }

    override val isUnderAttack: Boolean
        get() = error("Mock does not offer any functionality.")

    override fun orderFuel(percent: Int) {
        error("Mock does not offer any functionality.")
    }

    override fun orderSupport() {
        error("Mock does not offer any functionality.")
    }

    override fun pickupResource() {
        error("Mock does not offer any functionality.")
    }

    override fun releaseResource() {
        error("Mock does not offer any functionality.")
    }

    override fun repairMothership() {
        error("Mock does not offer any functionality.")
    }

    override fun searchResources() {
        error("Mock does not offer any functionality.")
    }

    override fun seeAdversaryAgent(): Boolean {
        error("Mock does not offer any functionality.")
    }

    override fun seeTeamAgent(): Boolean {
        error("Mock does not offer any functionality.")
    }

    override fun seeAdversaryMothership(): Boolean {
        error("Mock does not offer any functionality.")
    }

    override fun seeLostTeamAgent(): Boolean {
        error("Mock does not offer any functionality.")
    }

    override fun seeMarker(): Boolean {
        error("Mock does not offer any functionality.")
    }

    override fun seeTower(): Boolean {
        error("Mock does not offer any functionality.")
    }

    override fun seeResource(): Boolean {
        error("Mock does not offer any functionality.")
    }

    override fun seeResource(resourceType: Resource.ResourceType): Boolean {
        error("Mock does not offer any functionality.")
    }

    override fun spendTeamAgentFuel(value: Int) {
        error("Mock does not offer any functionality.")
    }

    override fun turnAround() {
        error("Mock does not offer any functionality.")
    }

    override fun turnLeft(beta: Int) {
        error("Mock does not offer any functionality.")
    }

    override fun turnRandom() {
        error("Mock does not offer any functionality.")
    }

    override fun turnRandom(opening: Int) {
        error("Mock does not offer any functionality.")
    }

    override fun turnRight(beta: Int) {
        error("Mock does not offer any functionality.")
    }

    override fun turnToResource() {
        error("Mock does not offer any functionality.")
    }

    override fun turnToResource(resourceType: Resource.ResourceType) {
        error("Mock does not offer any functionality.")
    }

    override fun kill() {
        error("Mock does not offer any functionality.")
    }

    override fun isMemberOfSwatTeam(swatTeams: Set<SwatTeam>): Boolean {
        error("Mock does not offer any functionality.")
    }

    override fun constructTower(type: Tower.TowerType) {
        error("Mock does not offer any functionality.")
    }

    override fun deconstructTower() {
        error("Mock does not offer any functionality.")
    }

    override fun hasTower(): Boolean {
        error("Mock does not offer any functionality.")
    }

    override fun makeInvisible() {
        error("Mock does not offer any functionality.")
    }

    override fun shift() {
        error("Mock does not offer any functionality.")
    }

    override fun isShifting(): Boolean {
        error("Mock does not offer any functionality.")
    }
}
