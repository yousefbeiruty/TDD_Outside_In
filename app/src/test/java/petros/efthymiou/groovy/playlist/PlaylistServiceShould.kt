package petros.efthymiou.groovy.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import petros.efthymiou.groovy.network.PlaylistApi
import petros.efthymiou.groovy.playlist.models.PlayListRaw
import petros.efthymiou.groovy.playlist.service.PlaylistService
import petros.efthymiou.groovy.utils.BaseUnitTest
import java.lang.RuntimeException

class PlaylistServiceShould : BaseUnitTest() {

    private lateinit var service: PlaylistService

    private val api: PlaylistApi = mock()

    private val playlists: List<PlayListRaw> = mock()

    @Test
    fun fetchPlaylistFromApi() = runBlockingTest {
        service = PlaylistService(api)
        service.fetchPlaylist().first()

        verify(api, times(1)).fetchAllplaylist()
    }

    @Test
    fun convertValueTFlowResultAndEmitThem() = runBlockingTest {
        mockSuccessCase()

        assertEquals(Result.success(playlists), service.fetchPlaylist().first())
    }


    private suspend fun mockFailureCase() {
        whenever(api.fetchAllplaylist()).thenThrow(RuntimeException("Damn backend developers"))

        service = PlaylistService(api)
    }

    private suspend fun mockSuccessCase() {
        whenever(api.fetchAllplaylist()).thenReturn(playlists)

        service = PlaylistService(api)
    }

    @Test
    fun emitErrorResultWhenEmitFails() = runBlockingTest {
        mockFailureCase()

        assertEquals("Something went wrong", service.fetchPlaylist()
                .first().exceptionOrNull()?.message)
    }
}