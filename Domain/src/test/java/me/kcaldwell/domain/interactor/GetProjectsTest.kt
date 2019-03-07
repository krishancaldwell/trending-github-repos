package me.kcaldwell.domain.interactor

import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import me.kcaldwell.domain.executor.PostExecutionThread
import me.kcaldwell.domain.interactor.browse.GetProjects
import me.kcaldwell.domain.model.GithubProject
import me.kcaldwell.domain.repository.ProjectsRepository
import me.kcaldwell.domain.test.ProjectDataFactory
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetProjectsTest {

    private lateinit var getProjects: GetProjects
    @Mock lateinit var projectsRepository: ProjectsRepository
    @Mock lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getProjects = GetProjects(projectsRepository, postExecutionThread)
    }

    @Test
    fun getProjectsCompletes() {
        stubGetProjects(Observable.just(ProjectDataFactory.makeProjectList(2)))
        val testObserver = getProjects.buildUseCaseObservable().test()
        testObserver.assertComplete()
    }

    @Test
    fun getProjectsReturnsData() {
        val projects = ProjectDataFactory.makeProjectList(2)
        stubGetProjects(Observable.just(projects))
        val testObserver = getProjects.buildUseCaseObservable().test()
        testObserver.assertValue(projects)
    }

    /**
     * Mocks get projects call
     * Whenever getProjects() is called, return observable
     */
    private fun stubGetProjects(observable: Observable<List<GithubProject>>) {
        whenever(projectsRepository.getProjects())
            .thenReturn(observable)
    }

}