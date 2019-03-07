package me.kcaldwell.domain.test

import me.kcaldwell.domain.model.GithubProject
import java.util.*

object ProjectDataFactory {

    fun randomUuid(): String {
        return UUID.randomUUID().toString()
    }

    fun randomBoolean(): Boolean {
        return Math.random() < 0.5
    }

    fun createRandomProject(): GithubProject {
        return GithubProject(randomUuid(), randomUuid(), randomUuid(),
            randomUuid(), randomUuid(), randomUuid(), randomUuid(),
            randomBoolean())
    }

    fun makeProjectList(count: Int): List<GithubProject> {
        val projects = mutableListOf<GithubProject>()

        repeat(count) {
            projects.add(createRandomProject())
        }

        return projects
    }

}