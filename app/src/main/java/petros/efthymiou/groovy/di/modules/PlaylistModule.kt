package petros.efthymiou.groovy.di.modules

import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import okhttp3.OkHttpClient
import petros.efthymiou.groovy.network.PlaylistApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val client=OkHttpClient()
val idlingResource=OkHttp3IdlingResource.create("okhttp", client)

@Module
@InstallIn(FragmentComponent::class)
class PlaylistModule {

    @Provides
    fun playListApi(retrofit: Retrofit):PlaylistApi=retrofit.create(PlaylistApi::class.java)

    @Provides
    fun retrofit():Retrofit=Retrofit.Builder()
            .baseUrl("http://192.168.1.16:3000/")//please check ip address of your computer
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}