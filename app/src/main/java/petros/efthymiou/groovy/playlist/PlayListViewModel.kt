package petros.efthymiou.groovy.playlist


import androidx.lifecycle.*
import kotlinx.coroutines.flow.onEach
import petros.efthymiou.groovy.playlist.models.Playlist
import petros.efthymiou.groovy.playlist.models.PlaylistDetails
import petros.efthymiou.groovy.playlist.repository.PlayListRepository
import petros.efthymiou.groovy.playlist.repository.PlaylistDetailsRepository
import javax.inject.Inject


class PlayListViewModel @Inject constructor(
    private val repository: PlayListRepository
) : ViewModel() {

    val loader=MutableLiveData<Boolean>()

    val playlists = liveData<Result<List<Playlist>>> {
        loader.postValue(true)
        emitSource(repository.getPlaylist()
            .onEach {
                loader.postValue(false)
            }
            .asLiveData())

    }

    fun getPlaylistDetails(repositoryDetails:PlaylistDetailsRepository,id:String)= liveData<Result<PlaylistDetails>> {
        emitSource(repositoryDetails.getPlaylistDetails(id).asLiveData())
    }

}
