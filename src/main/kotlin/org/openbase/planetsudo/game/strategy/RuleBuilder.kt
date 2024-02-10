package org.openbase.planetsudo.game.strategy

import org.openbase.planetsudo.game.SwatTeam

data class RuleBuilder(
    var name: String? = null,
    private var swat: Set<SwatTeam> = emptySet(),
    var constraint: (() -> Boolean)? = null,
    var action: (() -> Unit)? = null,
) {
    fun swat(vararg additionalSwat: SwatTeam): RuleBuilder = apply { swat = swat.plus(additionalSwat.toSet()) }
    fun swat(additionalSwat: Collection<SwatTeam>): RuleBuilder = apply { swat = swat.plus(additionalSwat.toSet()) }
    infix fun inCase(constraint: () -> Boolean): RuleBuilder = apply { this.constraint = constraint }
    fun action(action: () -> Unit): RuleBuilder = apply { this.action = action }

    fun merge(builder: RuleBuilder) = apply {
        name = builder.name ?: name
        swat = swat.plus(builder.swat)
        constraint = builder.constraint ?: constraint
        action = builder.action ?: action
    }

    fun build(): Rule {
        if (this@RuleBuilder.name.isNullOrBlank()) {
            error("$this has no name!")
        }

        if (this@RuleBuilder.constraint == null) {
            error("$this has no constraint!")
        }

        if (this@RuleBuilder.action == null) {
            error("$this has no action!")
        }

        if (this@RuleBuilder.swat.isEmpty()) {
            error("$this has no agent assignment!")
        }

        return object : Rule(name = this@RuleBuilder.name!!, swatTeams = this@RuleBuilder.swat) {
            override fun constraint(): Boolean = this@RuleBuilder.constraint?.let { it() }!!
            override fun action(): Unit = this@RuleBuilder.action?.let { it() }!!
        }
    }
}
