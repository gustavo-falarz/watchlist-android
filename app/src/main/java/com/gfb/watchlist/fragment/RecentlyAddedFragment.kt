package com.gfb.watchlist.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gfb.watchlist.R
import com.gfb.watchlist.activity.ContentDetailsActivity
import com.gfb.watchlist.adapter.ContentAdapter
import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.ContentContainer
import com.gfb.watchlist.entity.UserInfo
import com.gfb.watchlist.entity.dto.UserContentDTO
import com.gfb.watchlist.service.ContentService
import com.gfb.watchlist.util.Constants
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton


class RecentlyAddedFragment : BaseFragment() {
    private lateinit var recyclerViewContent: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_recently_added, container, false)
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
        setAdapter()
    }

    private fun setAdapter() {
        val adapter = ContentAdapter(ContentContainer.content,
                { content -> callActivity(content) },
                { content -> confirmationArchive(content) })

        recyclerViewContent.adapter = adapter
    }

    private fun callActivity(content: Content) {
        val intent = Intent(context, ContentDetailsActivity::class.java)
        intent.putExtra(Constants.TRANSITION_KEY_CONTENT, content)
        startActivity(intent)
    }

    private fun confirmationArchive(content: Content) {
        alert(String.format(getString(R.string.message_confirmation_archive_content), content.title), getString(R.string.title_add_content)) {
            positiveButton(R.string.yes) { archiveContent(content) }
            negativeButton(R.string.no) {}
        }.show()
    }

    private fun archiveContent(content: Content) {
        showProgress()
        ContentService.archiveContent(UserContentDTO(UserInfo.userId, content)).applySchedulers()
                .subscribe(
                        { response ->
                            closeProgress()
                            alert(response.message, getString(R.string.title_success)) {
                                yesButton {
                                    ContentContainer.content.remove(content)
                                    setAdapter()
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
