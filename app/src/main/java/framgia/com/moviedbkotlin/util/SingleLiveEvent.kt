package framgia.com.moviedbkotlin.util

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by fs-sournary.
 * Date: 7/23/18.
 * Description: An important class that resolves a common problem:
 * configuration change such as rotate screen.
 * An update can be emmit if MutableLiveData is active and emit again when rotate screen.
 * SingleLiveData is only emmit item when setValue() and call() are called
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(value: T?) {
        pending.set(true)
        super.setValue(value)
    }

    @WorkerThread
    override fun postValue(value: T?) {
        pending.set(true)
        super.postValue(value)
    }

    fun call() {
        value = null
    }
}
