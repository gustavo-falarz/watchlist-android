package com.gfb.watchlist.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.gfb.watchlist.R
import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.ContentContainer
import com.gfb.watchlist.entity.UserInfo
import com.gfb.watchlist.entity.dto.UserContentDTO
import com.gfb.watchlist.service.ContentService
import kotlinx.android.synthetic.main.activity_create_content.*
import org.jetbrains.anko.*

class CreateContentActivity : BaseActivity(), AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        selectedType = types[p2]
    }

    private var types = arrayOf("Movie", "Series")
    private lateinit var selectedType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_content)
        setupToolbar(R.string.title_add_new_content)
        setupActionBar()
        btCreate.setOnClickListener { create() }

        spType!!.onItemSelectedListener = this

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, types)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spType!!.setAdapter(aa)
    }

    private fun create() {
        var b = checkFields()
        if (b) {
            var content = Content(null, etTitle.text.toString(), etReleased.text.toString(),
                    selectedType, etPoster.text.toString(), null, etDirector.text.toString(), etPlot.text.toString(), null,
                    null, null, null, null)

            if(content.poster == ""){
                content.poster = "http://www.pvhc.net/img20/codszkegyqycyqtotrwk.png"
            }
            showProgress()
            ContentService.addContentAula( content).applySchedulers()
                    .subscribe(
                            { response ->
                                if (response.status) {
                                    alert(response.message, getString(R.string.title_success)) {
                                        yesButton {
                                            ContentContainer.content = null
                                            startActivity(intentFor<MainActivity>().clearTask().newTask())
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
        } else {
            showWarning("Some fields might be empty")
        }
    }

    private fun checkFields(): Boolean {
        if (etTitle.text.isNullOrEmpty()) {
            return false
        }
        if (etReleased.text.isNullOrEmpty()) {
            return false
        }
        return true
    }


}
