package com.stokeapp.stoke

import android.app.Activity

abstract class Navigator {
    abstract fun goToNext(activity: Activity)

    protected inline fun useAndFinish(activity: Activity, block: (Activity) -> Unit) {
        block(activity)
        activity.finish()
    }
}