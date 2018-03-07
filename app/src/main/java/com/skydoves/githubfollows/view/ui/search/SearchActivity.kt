package com.skydoves.githubfollows.view.ui.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.ViewTreeObserver
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.skydoves.githubfollows.R
import com.skydoves.githubfollows.extension.checkIsMaterialVersion
import com.skydoves.githubfollows.extension.circularRevealed
import com.skydoves.githubfollows.extension.circularUnRevealed
import com.skydoves.githubfollows.extension.inVisible
import com.skydoves.githubfollows.factory.AppViewModelFactory
import com.skydoves.githubfollows.models.History
import com.skydoves.githubfollows.view.adapter.HistoryAdapter
import com.skydoves.githubfollows.view.viewholder.HistoryViewHolder
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar_search.*
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-01-22.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class SearchActivity : AppCompatActivity(), TextView.OnEditorActionListener, HistoryViewHolder.Delegate {

    @Inject lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(SearchActivityViewModel::class.java) }
    private val adapter by lazy { HistoryAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        startCircularRevealed(savedInstanceState)

        observeViewModel()
        initializeAdapter()

        toolbar_search_home.setOnClickListener { onBackPressed() }
        toolbar_search_input.setOnEditorActionListener(this)
    }

    private fun startCircularRevealed(savedInstanceState: Bundle?) {
        if (savedInstanceState == null && checkIsMaterialVersion()) {
            search_layout.inVisible()

            val viewTreeObserver = search_layout.viewTreeObserver
            if (viewTreeObserver.isAlive) {
                viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        circularRevealed(search_layout ,search_layout.width, 0)
                        search_layout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                })
            }
        }
    }

    private fun observeViewModel() {
        viewModel.historiesLiveData.observe(this, Observer { it?.let { adapter.updateItemList(it) } })
        viewModel.githubUserLiveData.observe(this, Observer { it?.let { onChangeUser(it.login) } })
        viewModel.toastMessage.observe(this, Observer { toast(it.toString()) })
    }

    private fun initializeAdapter() {
        search_recyclerView.layoutManager = LinearLayoutManager(this)
        search_recyclerView.adapter = adapter
        viewModel.selectHistories()
    }

    override fun onEditorAction(p0: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        val searchKeyword = toolbar_search_input.text
        if(actionId == EditorInfo.IME_ACTION_SEARCH) {
            searchKeyword?.let {
                viewModel.fetchGithubUser(it.toString())
                return true
            }
        }
        return false
    }

    override fun onItemClicked(history: History) {
        onChangeUser(history.search)
    }

    override fun onDeleteHistory(history: History) {
        viewModel.deleteHistory(history)
    }

    private fun onChangeUser(userName: String) {
        viewModel.putPreferenceUserName(userName)
        viewModel.insertHistory(userName)
        setResult(intent_requestCode, Intent().putExtra(viewModel.getPreferenceUserKeyName(), userName))
        onBackPressed()
    }

    override fun onBackPressed() {
        when(checkIsMaterialVersion()) {
            true -> circularUnRevealed(search_layout , search_layout.width, 0)
            false -> super.onBackPressed()
        }
    }

    companion object {
        const val intent_requestCode = 1001
    }
}
