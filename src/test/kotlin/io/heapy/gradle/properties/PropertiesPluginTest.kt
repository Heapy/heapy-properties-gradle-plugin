package io.heapy.gradle.properties

import org.gradle.kotlin.dsl.getByName
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Path

class PropertiesPluginTest {
    @Test
    fun `test apply plugin`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply(PropertiesPlugin.PLUGIN_ID)

        assertNotNull(project.plugins.getPlugin(PropertiesPlugin::class.java))
    }

    @Test
    fun `test registering extension`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply(PropertiesPlugin.PLUGIN_ID)

        assertNotNull(project.extensions.getByName<PropertiesPluginExtension>("props"))
    }

    @Test
    fun `simple project build`() {
        val projectDir = Files.createTempDirectory("")
        projectDir.addFile("build.gradle.kts") {
            """
                plugins {
                    id("io.heapy.gradle.properties")
                }
                
                tasks.create("validate") {
                    doLast {
                        print("Version is ${'$'}version")
                        check(version == "1.2.3") { "Version is not 1.2.3" }
                    }
                }   
            """.trimIndent()
        }

        projectDir.addFile("build.properties") {
            """
                version=1.2.3
            """.trimIndent()
        }

        val result = GradleRunner.create()
            .withProjectDir(projectDir.toFile())
            .withPluginClasspath()
            .withArguments("validate")
            .build()

        assertEquals(TaskOutcome.SUCCESS, result.task(":validate")?.outcome)
        assertTrue(result.output.contains("Version is 1.2.3"))
    }
}

internal fun Path.addFile(name: String, body: () -> String) {
    val newFile = resolve(name).toFile()
    newFile.writeText(body())
}
