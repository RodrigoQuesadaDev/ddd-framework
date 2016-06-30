@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.spec.story

import com.rodrigodev.common.spec.story.converter.*
import com.rodrigodev.common.spec.story.steps.SpecSteps
import com.rodrigodev.common.spec.story.transformer.MultilineTableTransformer
import com.thoughtworks.paranamer.BytecodeReadingParanamer
import com.thoughtworks.paranamer.CachingParanamer
import org.jbehave.core.configuration.MostUsefulConfiguration
import org.jbehave.core.io.LoadFromClasspath
import org.jbehave.core.junit.JUnitStories
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
abstract class SpecStory : JUnitStories() {

    private val setUpClosures: MutableList<() -> Unit> = mutableListOf()

    private val preStories: MutableList<String> = mutableListOf()
    private val postStories: MutableList<String> = mutableListOf()

    private val steps: MutableList<Any> = mutableListOf()
    private val stepsClosures: MutableList<() -> List<Any>> = mutableListOf()

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
            DurationConverter(),
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
    fun setUpAll() {
        setUpClosures.forEach { it() }
        configureSteps()
        configuredEmbedder().embedderControls().doIgnoreFailureInStories(true)
    }

    fun setUp(closure: () -> Unit) {
        setUpClosures.add(closure)
    }

    protected fun preStories(vararg paths: String) {
        preStories.addAll(paths)
    }

    protected fun postStories(vararg paths: String) {
        postStories.addAll(paths)
    }

    protected fun steps(closure: () -> List<Any>) {
        stepsClosures.add(closure)
    }

    override fun stepsFactory() = InstanceStepsFactory(configuration(), steps)

    override fun configuration() = MostUsefulConfiguration()
            .useParameterConverters(parameterConverters.addConverters(converters))
            .useParameterControls(ParameterControls().useDelimiterNamedParameters(true))
            .useParanamer(CachingParanamer(BytecodeReadingParanamer()))
            .useStoryParser(RegexStoryParser(tableFactory))
            .useStoryLoader(LoadFromClasspath(this.javaClass))
            .useStoryReporterBuilder(
                    StoryReporterBuilder()
                            .withDefaultFormats()
                            .withFormats(Format.CONSOLE, Format.HTML_TEMPLATE)
                            .withFailureTrace(true)
                            .withRelativeDirectory("../build/jbehave"))

    //region Stories Configuration
    override fun storyPaths(): List<String> {
        val pathResolver = configuredEmbedder().configuration().storyPathResolver()
        return preStories + listOf(pathResolver.resolve(this.javaClass)) + postStories
    }
    //endregion

    //region Steps Configuration
    private inline fun configureSteps() {
        steps.addAll(stepsClosures.toStepsList())
    }

    private inline fun List<() -> List<Any>>.toStepsList(): Collection<Any> = flatMap { it() }
    //endregion
}
