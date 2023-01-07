package com.mhowell.plugins;

import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.List;

@Mojo(name = "dependency-counter-java", defaultPhase = LifecyclePhase.COMPILE)
public class DependencyCounterMojoJava extends AbstractMojo {
	@Parameter(defaultValue = "${project}", required = true, readonly = true)
	MavenProject project;

	@Parameter(property = "scope")
	String scope;

	public void execute() throws MojoExecutionException, MojoFailureException {
		List<Dependency> dependencies = project.getDependencies();
		var matchingDependencies =
				dependencies.stream().filter(d -> null == scope || scope.isEmpty() || scope.equals(d.getScope())).toList();

		getLog().info("Number of dependencies: " + matchingDependencies.size());
		matchingDependencies.forEach(d -> getLog().info(d.getGroupId() + ":" + " " + d.getArtifactId() + ":" + d.getVersion() + " - " + d.getScope()));
	}
}
