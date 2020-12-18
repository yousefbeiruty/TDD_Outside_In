package petros.efthymiou.groovy.playlist.mapper

import petros.efthymiou.groovy.R
import petros.efthymiou.groovy.playlist.models.PlayListDetailsRaw
import petros.efthymiou.groovy.playlist.models.PlayListRaw
import petros.efthymiou.groovy.playlist.models.Playlist
import petros.efthymiou.groovy.playlist.models.PlaylistDetails
import javax.inject.Inject

class PlaylistDetailsMapper @Inject constructor() : Function1<PlayListDetailsRaw,PlaylistDetails>  {

    override fun invoke(playlistDetailsRaw:PlayListDetailsRaw): PlaylistDetails {
        return PlaylistDetails(playlistDetailsRaw.id,playlistDetailsRaw.name,playlistDetailsRaw.details)
    }
}