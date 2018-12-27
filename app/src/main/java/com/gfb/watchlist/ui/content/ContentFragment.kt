package com.gfb.watchlist.ui.content

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.gfb.watchlist.R
import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.Result
import com.gfb.watchlist.prefs
import com.gfb.watchlist.ui.BaseFragment
import com.gfb.watchlist.ui.adapter.ContentAdapter
import com.gfb.watchlist.ui.content.impl.ContentPresenterImpl
import com.gfb.watchlist.ui.contentDetails.impl.ContentDetailsViewImpl
import com.gfb.watchlist.util.Constants
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.yesButton

open class ContentFragment : BaseFragment(), ContentView {
    private var inflated = false
    private lateinit var adapter: ContentAdapter
    protected lateinit var etSearch: EditText
    protected lateinit var type: String
    protected lateinit var recyclerView: RecyclerView
    protected val presenter = ContentPresenterImpl(this)

    override fun onArchiveContent(observable: Observable<Result>, content: Content) {
        showProgress()
        observable.applySchedulers()
                .subscribeBy(
                        onNext = {
                            deleteContent(it, content)
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

    override fun setupAdapter() {
        presenter.setupAdapter(type)
    }

    override fun deleteContent(result: Result, content: Content) {
        alert(result.message, getString(R.string.title_success)) {
            yesButton {
                presenter.deleteContent(content)
            }
        }.show()
    }

    override fun onContentDeleted() {
        presenter.setupAdapter(type)
    }

    override fun onShowDetails(content: Content) {
        startActivity<ContentDetailsViewImpl>(Constants.TRANSITION_KEY_CONTENT to content)
    }

    override fun updateList(items: List<Content>) {
        adapter.setItems(items)
        adapter.notifyDataSetChanged()
    }

    override fun onSearch(): TextWatcher? {
        return object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                presenter.searchContent(p0.toString(), (type))
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        }
    }

    override fun createAdapter(list: List<Content>) {
        adapter = ContentAdapter(list,
                { content -> showDetails(content) },
                { content -> confirmationArchive(content) })
        recyclerView.adapter = adapter
        inflated = true
    }

    override fun showDetails(content: Content) {
        presenter.showDetails(content)
    }

    override fun confirmationArchive(content: Content) {
        alert(String.format(getString(R.string.message_confirmation_archive_content),
                content.title), getString(R.string.title_archive_content)) {
            positiveButton(R.string.yes) {
                archiveContent(content)
            }
            negativeButton(R.string.no) {}
        }.show()
    }

    override fun archiveContent(content: Content) {
        presenter.archiveContent(prefs.userId, content)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        when {
            inflated && !isVisibleToUser -> {
                etSearch.setText("")
            }
        }
    }
}