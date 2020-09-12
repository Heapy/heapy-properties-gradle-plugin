package io.heapy.gradle.properties

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.extra
import java.io.File
import java.util.Properties

class PropertiesPluginExtension {
    val versionFile: File? = null
}

class PropertiesPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create<PropertiesPluginExtension>("props")

        setVersion(project, extension)
        setProperties(project)
    }

    internal fun setProperties(project: Project) {
        projectList(project).reversed().forEach {
            val file = it.file("local.properties")
            if (file.exists()) {
                val props = file.loadToProps()
                props.stringPropertyNames().forEach { name ->
                    project.extra[name] = props.getProperty(name)
                }
            }
        }
    }

    internal fun projectList(project: Project): List<Project> {
        val projects = mutableListOf<Project>()
        var parent: Project? = project

        while (parent != null) {
            projects.add(parent)
            parent = parent.parent
        }

        return projects.toList()
    }

    internal fun setVersion(
        project: Project,
        extension: PropertiesPluginExtension
    ) {
        project.afterEvaluate {
            val propsFile = extension.versionFile
                ?: rootProject.rootDir.resolve("build.properties")

            version = propsFile.loadToProps().getProperty("version")
        }
    }

    internal fun File.loadToProps(
        properties: Properties = Properties()
    ): Properties {
        this.reader().use(properties::load)
        return properties
    }
}
