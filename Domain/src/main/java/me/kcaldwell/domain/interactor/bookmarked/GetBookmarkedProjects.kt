package me.kcaldwell.domain.interactor.bookmarked

import io.reactivex.Observable
import me.kcaldwell.domain.executor.PostExecutionThread
import me.kcaldwell.domain.interactor.ObservableUseCase
import me.kcaldwell.domain.model.GithubProject
import me.kcaldwell.domain.repository.ProjectsRepository
import javax.inject.Inject

open class GetBookmarkedProjects @Inject constructor(
    private val projectsRepository: ProjectsRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<GithubProject>, Nothing?>(postExecutionThread) {

    public override fun buildUseCaseObservable(params: Nothing?): Observable<List<GithubProject>> {
        return projectsRepository.getBookmarkedProjects()
    }
}