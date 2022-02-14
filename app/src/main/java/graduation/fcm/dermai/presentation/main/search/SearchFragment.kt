package graduation.fcm.dermai.presentation.main.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import graduation.fcm.dermai.common.ResponseState
import graduation.fcm.dermai.common.extentions.gone
import graduation.fcm.dermai.common.extentions.hide
import graduation.fcm.dermai.common.extentions.show
import graduation.fcm.dermai.core.BaseFragment
import graduation.fcm.dermai.databinding.FragmentSearchBinding
import graduation.fcm.dermai.domain.model.home.SearchResult
import graduation.fcm.dermai.presentation.main.adapter.SearchHistoryAdapter
import graduation.fcm.dermai.presentation.main.utils.FromScreen

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    override val viewModel: SearchViewModel by viewModels()
    override fun selfHandleObserveState(): Boolean = false

    private val searchHistoryAdapter = SearchHistoryAdapter {
        navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailsFragment(
                it, false
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSearchHistory()
        setUpRv()
        handleClicks()
        observeData()

    }

    private fun observeData() {
        viewModel.searchHistoryResult.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseState.Error -> {
                    binding.textView3.gone()
                }
                is ResponseState.Loading -> {
                }
                is ResponseState.NotAuthorized -> logOut()
                is ResponseState.Success -> {
                    val data = it.data?.data ?: return@observe
                    binding.textView3.show()

                    val diseaseToUse =
                        data.history ?: data.disease ?: data.diseases ?: return@observe

                    val searchResults = diseaseToUse.map { disease ->
                        SearchResult(
                            disease.id,
                            disease.name,
                            if (disease.attachment.isNotEmpty()) {
                                disease.attachment[0].url
                            } else {
                                ""
                            },
                            disease
                        )
                    }

                    Log.e("searchResults", "searchResults: $searchResults")
                    if (searchResults.isEmpty()) binding.textView3.gone()
                    else binding.textView3.show()

                    searchHistoryAdapter.submitList(searchResults)
                }
            }
        }
    }

    private fun handleClicks() {
        binding.searchBar.searchEt.addTextChangedListener {
            if (it.toString().isNotEmpty()) {
                binding.searchBar.sendSearchIconIv.show()
            } else {
                binding.searchBar.sendSearchIconIv.hide()
            }
        }

        binding.searchBar.sendSearchIconIv.setOnClickListener {
            val searchQuery = binding.searchBar.searchEt.text.toString().trim()
            navigate(
                SearchFragmentDirections.actionSearchFragmentToResultFragment(
                    FromScreen.SEARCH,
                    searchQuery,
                    -1,
                    ""
                )
            )
        }

    }

    private fun setUpRv() {
        binding.recentRv.adapter = searchHistoryAdapter
    }

    override fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentSearchBinding.inflate(inflater, container, false)


}