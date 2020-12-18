package petros.efthymiou.groovy.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import petros.efthymiou.groovy.playlist.repository.PlayListRepository
import javax.inject.Inject

class PlayListViewModelFactory @Inject constructor(
    private val repository: PlayListRepository
):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlayListViewModel(repository) as T
    }

}
