package com.gfb.watchlist.ui.addToList.impl

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.gfb.watchlist.R
import com.gfb.watchlist.ui.BaseView
import com.gfb.watchlist.ui.adapter.ResumedContentAdapter
import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.Result
import com.gfb.watchlist.prefs
import com.gfb.watchlist.ui.addToList.AddToListView
import com.gfb.watchlist.ui.main.impl.MainViewImpl
import com.gfb.watchlist.ui.preview.impl.PreviewViewImpl
import com.gfb.watchlist.util.Constants
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_add_to_list.*
import org.jetbrains.anko.*

class AddToListViewImpl : BaseView(), AddToListView {

    private val presenter = AddToListPresenterImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_list)
        (R.string.title_search)
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
        presenter.searchOnImdb(query)
    }

    override fun onContentFound(contents: List<Content>) {
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

    private fun handleContentAdded(response: Result) {
        alert(response.message, getString(R.string.title_success)) {
            yesButton {
                presenter.handleContentAdded(response)
            }
        }.show()
    }

    override fun onAddContent(observable: Observable<Result>) {
        observable.applySchedulers()
                .subscribeBy(
                        onNext = {
                            handleContentAdded(it)
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

    override fun onFindContent(observable: Observable<List<Content>>) {
        observable.applySchedulers()
                .subscribeBy(
                        onNext = {
                            presenter.handleContentFound(it)
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

    override fun onContentAdded(result: Result) {
        startActivity(intentFor<MainViewImpl>().clearTask().newTask())
        finish()
    }

    override fun onShowDetails(imdbId: String) {
        startActivity<PreviewViewImpl>(Constants.TRANSITION_KEY_CONTENT to imdbId)
    }
}


