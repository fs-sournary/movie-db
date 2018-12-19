package framgia.com.moviedbkotlin.repository

/**
 * Created: 17/12/2018
 * By: Sang
 * Description:
 */
enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}


class NetworkState private constructor(val status: Status, val message: String? = null) {

    companion object {

        val RUNNING = NetworkState(Status.RUNNING)
        val SUCCESS = NetworkState(Status.SUCCESS)

        fun error(message: String?): NetworkState = NetworkState(Status.FAILED, message)
    }
}
