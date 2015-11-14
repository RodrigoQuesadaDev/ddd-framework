package com.rodrigodev.common.spec.story

import com.rodrigodev.common.spec.story.converter.*
import com.rodrigodev.common.spec.story.transformer.MultilineTableTransformer
import org.jbehave.core.configuration.MostUsefulConfiguration
import org.jbehave.core.io.LoadFromClasspath
import org.jbehave.core.junit.JUnitStory
import org.jbehave.core.model.ExamplesTableFactory
import org.jbehave.core.model.TableTransformers
import org.jbehave.core.parsers.RegexStoryParser
import org.jbehave.core.reporters.Format
import org.jbehave.core.reporters.StoryReporterBuilder
import org.jbehave.core.steps.InstanceStepsFactory
import org.jbehave.core.steps.ParameterControls
import org.jbehave.core.steps.ParameterConverters
import org.jbehave.core.steps.ParameterConverters.ExamplesTableConverter
import org.jbehave.core.steps.ParameterConverters.ParameterConverter
import org.junit.Before

/**
 * Created by Rodrigo Quesada on 26/10/15.
 */
abstract class SpecStory : JUnitStory() {

    protected open val steps: Array<*> = arrayOf<Any>()

    private val tableTransformers = TableTransformers().apply {
        useTransformer(MultilineTableTransformer.NAME, MultilineTableTransformer())
    }
    private val tableFactory = ExamplesTableFactory(tableTransformers)

    protected val parameterConverters = ParameterConverters()
    private val baseCustomConverters = arrayOf(
            ExamplesTableConverter(tableFactory),
            JsonConverter(),
            ListConverter(parameterConverters),
            LocalDateConverter(),
            CustomNumberListConverter(),
            CustomNumberConverter()
    );
    protected open val storyConverters: Array<ParameterConverter> = emptyArray()

    private val converters: List<ParameterConverter> by lazy {
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
            .useParameterConverters(parameterConverters.addConverters(converters))
            .useParameterControls(ParameterControls().useDelimiterNamedParameters(true))
            .useStoryParser(RegexStoryParser(tableFactory))
            .useStoryLoader(LoadFromClasspath(this.javaClass))
            .useStoryReporterBuilder(
                    StoryReporterBuilder()
                            .withDefaultFormats()
                            .withFormats(Format.CONSOLE, Format.HTML_TEMPLATE)
                            .withFailureTrace(true)
                            .withRelativeDirectory("../build/jbehave"))
}