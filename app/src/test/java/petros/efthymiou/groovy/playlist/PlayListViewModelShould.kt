package petros.efthymiou.groovy.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

import org.junit.Assert.*
import petros.efthymiou.groovy.playlist.models.Playlist
import petros.efthymiou.groovy.playlist.models.PlaylistDetails
import petros.efthymiou.groovy.playlist.repository.PlayListRepository
import petros.efthymiou.groovy.playlist.repository.PlaylistDetailsRepository
import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.utils.captureValues
import petros.efthymiou.groovy.utils.getValueForTest
import java.lang.RuntimeException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PlayListViewModelShould : BaseUnitTest (){

    private val repository: PlayListRepository = mock()

    private val repositoryDetails: PlaylistDetailsRepository = mock()

    private val playList=mock<List<Playlist>>()

    private val playlistDetails= mock<PlaylistDetails>()

    private val expected=Result.success(playList)

    private val expectedDetails=Result.success(playlistDetails)

    private val exception=RuntimeException("Something went wrong")

    @Test
    fun getPlayListFromRepository()= runBlockingTest {
        val viewModel = mockSuccessfullCase()
        viewModel.playlists.getValueForTest()

        verify(repository, times(1)).getPlaylist()
    }

    @Test
    fun getDetailsPlayListFromRepository()= runBlockingTest{
       val viewModel=mockSucssesPlayListDetails()
        viewModel.getPlaylistDetails(repositoryDetails,"1").getValueForTest()

        verify(repositoryDetails, times(1)).getPlaylistDetails("1")
    }

    @Test
    fun emitErrorWhenReceiveError(){
        val viewModel = mockErrorCase()

        assertEquals(exception,viewModel.playlists.getValueForTest()!!.exceptionOrNull())
    }



    @Test
    fun emitsPlaylistFromRepository()= runBlockingTest{
        val viewModel = mockSuccessfullCase()
        assertEquals(expected,viewModel.playlists.getValueForTest())
    }

    @Test
    fun showSpinnerWhileLoading()= runBlockingTest{
        val viewModel:PlayListViewModel= mockSuccessfullCase()
        viewModel.loader.captureValues{
            viewModel.playlists.getValueForTest()

            assertEquals(true,values[0])
        }
    }

    @Test
    fun closeLoaderAfterPlayListLoad()= runBlockingTest{
       val viewModel:PlayListViewModel=mockSuccessfullCase()

        viewModel.loader.captureValues {
            viewModel.playlists.getValueForTest()
            assertEquals(false,values.last())
        }
    }
    @Test
    fun closeLoaderAfterErrorCase()= runBlockingTest{
        val viewModel:PlayListViewModel=mockErrorCase()

        viewModel.loader.captureValues {
            viewModel.playlists.getValueForTest()
            assertEquals(false,values.last())
        }
    }
    private fun mockSucssesPlayListDetails():PlayListViewModel{
        runBlocking {
            whenever(repositoryDetails.getPlaylistDetails("1")).thenReturn(
                flow {
                    emit(expectedDetails)
                }
            )
        }
        val playlistViewModel=PlayListViewModel(repository)
        playlistViewModel.getPlaylistDetails(repositoryDetails,"1")
        return playlistViewModel
    }
    private fun mockSuccessfullCase(): PlayListViewModel {
        runBlocking {
            whenever(repository.getPlaylist()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }

        return PlayListViewModel(repository)
    }

    private fun mockErrorCase(): PlayListViewModel {
        runBlocking {
            whenever(repository.getPlaylist()).thenReturn(
                flow {
                    emit(Result.failure<List<Playlist>>(exception))
                }
            )
        }

        return PlayListViewModel(repository)
    }
}