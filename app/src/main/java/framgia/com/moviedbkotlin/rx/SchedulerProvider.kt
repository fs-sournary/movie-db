package framgia.com.moviedbkotlin.rx

import io.reactivex.Scheduler

/**
 * Created: 12/13/18.
 * By: Sang
 * Description:
 */
interface SchedulerProvider {

    fun io(): Scheduler

    fun ui(): Scheduler
}
