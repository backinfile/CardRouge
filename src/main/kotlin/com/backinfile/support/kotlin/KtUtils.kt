package com.backinfile.support.kotlin

import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class KtUtils {
}

val Number.d get() = this.toDouble()
val Number.f get() = this.toFloat()


inline fun <T, C : Comparable<C>> maxOf(a: T, b: T, key: (T) -> C) = if (key(a) > key(b)) a else b


/**
 * 只允许设置一次的属性
 *
 * usage: `var value:Int by Delegates.once()`
 */
fun <T : Any> Delegates.once(v: T? = null): ReadWriteProperty<Any?, T> = object : ReadWriteProperty<Any?, T> {
    private var value: T? = v

    public override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("Property ${property.name} should be initialized before get.")
    }

    public override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (this.value != null) throw IllegalStateException("Property ${property.name} cannot be set more than once.")
        this.value = value
    }
}