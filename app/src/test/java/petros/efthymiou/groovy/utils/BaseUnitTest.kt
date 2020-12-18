package petros.efthymiou.groovy.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

open class BaseUnitTest {
    @get:Rule
    val coroutineTestRule=MainCoroutineScopeRule()

    @get:Rule
    val instantTaskExecutorRule= InstantTaskExecutorRule()


}