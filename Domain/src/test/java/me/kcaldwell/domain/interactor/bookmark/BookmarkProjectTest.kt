package me.kcaldwell.domain.interactor.bookmark

import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import me.kcaldwell.domain.executor.PostExecutionThread
import me.kcaldwell.domain.interactor.bookmarked.BookmarkProject
import me.kcaldwell.domain.repository.ProjectsRepository
import me.kcaldwell.domain.test.ProjectDataFactory
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.lang.IllegalArgumentException

class BookmarkProjectTest {

    private lateinit var bookmarkProject: BookmarkProject
    @Mock lateinit var projectsRepository: ProjectsRepository
    @Mock lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        bookmarkProject = BookmarkProject(projectsRepository, postExecutionThread)
    }

    @Test
    fun bookmarkProjectCompletes() {
        // Mock the function
        stubBookmarkProject(Completable.complete())

        // Build the use case completable function
        val testObserver = bookmarkProject.buildUseCaseCompletable(
            BookmarkProject.Params.forProject(ProjectDataFactory.randomUuid())).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun bookmarkProjectThrowsException() {
        bookmarkProject.buildUseCaseCompletable().test()
    }

    /**
     * Since above test interacts with bookmark repository, override to always
     * return the completable passed into this function whenever the
     * bookmarkProject function is invoked on the projectsRepository using this
     * function.
     */
    private fun stubBookmarkProject(completable: Completable) {
        whenever(projectsRepository.bookmarkProject(anyOrNull()))
            .thenReturn(completable)
    }

}