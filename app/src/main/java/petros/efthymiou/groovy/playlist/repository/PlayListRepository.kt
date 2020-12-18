package petros.efthymiou.groovy.playlist.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import petros.efthymiou.groovy.playlist.service.PlaylistService
import petros.efthymiou.groovy.playlist.mapper.PlayListMapper
import petros.efthymiou.groovy.playlist.models.Playlist
import javax.inject.Inject


class PlayListRepository @Inject constructor(
    private val service: PlaylistService,
    private val mapper: PlayListMapper
) {
    suspend fun getPlaylist(): Flow<Result<List<Playlist>>> =
        service.fetchPlaylist().map {
            if (it.isSuccess)
            Result.success(mapper(it.getOrNull()!!))
           else
             Result.failure(it.exceptionOrNull()!!)
        }


}
