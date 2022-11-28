package com.example.testtask

open class Creature() {
    var attack = 0
        protected set
    var defense = 0
        protected set
    var health = 0
        protected set
    var damageMin = 0
        protected set
    var damageMax = 0
        protected set

    var isAlive = false
        protected set
    var healthPoint = 0
        protected set
    var healingCount = 3
        protected set

    companion object {
        const val SUCCESS_CREATE = 0
        const val WRONG_ATTACK = 1
        const val WRONG_DEFENSE = 2
        const val WRONG_HEALTH = 3
        const val WRONG_DAMAGE = 4
    }

    fun create(attack: Int, defense: Int, health: Int, damageMin: Int, damageMax: Int): Int {
        if (attack in 1..20) {
            this.attack = attack
        } else {
            return WRONG_ATTACK
        }

        if (defense in 1..20) {
            this.defense = defense
        } else {
            return WRONG_DEFENSE
        }

        if (health > 0) {
            this.health = health
        } else {
            return WRONG_HEALTH
        }

        if (damageMin in 0 .. damageMax) {
            this.damageMin = damageMin
            this.damageMax = damageMax
        } else {
            return WRONG_DAMAGE
        }

        healthPoint = this.health
        isAlive = true
        return SUCCESS_CREATE
    }

    fun setDamage(damage: Int): Int {
        val realDamage: Int

        if (healthPoint - damage > 0) {
            healthPoint -= damage
            realDamage = damage
        } else {
            realDamage = healthPoint
            healthPoint = 0
            isAlive = false
        }

        return realDamage
    }

    override fun toString(): String {
        return "Существо"
    }
}