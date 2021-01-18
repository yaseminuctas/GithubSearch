package com.yaseminuctas.githubsearch.view

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.yaseminuctas.githubsearch.R
import com.yaseminuctas.githubsearch.databinding.ActivityMainBinding
import com.yaseminuctas.githubsearch.model.RepoNameResponse
import com.yaseminuctas.githubsearch.model.UserNameResponse
import com.yaseminuctas.githubsearch.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()
    private lateinit var animation: AnimationDrawable
    lateinit var binding: ActivityMainBinding
    var userNameList: List<UserNameResponse.ItemUserName>? = null
    var repoNameList: List<RepoNameResponse.ItemRepoName>? = null
    private val userNameAdapter = UserNameAdapter()
    private val repoNameAdapter = RepoNameAdapter()
    private var tabIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
    }

    private fun initDataBinding() {
        binding =
            DataBindingUtil.setContentView(
                this,
                R.layout.activity_main
            )

        binding.viewModel = viewModel
        searchViewDetectDidChange()
        animation = img_loading.drawable as AnimationDrawable

        viewModel.userName.observe(this, Observer { data ->
            userNameList = data
        })

        viewModel.repoName.observe(this, Observer { data ->
            repoNameList = data
        })

        initIsLoading()
    }


    private fun initIsLoading() {
        viewModel.isLoading.observe(this, Observer {
            if (it) {
                //showLoading
                img_loading.visibility = View.VISIBLE
                animation.start()
            } else {
                img_loading.visibility = View.GONE
                animation.stop()

                if (!userNameList.isNullOrEmpty() || !repoNameList.isNullOrEmpty()) {
                    arrangeTabLayout()
                    tabSelected(tabIndex)
                }
            }
        })
    }

    private fun searchViewDetectDidChange() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                if (TextUtils.isEmpty(newText)) {
                    viewModel.removeData(true, true)
                    fetchSearchingUi()
                } else {
                    if (shouldFetchData(newText!!)) {
                        fetchSearchingUi()
                        viewModel.getSearchUserName(newText)
                        viewModel.getSearchRepoName(newText)
                    } else {
                        viewModel.removeData(true, true)
                    }
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
        })
    }

    fun fetchSearchingUi() {
        tabLayout.visibility = View.GONE
        recyclerCategories.visibility = View.GONE
        divider.visibility = View.GONE
    }

    //fetch data rules
    fun shouldFetchData(query: String): Boolean {
        if (query.contains(",") ||
            query.contains(";") ||
            query.contains(":")
        ) {
            return false
        }
        if (query.length <= 2) {
            return false
        }

        return true
    }

    private fun arrangeTabLayout() {
        tabLayout.visibility = View.VISIBLE
        recyclerCategories.visibility = View.VISIBLE
        divider.visibility = View.VISIBLE
        if (tabLayout.tabCount == 0) {
            tabLayout.addTab(
                tabLayout.newTab().setText(R.string.user_name),
                0,
                true
            )
            tabLayout.addTab(
                tabLayout.newTab().setText(R.string.repo_name),
                1,
                false
            )
            showFirstTabContent(userNameList!!)
            arrangeTabLayoutListener(tabLayout)

        }
    }

    private fun showFirstTabContent(dataList: List<UserNameResponse.ItemUserName>) {
        recyclerCategories.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerCategories.adapter = userNameAdapter
        userNameAdapter.setUserList(dataList)
    }

    private fun arrangeTabLayoutListener(
        tabLayoutDetail: TabLayout
    ) {
        tabLayoutDetail.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                //Nothing
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                tabIndex = tab.position
                tabSelected(tabIndex)
            }
        })
    }

    private fun tabSelected(selectedIndex: Int) {
        when (selectedIndex) {
            0 -> {
                userNameList?.let {
                    showFirstTabContent(it)
                }
            }
            1 -> {
                recyclerCategories.layoutManager = LinearLayoutManager(
                    this@MainActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                recyclerCategories.adapter = repoNameAdapter
                repoNameList?.let { repoNameAdapter.setRepoList(it) }
            }
        }
    }
}
