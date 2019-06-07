package com.skydoves.githubfollows.view.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewTreeObserver
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.skydoves.githubfollows.R
import com.skydoves.githubfollows.databinding.ActivitySearchBinding
import com.skydoves.githubfollows.extension.checkIsMaterialVersion
import com.skydoves.githubfollows.extension.circularRevealed
import com.skydoves.githubfollows.extension.circularUnRevealed
import com.skydoves.githubfollows.extension.inVisible
import com.skydoves.githubfollows.extension.observeLiveData
import com.skydoves.githubfollows.extension.vm
import com.skydoves.githubfollows.factory.AppViewModelFactory
import com.skydoves.githubfollows.models.GithubUser
import com.skydoves.githubfollows.models.History
import com.skydoves.githubfollows.models.Resource
import com.skydoves.githubfollows.models.Status
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

class SearchActivity : AppCompatActivity(),
  TextView.OnEditorActionListener,
  HistoryViewHolder.Delegate {

  @Inject
  lateinit var viewModelFactory: AppViewModelFactory
  private val viewModel by lazy { vm(viewModelFactory, SearchActivityViewModel::class) }
  private lateinit var binding: ActivitySearchBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
    binding.viewModel = viewModel
    binding.lifecycleOwner = this

    initializeUI()
    startCircularRevealed(savedInstanceState)
    observeLiveData(viewModel.githubUserLiveData) { onChangeUser(it) }
  }

  private fun startCircularRevealed(savedInstanceState: Bundle?) {
    if (savedInstanceState == null && checkIsMaterialVersion()) {
      search_layout.inVisible()

      val viewTreeObserver = search_layout.viewTreeObserver
      if (viewTreeObserver.isAlive) {
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
          override fun onGlobalLayout() {
            circularRevealed(search_layout, search_layout.width, 0)
            search_layout.viewTreeObserver.removeOnGlobalLayoutListener(this)
          }
        })
      }
    }
  }

  private fun initializeUI() {
    search_recyclerView.layoutManager = LinearLayoutManager(this)
    search_recyclerView.adapter = HistoryAdapter(this)
    toolbar_search_home.setOnClickListener { onBackPressed() }
    toolbar_search_input.setOnEditorActionListener(this)
  }

  override fun onEditorAction(p0: TextView?, actionId: Int, event: KeyEvent?): Boolean {
    val searchKeyword = toolbar_search_input.text
    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
      searchKeyword?.let {
        viewModel.login.postValue(it.toString())
        return true
      }
    }
    return false
  }

  override fun onItemClicked(history: History) {
    onSetResult(history.search)
  }

  override fun onDeleteHistory(history: History) {
    viewModel.deleteHistory(history)
  }

  private fun onChangeUser(resource: Resource<GithubUser>) {
    when (resource.status) {
      Status.SUCCESS -> onSetResult(resource.data?.login!!)
      Status.ERROR -> toast(resource.message.toString())
      Status.LOADING -> Unit
    }
  }

  private fun onSetResult(user: String) {
    viewModel.insertHistory(user)
    setResult(intent_requestCode, Intent().putExtra(viewModel.getPreferenceUserKeyName(), user))
    onBackPressed()
  }

  override fun onBackPressed() {
    when (checkIsMaterialVersion()) {
      true -> circularUnRevealed(search_layout, search_layout.width, 0)
      false -> super.onBackPressed()
    }
  }

  companion object {
    const val intent_requestCode = 1001
  }
}
