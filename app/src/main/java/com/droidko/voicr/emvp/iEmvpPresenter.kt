package com.droidko.voicr.emvp

import com.droidko.voicr.firebase.DbAccess

interface iEmvpPresenter {

    fun dbAccess(): DbAccess { return DbAccess() }

}