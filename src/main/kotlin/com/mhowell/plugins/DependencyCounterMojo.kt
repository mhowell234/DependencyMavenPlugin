package com.mhowell.plugins

import org.apache.maven.model.Dependency
import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugin.MojoExecutionException
import org.apache.maven.plugin.MojoFailureException
import org.apache.maven.plugins.annotations.LifecyclePhase
import org.apache.maven.plugins.annotations.Mojo
import org.apache.maven.plugins.annotations.Parameter
import org.apache.maven.project.MavenProject

@Mojo(name = "dependency-counter", defaultPhase = LifecyclePhase.COMPILE)
class DependencyCounterMojo : AbstractMojo() {

    @Parameter(defaultValue = "\${project}", required = true, readonly = true)
    lateinit var project: MavenProject

    @Parameter(property = "scope")
    var scope: String? = null

    @Throws(MojoExecutionException::class, MojoFailureException::class)
    override fun execute() {
        val dependencies: List<Dependency> = project.dependencies.filterIsInstance<Dependency>()

        val matchingDependencies = dependencies.filter { scope.isNullOrBlank() || scope == it.scope }

        log.info("Number of dependencies: ${matchingDependencies.size}")
        matchingDependencies.forEach { log.info("${it.groupId}:${it.artifactId}:${it.version} - ${it.scope}") }
    }
}