package timeto.shared

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import timeto.shared.db.IntervalModel
import timeto.shared.vm.__VM

class WatchAppVM : __VM<WatchAppVM.State>() {

    private var isSynced = false

    data class State(
        val isAppReady: Boolean,
        val syncBtnTextOrNull: String?,
    )

    override val state = MutableStateFlow(
        State(
            isAppReady = false,
            syncBtnTextOrNull = null,
        )
    )

    override fun onAppear() {
        scopeVM().launchEx {

            initKmmDeferred.join()

            /**
             * DO NOT sync data on each onAppear(). Example:
             * - Open the ticker's sheet to start the activity and start the activity;
             * - On sheet's closing the onAppear() would be triggered;
             * - Without "doForceOrOnce = false" synchronization will occur immediately.
             *   So the old data from the iPhone is synchronized, and only then,
             *   after the custom delay, will the new data come.
             *  As a result, the UI will first display the current activity because of the
             *  call to start...WithLocal(), then it will return to the old one because of
             *  early synchronization, and then it will return to the current one again.
             */
            sync(doForceOrOnce = false)

            if (IntervalModel.getLastOneOrNull() == null) {

                IntervalModel
                    .getLastOneOrNullFlow()
                    .filterNotNull()
                    .onEachExIn(this) {
                        setAppIsReady()
                    }

                launchEx {
                    delay(2_000L)
                    if (!state.value.isAppReady)
                        state.update { it.copy(syncBtnTextOrNull = "Sync") }
                }
                return@launchEx
            }

            setAppIsReady()
        }
    }

    fun sync(doForceOrOnce: Boolean) {
        if (!isSynced || doForceOrOnce) {
            isSynced = true
            WatchToIosSync.sync()
        }
    }

    private fun setAppIsReady() {
        state.update { it.copy(isAppReady = true) }
    }
}
