package petros.efthymiou.groovy.playlist

import junit.framework.Assert.assertEquals
import org.junit.Test
import petros.efthymiou.groovy.R
import petros.efthymiou.groovy.playlist.mapper.PlayListMapper
import petros.efthymiou.groovy.playlist.models.PlayListRaw
import petros.efthymiou.groovy.utils.BaseUnitTest

class PlaylistMapperShould :BaseUnitTest() {

    private val playlistRaw= PlayListRaw("1","name","category")
    private val playlisRowtRock= PlayListRaw("1","name","rock")

    private val mapper= PlayListMapper()

    private val playlists=mapper(listOf(playlistRaw))

    private val playlist=playlists[0]

    private val playlistRock=mapper(listOf(playlisRowtRock))[0]

    @Test
    fun keepSameId(){
         assertEquals(playlistRaw.id,playlist.id)
    }

    @Test
    fun keepSameName(){
        assertEquals(playlistRaw.name,playlist.name)
    }

    @Test
    fun keepSameCategory(){
        assertEquals(playlistRaw.category,playlist.category)
    }

    @Test
    fun mapDefaultImageWhenItsNotRock(){
        assertEquals(R.mipmap.playlist,playlist.image)
    }

    @Test
    fun  mapRockImageWhenTheCategoryIsRock(){
        assertEquals(R.mipmap.rock,playlistRock.image)
    }
}