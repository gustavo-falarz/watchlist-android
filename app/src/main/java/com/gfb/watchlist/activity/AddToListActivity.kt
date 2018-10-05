package com.gfb.watchlist.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.gfb.watchlist.R
import com.gfb.watchlist.adapter.ResumedContentAdapter
import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.ContentContainer
import com.gfb.watchlist.entity.dto.UserContentDTO
import com.gfb.watchlist.prefs
import com.gfb.watchlist.service.ContentService
import com.gfb.watchlist.util.Constants
import kotlinx.android.synthetic.main.activity_add_to_list.*
import org.jetbrains.anko.*

class AddToListActivity : BaseActivity() {

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
        if(query.isEmpty()){
            showWarning(getString(R.string.warning_empty_search_query))
            return
        }

        showProgress()
        ContentService.searchOnImdb(query).applySchedulers()
                .subscribe(
                        { contents ->
                            setAdapter(contents)
                            closeProgress()
                        },
                        { error ->
                            handleException(error)
                            closeProgress()
                        }
                )
    }

    private fun setAdapter(contents: List<Content>) {
        recyclerView.adapter = ResumedContentAdapter(contents,
                {
                    showDetails(it)
                },
                {
                    confirmAddition(it)
                })
    }

    private fun confirmAddition(content: Content) {
        alert(String.format(getString(R.string.message_confirmation_add_content), content.title), getString(R.string.title_add_content)) {
            positiveButton(R.string.yes) { addToList(content) }
            negativeButton(R.string.no) {}
        }.show()
    }

    private fun showDetails(content: Content) {
        val intent = Intent(this, PreviewActivity::class.java)
        intent.putExtra(Constants.TRANSITION_KEY_CONTENT, content.imdbID)
        startActivity(intent)
    }

    private fun addToList(content: Content) {
        showProgress()
        ContentService.addContent(UserContentDTO(prefs.userId, content, null)).applySchedulers()
                .subscribe(
                        { response ->
                            if (response.status) {
                                alert(response.message, getString(R.string.title_success)) {
                                    yesButton {
                                        startActivity(intentFor<MainActivity>().clearTask().newTask())
                                        ContentContainer.updated = true
                                        finish()
                                    }
                                }.show()
                            }
                            closeProgress()
                        },
                        { error ->
                            closeProgress()
                            handleException(error)
                        }
                )
    }

}


