package petros.efthymiou.groovy.playlist.mapper

import petros.efthymiou.groovy.R
import petros.efthymiou.groovy.playlist.models.PlayListRaw
import petros.efthymiou.groovy.playlist.models.Playlist
import javax.inject.Inject

class PlayListMapper @Inject constructor() : Function1<List<PlayListRaw>,List<Playlist>>{
    override fun invoke(playlistRaw: List<PlayListRaw>): List<Playlist> {
     return playlistRaw.map {
         val image=when(it.category){
             "rock"->R.mipmap.rock
             else->R.mipmap.playlist
         }
             Playlist(it.id,it.name,it.category, image)

     }
    }

}
