package com.backinfile.support.kotlin


class Random {
    private val random: java.util.Random
    var counter = 0
        private set
    val seed: Long

    @JvmOverloads
    constructor(seed: Long = System.currentTimeMillis()) {
        random = java.util.Random(seed)
        this.seed = seed
    }

    constructor(seed: Long, counter: Int) {
        random = java.util.Random(seed)
        this.seed = seed
        for (i in 0 until counter) {
            next(1)
        }
    }

    fun next(bound: Int): Int {
        counter++
        return random.nextInt(bound)
    }

    fun next(a: Int, b: Int): Int {
        return next(b - a) + a
    }

    fun nextBool(): Boolean {
        return next(2) == 0
    }

    fun <T> randItem(collection: Collection<T>?): T? {
        return if (collection.isNullOrEmpty()) {
            null
        } else KtUtils.get(collection, next(collection.size))
    }

    /**
     * 输入权重，输出一个随机的Index
     * 要求权重不小于0
     */
    fun randWeightIndex(weights: List<Int>): Int {
        val sum = weights.sum()
        var rand = next(sum)
        for (i in weights.indices) {
            val w = weights[i]
            if (rand < w) {
                return i
            }
            rand -= w
        }
        return weights.size - 1
    }

    fun <T> randWeight(weight: List<Int>, list: List<T>): T {
        return list[randWeightIndex(weight)]
    }

    fun copy(): Random {
        return Random(seed, counter)
    }

    companion object {
        val instance = Random()
    }
}
