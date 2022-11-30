package com.example.testtask

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testtask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    
    private var player = Player()

    private var monster = Monster()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding.buttonNewPlayer.setOnClickListener {
            player = Player()
            player.create(
                (1..20).random(),
                (1..20).random(),
                (1..10).random(),
                (1..3).random(),
                (3..10).random()
            )
            binding.textViewValueAttackPlayer.text = player.attack.toString()
            binding.textViewValueDefensePlayer.text = player.defense.toString()
            binding.textViewValueHealthPlayer.text = resources.getString(
                R.string.text_view_health_value,
                player.healthPoint,
                player.health
            )
            binding.textViewValueDamagePlayer.text = resources.getString(
                R.string.text_view_damage_value,
                player.damageMin,
                player.damageMax
            )
            binding.buttonHealingPlayer.text =
                resources.getString(R.string.button_healing_player, player.healingCount)
            binding.buttonPunchPlayer.isEnabled = monster.isAlive
            binding.buttonHealingPlayer.isEnabled = true
            binding.buttonPunchMonster.isEnabled = monster.isAlive
        }

        binding.buttonNewWrongPlayer.setOnClickListener {
            binding.textViewInfo.append(resources.getString(R.string.log_try_wrong_player))
            player = Player()
            val randAttack = (-5..25).random()
            val randDefense = (-5..25).random()
            val randHealth = (-5..25).random()
            val randDamageMin = (-5..25).random()
            val randDamageMax = (-5..25).random()
            val result = player.create(
                randAttack,
                randDefense,
                randHealth,
                randDamageMin,
                randDamageMax
            )

            if (result == Creature.SUCCESS_CREATE) {
                binding.textViewValueAttackPlayer.text = player.attack.toString()
                binding.textViewValueDefensePlayer.text = player.defense.toString()
                binding.textViewValueHealthPlayer.text = resources.getString(
                    R.string.text_view_health_value,
                    player.healthPoint,
                    player.health
                )
                binding.textViewValueDamagePlayer.text = resources.getString(
                    R.string.text_view_damage_value,
                    player.damageMin,
                    player.damageMax
                )
                binding.buttonHealingPlayer.text =
                    resources.getString(R.string.button_healing_player, player.healingCount)
                binding.buttonPunchPlayer.isEnabled = monster.isAlive
                binding.buttonHealingPlayer.isEnabled = true
                binding.buttonPunchMonster.isEnabled = monster.isAlive
                binding.textViewInfo.append(resources.getString(R.string.log_wrong_player_success))
            } else {
                when (result) {
                    Creature.WRONG_ATTACK -> binding.textViewInfo.append(
                        resources.getString(
                            R.string.log_wrong_attack,
                            randAttack
                        )
                    )
                    Creature.WRONG_DEFENSE -> binding.textViewInfo.append(
                        resources.getString(
                            R.string.log_wrong_defense,
                            randDefense
                        )
                    )
                    Creature.WRONG_HEALTH -> binding.textViewInfo.append(
                        resources.getString(
                            R.string.log_wrong_health,
                            randHealth
                        )
                    )
                    Creature.WRONG_DAMAGE -> binding.textViewInfo.append(resources.getString(R.string.log_wrong_damage))
                }
            }
        }

        binding.buttonNewMonster.setOnClickListener {
            monster = Monster()
            monster.create(
                (1..20).random(),
                (1..20).random(),
                (1..10).random(),
                (1..3).random(),
                (3..10).random()
            )
            binding.textViewValueAttackMonster.text = monster.attack.toString()
            binding.textViewValueDefenseMonster.text = monster.defense.toString()
            binding.textViewValueHealthMonster.text = resources.getString(
                R.string.text_view_health_value,
                monster.healthPoint,
                monster.health
            )
            binding.textViewValueDamageMonster.text = resources.getString(
                R.string.text_view_damage_value,
                monster.damageMin,
                monster.damageMax
            )
            binding.buttonPunchMonster.isEnabled = player.isAlive
            binding.buttonPunchPlayer.isEnabled = player.isAlive
        }

        binding.buttonHealingPlayer.setOnClickListener {
            if (player.healingCount > 0) {
                if (player.healing() == Player.SUCCESS_HEALING) {
                    binding.textViewValueHealthPlayer.text = resources.getString(
                        R.string.text_view_health_value,
                        player.healthPoint,
                        player.health
                    )
                    binding.buttonHealingPlayer.text =
                        resources.getString(R.string.button_healing_player, player.healingCount)
                    binding.textViewInfo.append(
                        resources.getString(
                            R.string.log_healing_success,
                            player.toString()
                        )
                    )
                } else {
                    binding.textViewInfo.append(
                        resources.getString(
                            R.string.log_healing_fault,
                            player.toString()
                        )
                    )
                }
            }

            if (player.healingCount == 0) {
                binding.buttonHealingPlayer.isEnabled = false
            }
        }

        binding.buttonPunchPlayer.setOnClickListener {
            punch(player, monster)
        }

        binding.buttonPunchMonster.setOnClickListener {
            punch(monster, player)
        }

        binding.buttonClear.setOnClickListener {
            binding.textViewInfo.text = resources.getString(R.string.text_view_default_info)
        }
    }

    private fun punch(attackingCreature: Creature, defensiveCreature: Creature) {
        binding.textViewInfo.append(
            resources.getString(
                R.string.log_try_punch,
                attackingCreature.toString()
            )
        )

        var modAttack =
            if (attackingCreature.attack - defensiveCreature.defense >= 0) {
                attackingCreature.attack - defensiveCreature.defense + 1
            } else {
                1
            }

        while (modAttack > 0) {
            val valueOfCube = (1..6).random()
            binding.textViewInfo.append(resources.getString(R.string.log_dice_roll, valueOfCube))
            if (valueOfCube > 4) {
                val damage = (attackingCreature.damageMin..attackingCreature.damageMax).random()
                val realDamage = defensiveCreature.setDamage(damage)
                binding.textViewInfo.append(
                    resources.getString(
                        R.string.log_punch_success,
                        attackingCreature.toString(),
                        realDamage
                    )
                )
                if (defensiveCreature is Player) {
                    binding.textViewValueHealthPlayer.text = resources.getString(
                        R.string.text_view_health_value,
                        defensiveCreature.healthPoint,
                        defensiveCreature.health
                    )
                } else {
                    binding.textViewValueHealthMonster.text = resources.getString(
                        R.string.text_view_health_value,
                        defensiveCreature.healthPoint,
                        defensiveCreature.health
                    )
                }

                if (!defensiveCreature.isAlive) {
                    binding.textViewInfo.append(
                        resources.getString(
                            R.string.log_death,
                            defensiveCreature.toString()
                        )
                    )
                    if (defensiveCreature is Player) {
                        binding.buttonPunchPlayer.isEnabled = false
                        binding.buttonHealingPlayer.isEnabled = false
                        binding.buttonPunchMonster.isEnabled = false
                    } else {
                        binding.buttonPunchMonster.isEnabled = false
                        binding.buttonPunchPlayer.isEnabled = false
                    }
                }

                return
            }

            modAttack--
        }

        binding.textViewInfo.append(
            resources.getString(
                R.string.log_punch_fault,
                attackingCreature.toString()
            )
        )
    }
}