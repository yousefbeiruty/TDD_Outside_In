package petros.efthymiou.groovy.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import petros.efthymiou.groovy.playlist.mapper.PlayListMapper
import petros.efthymiou.groovy.playlist.models.PlayListRaw
import petros.efthymiou.groovy.playlist.models.Playlist
import petros.efthymiou.groovy.playlist.repository.PlayListRepository
import petros.efthymiou.groovy.playlist.service.PlaylistService
import petros.efthymiou.groovy.utils.BaseUnitTest
import java.lang.RuntimeException

class PlaylistRepositoryShould:BaseUnitTest() {

    private val service: PlaylistService = mock()
    private val playlists= mock<List<Playlist>>()
    private val playListRaw= mock<List<PlayListRaw>>()
    private val mapper: PlayListMapper =mock()
    private val exception=RuntimeException("Something went wrong")

    @Test
    fun getPlaylistFromService()= runBlockingTest{
       val repository=mockSuccessfullCase()

        repository.getPlaylist()

        verify(service, times(1)).fetchPlaylist()
    }

    @Test
    fun emitPlaylistFromService()= runBlockingTest{
      val repository = mockSuccessfullCase()

        assertEquals(playlists,repository.getPlaylist().first().getOrNull())
    }

    @Test
    fun propagateErrors()= runBlockingTest{
        val repository = mockFailureCase()
        assertEquals(exception,repository.getPlaylist().first().exceptionOrNull())
    }

    @Test
    fun delegateBusinessLogicToMapper()= runBlockingTest{
        val repository=mockSuccessfullCase()

        repository.getPlaylist().first()

        verify(mapper, times(1)).invoke(playListRaw)
    }

    private suspend fun mockFailureCase(): PlayListRepository {
        whenever(service.fetchPlaylist()).thenReturn(
                flow {
                    emit(Result.failure<List<PlayListRaw>>(exception))
                }
        )

        return PlayListRepository(service, mapper)
    }

    private suspend fun mockSuccessfullCase(): PlayListRepository {
        whenever(service.fetchPlaylist()).thenReturn(
                flow {
                    emit(Result.success(playListRaw))
                }
        )
        whenever(mapper.invoke(playListRaw)).thenReturn(playlists)
        return PlayListRepository(service, mapper)
    }
}