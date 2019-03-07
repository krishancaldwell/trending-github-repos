package me.kcaldwell.domain.repository

import io.reactivex.Completable
import io.reactivex.Observable
import me.kcaldwell.domain.model.GithubProject
import java.util.*

/**
 * Interface to define what needs to be implemented in order to obtain and
 * manipulate data model.
 */
interface ProjectsRepository {

    fun getProjects(): Observable<List<GithubProject>>

    fun bookmarkProject(projectId: String): Completable

    fun unbookmarkProjects(projectId: String): Completable

    fun getBookmarkedProjects(): Observable<List<GithubProject>>

}