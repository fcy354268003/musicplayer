package com.fcy.musicplayer.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.concurrent.ConcurrentHashMap

/**
 * 单例LiveData管理者
 */
object LiveDataManager {
    private val container: ConcurrentHashMap<String, MutableLiveData<out Any>> = ConcurrentHashMap()
    fun <T : Any> with(name: String): MutableLiveData<out Any> {
        if (container[name] == null) {
            container[name] = MutableLiveData<T>()
        }
        return container[name]!!
    }
}