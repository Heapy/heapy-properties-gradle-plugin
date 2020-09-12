package io.heapy.gradle.properties

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.getByName
import java.io.File
import java.util.Properties

open class PropertiesPluginExtension {
    var versionFile: File? = null
}

class PropertiesPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        setVersion(project)
        setProperties(project)
    }

    internal fun setProperties(project: Project) {
        parentProjects(project).reversed().forEach {
            val file = it.file("local.properties")
            if (file.exists()) {
                val props = file.loadToProps()
                props.stringPropertyNames().forEach { name ->
                    project.extra[name] = props.getProperty(name)
                }
            }
        }
    }

    internal fun parentProjects(project: Project): List<Project> {
        val projects = mutableListOf<Project>()
        var parent: Project? = project

        while (parent != null) {
            projects.add(parent)
            parent = parent.parent
        }

        return projects.toList()
    }

    internal fun setVersion(project: Project) {
        project.extensions.create<PropertiesPluginExtension>("props")

        project.afterEvaluate {
            val versionFile = parentProjects(project)
                .filter { it.pluginManager.hasPlugin(PLUGIN_ID) }
                .map { it.extensions.getByName<PropertiesPluginExtension>("props") }
                .mapNotNull { it.versionFile }
                .firstOrNull()

            val propsFile = versionFile
                ?: project.rootProject.rootDir.resolve("build.properties")

            if (propsFile.exists()) {
                val buildVersion = propsFile.loadToProps().getProperty("version")
                version = buildVersion
                subprojects {
                    version = buildVersion
                }
            } else {
                logger.error("Version file ($propsFile) is not found")
            }
        }
    }

    internal fun File.loadToProps(
        properties: Properties = Properties()
    ): Properties {
        this.reader().use(properties::load)
        return properties
    }

    companion object {
        private const val PLUGIN_ID = "io.heapy.gradle.properties"
    }
}
