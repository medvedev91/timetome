package me.timeto.shared.vm

import kotlinx.coroutines.flow.*
import me.timeto.shared.*
import me.timeto.shared.db.TaskFolderDb
import me.timeto.shared.db.TaskFolderDb.Companion.sortedFolders

class TabTasksVM : __VM<TabTasksVM.State>() {

    data class TaskFolderUI(
        val folder: TaskFolderDb,
    ) {
        val tabText = folder.name.toTabText()
    }

    data class State(
        val taskFoldersUI: List<TaskFolderUI>,
        val initFolder: TaskFolderDb = DI.getTodayFolder(),
    )

    override val state = MutableStateFlow(
        State(
            taskFoldersUI = DI.taskFolders.sortedFolders().map { TaskFolderUI(it) },
        )
    )

    override fun onAppear() {
        val scope = scopeVM()
        TaskFolderDb.getAscBySortFlow().onEachExIn(scope) { folders ->
            val taskFoldersUI = folders.sortedFolders().map { TaskFolderUI(it) }
            state.update { it.copy(taskFoldersUI = taskFoldersUI) }
        }
    }
}

private fun String.toTabText(): String =
    this.uppercase().split("").joinToString("\n").trim()
