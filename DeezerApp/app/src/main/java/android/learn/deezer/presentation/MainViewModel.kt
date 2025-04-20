package android.learn.deezer.presentation

import androidx.lifecycle.ViewModel
import androidx.media3.session.MediaController
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val controllerFuture: ListenableFuture<MediaController>
) : ViewModel() {
    private var _controller: MediaController? = null
    private val controller: MediaController
        get() = _controller ?: throw RuntimeException("Unexpected error")

    init {
        controllerFuture.addListener({
            _controller = controllerFuture.get()
        }, MoreExecutors.directExecutor())
    }

    override fun onCleared() {
        super.onCleared()
        controller.stop()
        controller.removeMediaItems(0, controller.mediaItemCount)
        MediaController.releaseFuture(controllerFuture)
    }
}