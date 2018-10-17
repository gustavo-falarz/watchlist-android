package com.gfb.watchlist.ui.addToList.impl

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.gfb.watchlist.R
import com.gfb.watchlist.activity.BaseActivity
import com.gfb.watchlist.activity.MainActivity
import com.gfb.watchlist.adapter.ResumedContentAdapter
import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.ContentContainer
import com.gfb.watchlist.entity.Result
import com.gfb.watchlist.prefs
import com.gfb.watchlist.service.ContentService
import com.gfb.watchlist.ui.addToList.AddToListView
import com.gfb.watchlist.ui.preview.impl.PreviewViewImpl
import com.gfb.watchlist.util.Constants
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_add_to_list.*
import org.jetbrains.anko.*

class AddToListViewImpl : BaseActivity(), AddToListView {
    private val presenter = AddToListPresenterImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_list)
        setupToolbar(R.string.title_search)
        setupActionBar()

        recyclerView.layoutManager = LinearLayoutManager(this)
        imSearchOnImdb.setOnClickListener { searchOnImdb() }
        etSearchContent.setOnEditorActionListener(onActionSearch())
    }

    private fun onActionSearch(): TextView.OnEditorActionListener? {
        return TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchOnImdb()
                true
            } else {
                false
            }
        }
    }

    private fun searchOnImdb() {
        hideKeyboard()
        val query = etSearchContent.text.toString().trim()
        showProgress()
        ContentService.searchOnImdb(query).applySchedulers()
                .subscribeBy(
                        onNext = {
                            createAdapter(it)
                            closeProgress()
                        },
                        onError = {
                            handleException(it)
                            closeProgress()
                        },
                        onComplete = {
                            closeProgress()
                        }
                )
    }

    private fun createAdapter(contents: List<Content>) {
        recyclerView.adapter = ResumedContentAdapter(contents,
                {
                    presenter.showDetails(it.imdbID)
                },
                {
                    confirmAddition(it)
                })
    }

    private fun confirmAddition(content: Content) {
        alert(String.format(getString(R.string.message_confirmation_add_content), content.title), getString(R.string.title_add_content)) {
            positiveButton(R.string.yes) {
                presenter.addContent(prefs.userId, content)
            }
            negativeButton(R.string.no) {}
        }.show()
    }

    private fun handleResult(response: Result) {
        alert(response.message, getString(R.string.title_success)) {
            yesButton {
                startActivity(intentFor<MainActivity>().clearTask().newTask())
                ContentContainer.updated = true
                finish()
            }
        }.show()
    }

    override fun onContentAdded(observable: Observable<Result>) {
        observable.applySchedulers()
                .subscribeBy(
                        onNext = {
                            handleResult(it)
                        },
                        onError = {
                            closeProgress()
                            handleException(it)
                        },
                        onComplete = {
                            closeProgress()
                        }
                )
    }

    override fun onContentFound(observable: Observable<List<Content>>) {
        observable.applySchedulers()
                .subscribeBy(
                        onNext = {
                            createAdapter(it)
                            closeProgress()
                        },
                        onError = {
                            handleException(it)
                            closeProgress()
                        },
                        onComplete = {
                            closeProgress()
                        }
                )
    }

    override fun onShowDetails(imdbId: String) {
        startActivity<PreviewViewImpl>(Constants.TRANSITION_KEY_CONTENT to imdbId)
    }
}


