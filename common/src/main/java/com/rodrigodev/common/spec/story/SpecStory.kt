package com.rodrigodev.common.spec.story

import com.rodrigodev.common.spec.story.converter.LocalDateConverter
import org.jbehave.core.configuration.MostUsefulConfiguration
import org.jbehave.core.io.LoadFromClasspath
import org.jbehave.core.junit.JUnitStory
import org.jbehave.core.reporters.Format
import org.jbehave.core.reporters.StoryReporterBuilder
import org.jbehave.core.steps.InstanceStepsFactory
import org.jbehave.core.steps.ParameterControls
import org.jbehave.core.steps.ParameterConverters
import org.junit.Before

/**
 * Created by rodrigo on 26/10/15.
 */
abstract class SpecStory : JUnitStory() {

    protected open val steps: Array<*> = arrayOf<Any>()

    private val baseCustomConverters = arrayOf(LocalDateConverter());
    protected open val storyConverters: Array<ParameterConverters.ParameterConverter> = emptyArray()

    private val converters: List<ParameterConverters.ParameterConverter> by lazy {
        steps.asSequence()
                .map { it as? SpecSteps }.filterNotNull()
                .map { it.converters }
                .flatMap { it.asSequence() }
                .plus(storyConverters)
                .plus(baseCustomConverters)
                .distinctBy { it.javaClass }
                .toList()
    }

    @Before
    open fun setUp() {
        configuredEmbedder().embedderControls().doIgnoreFailureInStories(true)
    }

    override fun stepsFactory() = InstanceStepsFactory(configuration(), *steps)

    override fun configuration() = MostUsefulConfiguration()
            .useParameterConverters(ParameterConverters().addConverters(converters))
            .useParameterControls(ParameterControls().useDelimiterNamedParameters(true))
            .useStoryLoader(LoadFromClasspath(this.javaClass))
            .useStoryReporterBuilder(
                    StoryReporterBuilder()
                            .withDefaultFormats()
                            .withFormats(Format.CONSOLE, Format.HTML_TEMPLATE)
                            .withFailureTrace(true)
                            .withRelativeDirectory("../build/jbehave"))
}