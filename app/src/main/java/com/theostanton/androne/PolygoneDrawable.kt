package com.theostanton.androne

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.drawable.Drawable
import android.util.FloatProperty

typealias Hun = String

class PolygoneDrawable : Drawable(){

  var dotProgress = 0f
    set(value) {
      field = value.coerceIn(0f, 1f)
      callback.invalidateDrawable(this)
    }

  override fun draw(p0: Canvas?) {

  }

  override fun setAlpha(p0: Int) {

  }

  override fun getOpacity(): Int {

  }



  override fun setColorFilter(p0: ColorFilter?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  object LOL : FloatProperty<String>("ll"){
    override fun get(p0: String?): Float {
      TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setValue(p0: String?, p1: Float) {
      TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

  }


}