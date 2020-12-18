package petros.efthymiou.groovy.network

import petros.efthymiou.groovy.playlist.models.PlayListDetailsRaw
import petros.efthymiou.groovy.playlist.models.PlayListRaw
import retrofit2.http.GET
import retrofit2.http.Url

interface PlaylistApi {

  @GET("playlist")
  suspend  fun fetchAllplaylist() :List<PlayListRaw>

  @GET
  suspend fun fetchDetails(@Url url: String): PlayListDetailsRaw


}