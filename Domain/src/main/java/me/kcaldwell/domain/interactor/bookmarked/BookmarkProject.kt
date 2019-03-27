package me.kcaldwell.domain.interactor.bookmarked

import io.reactivex.Completable
import me.kcaldwell.domain.executor.PostExecutionThread
import me.kcaldwell.domain.interactor.CompletableUseCase
import me.kcaldwell.domain.repository.ProjectsRepository
import javax.inject.Inject

open class BookmarkProject @Inject constructor(
    private val projectsRepository: ProjectsRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<BookmarkProject.Params>(postExecutionThread) {

    public override fun buildUseCaseCompletable(params: Params?): Completable {
        if (params == null) throw IllegalArgumentException("Params cannot be null")

        return projectsRepository.bookmarkProject(params.projectId)
    }

    data class Params constructor(val projectId: String) {
        companion object {
            fun forProject(projectId: String) : Params {
                return Params(projectId)
            }
        }
    }
