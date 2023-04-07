package com.backinfile.cardRouge.buff

import com.backinfile.cardRouge.board.Board
import com.backinfile.cardRouge.dungeon.Dungeon
import com.backinfile.cardRouge.human.Human
import java.util.function.Predicate
import kotlin.reflect.KClass

abstract class BuffContainer {
    lateinit var dungeon: Dungeon
    lateinit var board: Board
    lateinit var human: Human

    private val fixedBuffs: MutableList<Buff> = ArrayList() // 卡牌固有buff（技能）

    private val buffs: MutableList<Buff> = ArrayList() // 持续拥有的buff（可以删除，失效）

    private val auraBuffs: MutableList<Buff> = ArrayList() // 光环buff，每次光环更新重新计算

    fun addAuraBuff(block: () -> Buff) {
        val buff = block();
        buff.container = this
        buff.isAura = true
        auraBuffs.add(buff)
    }

    fun addBuff(buff: Buff) {
        buff.container = this
        buffs.add(buff)
    }

    fun addFixedBuff(buff: Buff) {
        buff.container = this
        fixedBuffs.add(buff)
    }

    fun removeBuff(buff: Buff?) {
        buffs.remove(buff)
    }

    fun removeBuffs(predicate: Predicate<Buff?>) {
        buffs.removeIf(predicate)
    }

    fun hasBuff(buffClass: KClass<out Buff>): Boolean {
        for (buff in getAllBuffs()) {
            if (buffClass.isInstance(buff)) {
                return true
            }
        }
        return false
    }

    fun getAllBuffs(): List<Buff> {
        if (fixedBuffs.isEmpty() && buffs.isEmpty() && auraBuffs.isEmpty()) {
            return listOf()
        }
        return fixedBuffs + buffs + auraBuffs;
    }

    fun getAllKeywordBuff(): List<KeywordBuff> {
        return getAllBuffs().filterIsInstance<KeywordBuff>()
    }

}