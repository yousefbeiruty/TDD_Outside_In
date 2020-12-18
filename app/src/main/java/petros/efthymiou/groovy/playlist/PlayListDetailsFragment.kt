package petros.efthymiou.groovy.playlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_play_list_details.*
import petros.efthymiou.groovy.R
import petros.efthymiou.groovy.playlist.repository.PlaylistDetailsRepository
import javax.inject.Inject


private const val TAG = "PlayListDetailsFragment"
@AndroidEntryPoint
class PlayListDetailsFragment : Fragment() {

    val args:PlayListDetailsFragmentArgs by navArgs()

    lateinit var viewModel: PlayListViewModel

    @Inject
    lateinit var viewModelFactory: PlayListViewModelFactory

    @Inject
    lateinit var repositoryDetails: PlaylistDetailsRepository

    private lateinit var id: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_play_list_details, container, false)
        setUpViewModel()

        id = args.playlistId
        viewModel.getPlaylistDetails(repositoryDetails, id).observe(this as LifecycleOwner,
            { playlistDetails ->
                if (playlistDetails.getOrNull() != null) {
                    Log.d(TAG, "onCreateView: Success")
                    detail_name.text = playlistDetails.getOrNull()!!.name
                    details.text = playlistDetails.getOrNull()!!.details
                } else {
                    //TODO
                    Log.d(TAG, "onCreateView: Failer= "+playlistDetails.exceptionOrNull()?.message)
                }
            })

        return view
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlayListViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlayListDetailsFragment()
    }
}