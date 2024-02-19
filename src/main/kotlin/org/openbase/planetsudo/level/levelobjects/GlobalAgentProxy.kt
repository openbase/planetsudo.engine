package org.openbase.planetsudo.level.levelobjects

class GlobalAgentProxy(val agent: AgentInterface) : GlobalAgentInterface {
    override val isCommander: Boolean
        get() = agent.isCommander
    override val isFighting: Boolean
        get() = agent.isFighting
    override val isSupportOrdered: Boolean
        get() = agent.isSupportOrdered
    override val isUnderAttack: Boolean
        get() = agent.isUnderAttack
    override val actionPoints: Int
        get() = agent.actionPoints
    override val angle: Int
        get() = agent.angle
    override val fuel: Int
        get() = agent.fuel
    override val tonic: Int
        get() = agent.tonic
    override val tonicInPercent: Int
        get() = agent.tonicInPercent
    override val fuelInPercent: Int
        get() = agent.fuelInPercent
    override val fuelVolume: Int
        get() = agent.fuelVolume
    override val resourceType: Resource.ResourceType
        get() = agent.resourceType
    override val isCarryingResource: Boolean
        get() = agent.isCarryingResource
    override val hasFuel: Boolean
        get() = agent.hasFuel

    override fun isCarryingResource(type: Resource.ResourceType): Boolean = agent.isCarryingResource(type)
    override fun isTouchingResource(type: Resource.ResourceType): Boolean = agent.isTouchingResource(type)
    override val isShifting: Boolean
        get() = agent.isShifting
    override val carryingResourceType: Resource.ResourceType
        get() = agent.carryingResourceType
}
