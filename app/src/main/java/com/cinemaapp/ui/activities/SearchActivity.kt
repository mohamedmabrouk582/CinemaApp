package com.cinemaapp.ui.activities
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.cinemaapp.R
import com.cinemaapp.app.MyApp
import com.cinemaapp.callBacks.SearchCallBack
import com.cinemaapp.data.enums.DetailsType
import com.cinemaapp.data.models.SearchResult
import com.cinemaapp.databinding.SearchLayoutBinding
import com.cinemaapp.di.components.DaggerSearchComponent
import com.cinemaapp.ui.adapters.SearchAdapter
import com.cinemaapp.ui.base.BaseActivity
import com.cinemaapp.viewModels.SearchViewModel
import com.cinemaapp.viewModels.base.BaseViewModel
import com.cinemaapp.viewModels.base.BaseViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


/*
* Created By mabrouk on 06/05/19
* CinemaApp
*/

class SearchActivity : BaseActivity() , SearchCallBack, SearchAdapter.SearchListener {
    @Inject
    lateinit var factory:BaseViewModelFactory
    lateinit var layoutBinding: SearchLayoutBinding
    lateinit var viewModel: SearchViewModel<SearchCallBack>
    var suggestions: ArrayList<String> = ArrayList()
    val columns= arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1, SearchManager.SUGGEST_COLUMN_INTENT_DATA)
    lateinit var cursor:MatrixCursor
    val keywordsAdapter: SimpleCursorAdapter by lazy {
         SimpleCursorAdapter(this,
            R.layout.suggested_item_view,
            null,
            arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1),
            intArrayOf(R.id.Suggestions_text),
            0)
    }
    val searchAdapter:SearchAdapter by lazy {
        SearchAdapter(this@SearchActivity)
    }

    companion object{
        fun start(context: Context){
            val intent=Intent(context,SearchActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutBinding=DataBindingUtil.setContentView(this,R.layout.search_layout)
        inject()
        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu)
        val item = menu?.findItem(R.id.search_item)
        val searchView:SearchView=item?.actionView as SearchView
        searchView.isIconified=false
        searchView.suggestionsAdapter=keywordsAdapter
        searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener{
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                GlobalScope.launch (Dispatchers.Main) {
                    try {
                        searchView.setQuery(suggestions[position], true)
                        searchView.isFocusable = false
                    }catch (e:Exception){}
                }
                return true
            }

        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.reqSearch(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.reqKeyWords(newText!!,false)
                return true
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun initView() {
       viewModel=getViewModel(this,factory)
        viewModel.attachView(this)
        layoutBinding.searchRcv.adapter=searchAdapter
        layoutBinding.searchVm=viewModel
    }

    override fun inject() {
     DaggerSearchComponent.builder()
         .appComponent(MyApp.get(this).appComponent)
         .build().inject(this)
    }


    override fun getSearchActivity(): SearchActivity = this

    override fun loadKeyWorkds(data: ArrayList<String>) {
        cursor= MatrixCursor(columns)
        suggestions=data
      data.forEach {
          cursor.addRow(arrayOf(data.indexOf(it),it,it))
      }
        keywordsAdapter.swapCursor(cursor)
        keywordsAdapter.notifyDataSetChanged()
    }

    override fun loadMoreKeyWords(data: ArrayList<String>) {
        for (e in 0 .. keywordsAdapter.count){
            cursor.addRow(arrayOf(e,keywordsAdapter.getItem(e),keywordsAdapter.getItem(e)))
        }

        data.forEach {
            cursor.addRow(arrayOf(data.indexOf(it),it,it))
        }
        keywordsAdapter.swapCursor(cursor)
    }

    override fun loadSearchData(data: PagedList<SearchResult>) {
      searchAdapter.submitList(data)
    }

    override fun onMovieClick(item: SearchResult) {
       MovieDetailsActivity.start(this,item.id,DetailsType.MOVIE)
    }

    override fun onTvClick(item: SearchResult) {
        MovieDetailsActivity.start(this,item.id,DetailsType.TV)
    }

    private inline fun <reified T : BaseViewModel<SearchCallBack>> getViewModel(activity: FragmentActivity, factory: BaseViewModelFactory): T {
        return ViewModelProviders.of(activity,factory)[T::class.java]
    }

}