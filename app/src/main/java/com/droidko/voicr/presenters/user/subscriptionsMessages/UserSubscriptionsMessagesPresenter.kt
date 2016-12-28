package com.droidko.voicr.presenters.user.subscriptionsMessages

import com.droidko.voicr.emvp.iEmvpPresenter
import com.droidko.voicr.presenters.user.subscriptions.UserSubscriptionsPresenter
import com.droidko.voicr.presenters.user.subscriptions.iUserSubscriptionsInput
import com.droidko.voicr.presenters.user.subscriptions.iUserSubscriptionsOutput

class UserSubscriptionsMessagesPresenter(output: iUserSubscriptionsOutput) :
        UserSubscriptionsPresenter(output),
        iEmvpPresenter,
        iUserSubscriptionsInput {

    // TODO IMPLEMENTAR UNA FORMA DE RECIBIR EL ESTADO DE LOS CHANNELS EN TIEMPO REAL
    // SIN TENER QUE RECIBIR TODOS LOS MENSAJES VIEJOS, SOLO CUANDO HAYA UN NUEVO MENSAJE

    // Una forma podria ser tener un nodo en la DB que mantenga el "ultimo estado" de cada channel
    // y que cada vez que se produzca una modificacion en los mensajes de un channel, se actualizen
    // los childs de este nodo

}