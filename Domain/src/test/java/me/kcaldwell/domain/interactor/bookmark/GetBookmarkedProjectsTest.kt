package me.kcaldwell.domain.interactor.bookmark

import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import me.kcaldwell.domain.executor.PostExecutionThread
import me.kcaldwell.domain.interactor.bookmarked.GetBookmarkedProjects
import me.kcaldwell.domain.model.GithubProject
import me.kcaldwell.domain.repository.ProjectsRepository
import me.kcaldwell.domain.test.ProjectDataFactory
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetBookmarkedProjectsTest {

    private lateinit var getBookmarkedProjects: GetBookmarkedProjects
    @Mock lateinit var projectsRepository: ProjectsRepository
    @Mock lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getBookmarkedProjects = GetBookmarkedProjects(projectsRepository, postExecutionThread)
    }

    @Test
    fun getBookmarkedProjectsCompletes() {
        stubGetProjects(Observable.just(ProjectDataFactory.makeProjectList(2)))
        val testObserver = getBookmarkedProjects.buildUseCaseObservable().test()
        testObserver.assertComplete()
    }

    @Test
    fun getBookmarkedProjectsReturnsData() {
        val projects = ProjectDataFactory.makeProjectList(2)
        stubGetProjects(Observable.just(projects))
        val testObserver = getBookmarkedProjects.buildUseCaseObservable().test()
        testObserver.assertValue(projects)
    }

    private fun stubGetProjects(observable: Observable<List<GithubProject>>) {
        whenever(projectsRepository.getBookmarkedProjects())
            .thenReturn(observable)
    }

}