package me.kcaldwell.domain.interactor.bookmark

import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import me.kcaldwell.domain.executor.PostExecutionThread
import me.kcaldwell.domain.interactor.bookmarked.UnbookmarkProject
import me.kcaldwell.domain.repository.ProjectsRepository
import me.kcaldwell.domain.test.ProjectDataFactory
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class UnbookmarkProjectTest {

    private lateinit var unbookmarkProject: UnbookmarkProject
    @Mock lateinit var projectsRepository: ProjectsRepository
    @Mock lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        unbookmarkProject = UnbookmarkProject(projectsRepository, postExecutionThread)
    }

    @Test
    fun bookmarkProjectCompletes() {
        // Mock the function
        stubunbookmarkProject(Completable.complete())

        // Build the use case completable function
        val testObserver = unbookmarkProject.buildUseCaseCompletable(
            UnbookmarkProject.Params.forProject(ProjectDataFactory.randomUuid())).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun unbookmarkProjectThrowsException() {
        unbookmarkProject.buildUseCaseCompletable().test()
    }

    /**
     * Since above test interacts with bookmark repository, override to always
     * return the completable passed into this function whenever the
     * bookmarkProject function is invoked on the projectsRepository using this
     * function.
     */
    private fun stubunbookmarkProject(completable: Completable) {
        whenever(projectsRepository.bookmarkProject(anyOrNull()))
            .thenReturn(completable)
    }

}