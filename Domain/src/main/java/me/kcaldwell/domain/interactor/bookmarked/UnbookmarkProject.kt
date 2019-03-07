package me.kcaldwell.domain.interactor.bookmarked

import io.reactivex.Completable
import me.kcaldwell.domain.executor.PostExecutionThread
import me.kcaldwell.domain.interactor.CompletableUseCase
import me.kcaldwell.domain.repository.ProjectsRepository
import java.lang.IllegalArgumentException
import javax.inject.Inject

open class UnbookmarkProject @Inject constructor(
    private val projectsRepository: ProjectsRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<UnbookmarkProject.Params>(postExecutionThread) {

    override fun buildUseCaseCompletable(params: Params?): Completable {
        if (params == null) throw IllegalArgumentException("Params must not be null")

        return projectsRepository.unbookmarkProjects(params.projectId)
    }

    data class Params(val projectId: String) {
        fun forProject(projectId: String) :Params {
            return Params(projectId)
        }
    }

}