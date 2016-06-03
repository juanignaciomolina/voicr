package com.droidko.voicr.views.home

import android.view.View
import com.droidko.voicr.R
import com.droidko.voicr.model.Message
import com.droidko.voicr.presenters.home.HomePresenter
import com.droidko.voicr.views.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: BaseFragment(), iHomeView {

    val presenter by lazy { HomePresenter(this) }

    override fun onLayoutRequested(): Int {
        return R.layout.fragment_home
    }

    override fun onPopulateUi(rootView: View) {
        vSendMessageButton.setOnClickListener {
            presenter.sendMessage(vNewMessageEditText.text.toString())

            vNewMessageEditText.text.clear()
            toast("Sending message...")
        }
    }

    override fun onMessageArrive(message: Message) {
        vMessageViewer.text = message.message
        vUsernameViewer.text = message.userEmail
    }

    override fun onResume() {
        super.onResume()
        presenter.startListeningForMessages()
    }

    override fun onPause() {
        presenter.stopListeningForMessages()
        super.onPause()
    }
}