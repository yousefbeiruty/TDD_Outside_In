package petros.efthymiou.groovy.playlist

import android.os.Bundle
import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_playlist.*
import petros.efthymiou.groovy.R
import petros.efthymiou.groovy.playlist.adapter.MyPlaylistRecyclerViewAdapter
import petros.efthymiou.groovy.playlist.models.Playlist
import javax.inject.Inject

@AndroidEntryPoint
class PlayListFragment : Fragment() {


    lateinit var viewModel: PlayListViewModel

    @Inject
    lateinit var viewModelFactory: PlayListViewModelFactory



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlist, container, false)
        setUpViewModel()

        viewModel.loader.observe(this as LifecycleOwner,{loading->
            when(loading){
                true->loaders.visibility=View.VISIBLE
                else->loaders.visibility=View.GONE
            }

        })

        viewModel.playlists.observe(this as LifecycleOwner,{playlist->
            if(playlist.getOrNull()!=null)
            setUpList(playlist.getOrNull()!!)
            else{
               //TODO
            }
        })

        return view
    }

    private fun setUpList(

        playlist: List<Playlist>
    ) {
        with(playlists_list as RecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyPlaylistRecyclerViewAdapter(playlist) { id ->
                val action =
                    PlayListFragmentDirections.actionPlayListFragmentToPlayListDetailsFragment(id)

                findNavController().navigate(action)
            }
        }
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlayListViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PlayListFragment().apply {

            }
    }
}