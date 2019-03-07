package me.kcaldwell.domain.executor

import io.reactivex.Scheduler

/**
 * In keeping our separation of concerns (Domain should not reference Android
 * Framework). So this interface abstracts our observing thread to avoid the
 * Domain having any knowledge of the Android Framework.
 */
interface PostExecutionThread {
    val mScheduler: Scheduler
}