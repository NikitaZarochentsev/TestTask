package com.example.testtask

class Player : Creature() {

    companion object {
        const val SUCCESS_HEALING = 1
        const val FAULT_HEALING = 0
    }

    fun healing(): Int {
        if (healingCount >= 0 && healthPoint != health) {
            healthPoint += health / 2
            healingCount--
            if (healthPoint > health) {
                healthPoint = health
            }

            return SUCCESS_HEALING
        }

        return FAULT_HEALING
    }

    override fun toString(): String {
        return "Игрок"
    }
}