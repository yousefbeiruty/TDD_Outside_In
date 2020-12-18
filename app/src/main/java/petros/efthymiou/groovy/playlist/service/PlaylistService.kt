package petros.efthymiou.groovy.playlist.service

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import petros.efthymiou.groovy.network.PlaylistApi
import petros.efthymiou.groovy.playlist.models.PlayListDetailsRaw
import petros.efthymiou.groovy.playlist.models.PlayListRaw
import petros.efthymiou.groovy.utils.Constants
import java.lang.RuntimeException
import javax.inject.Inject

private const val TAG = "PlaylistService"
class PlaylistService @Inject constructor(private  val api:PlaylistApi) {
  suspend fun fetchPlaylist() : Flow<Result<List<PlayListRaw>>> {
      return flow { emit(Result.success(api.fetchAllplaylist()))}
              .catch {emit(Result.failure(RuntimeException("Something went wrong")))}
    }

    suspend fun fetchPlaylistDetails( id:String):Flow<Result<PlayListDetailsRaw>>{
        var url:String=Constants.base_usrl+"playlist-details/"+id
        Log.d(TAG, "fetchPlaylistDetails: url= "+url)
        return flow { emit(Result.success(api.fetchDetails(url))) }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }

}
