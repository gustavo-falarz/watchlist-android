package com.gfb.watchlist.fragment


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.gfb.watchlist.R
import com.gfb.watchlist.activity.MainActivity
import com.gfb.watchlist.adapter.ContentAdapter
import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.ContentContainer
import com.gfb.watchlist.entity.UserInfo
import com.gfb.watchlist.entity.dto.UserContentDTO
import com.gfb.watchlist.service.ContentService
import org.jetbrains.anko.alert
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.yesButton


class RecentlyAddedFragment : BaseFragment() {
    private lateinit var recyclerViewContent: RecyclerView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_recently_added, container, false)
        recyclerViewContent = view.findViewById(R.id.recyclerViewContent)
        recyclerViewContent.layoutManager = LinearLayoutManager(view.context)
        return view

    }

    companion object {

        fun newInstance(): RecentlyAddedFragment {
            return RecentlyAddedFragment()
        }
    }

    override fun onStart() {
        super.onStart()
        when {
            ContentContainer.isEmpty() -> findContent()
            else -> setAdapter()
        }

    }

    private fun setAdapter() {
        val adapter = ContentAdapter(ContentContainer.content!!) {
            confirmationArchive(it)
        }
        recyclerViewContent.adapter = adapter
    }

    private fun findContent() {
        showProgress()
        ContentService.findContent(UserContentDTO(UserInfo.userId, null, null)).applySchedulers()
                .subscribe(
                        { content ->
                            closeProgress()
                            ContentContainer.initContent(content)
                            setAdapter()
                        },
                        { error ->
                            closeProgress()
                            handleException(error)
                        }
                )
    }

    private fun confirmationArchive(content: Content) {
        alert(String.format(getString(R.string.message_confirmation_archive_content), content.title), getString(R.string.message_title_add_content)) {
            positiveButton(R.string.yes) { archiveContent(content) }
            negativeButton(R.string.no) {}
        }.show()
    }

    private fun archiveContent(content: Content) {
        showProgress()
        ContentService.archiveContent(UserContentDTO(UserInfo.userId, content, null)).applySchedulers()
                .subscribe(
                        { response ->
                            closeProgress()
                            alert(response.message, getString(R.string.message_title_success)) {
                                yesButton {
                                    //TODO Atualizar lista
                                }
                            }.show()
                        },
                        { error ->
                            closeProgress()
                            handleException(error)
                        }
                )
    }
}
