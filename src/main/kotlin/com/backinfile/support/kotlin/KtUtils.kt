package com.backinfile.support.kotlin

import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


typealias Action1<T> = (T) -> Unit

object KtUtils {
    fun <T> get(collection: Collection<T>, index: Int): T {
        var curIndex = index
        for (t in collection) {
            if (curIndex-- == 0) {
                return t
            }
        }
        throw IndexOutOfBoundsException("")
    }

}


inline val Number.d get() = this.toDouble()
inline val Number.f get() = this.toFloat()

/**
 * 只允许设置一次的属性
 *
 * usage: `var value:Int by Delegates.once()`
 */
fun <T : Any> Delegates.once(init: T? = null): ReadWriteProperty<Any?, T> = object : ReadWriteProperty<Any?, T> {
    private var value: T? = init

    public override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("Property ${property.name} should be initialized before get.")
    }

    public override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (this.value != null) throw IllegalStateException("Property ${property.name} cannot be set more than once.")
        this.value = value
    }
}

