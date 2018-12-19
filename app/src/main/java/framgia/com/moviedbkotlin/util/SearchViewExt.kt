package framgia.com.moviedbkotlin.util

import androidx.appcompat.widget.SearchView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created: 17/07/2018
 * By: Sang
 * Description:
 */
fun SearchView.querySearch(oldQuery: String?): Observable<String> {
    val subject = PublishSubject.create<String>()
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let { subject.onComplete() }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean =
            if (oldQuery == newText) false else {
                newText?.let { subject.onNext(it) }
                true
            }
    })
    return subject
}
