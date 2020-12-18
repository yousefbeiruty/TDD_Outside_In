package petros.efthymiou.groovy.playlist.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import petros.efthymiou.groovy.playlist.mapper.PlaylistDetailsMapper
import petros.efthymiou.groovy.playlist.service.PlaylistService
import petros.efthymiou.groovy.playlist.models.Playlist
import petros.efthymiou.groovy.playlist.models.PlaylistDetails
import javax.inject.Inject

class PlaylistDetailsRepository @Inject constructor(
 private val  service: PlaylistService,
 private val mapper:PlaylistDetailsMapper
) {
   suspend fun getPlaylistDetails(id:String): Flow<Result<PlaylistDetails>> =
    service.fetchPlaylistDetails(id).map {
        if (it.isSuccess)
            Result.success(mapper(it.getOrNull()!!))
        else
            Result.failure(it.exceptionOrNull()!!)
    }


}