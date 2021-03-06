package com.example.bmimaster

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class LiterallyACircle(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private val paint = Paint()
    private var _color: Int = Color.RED
    var color: Int
        get() = this._color
        set(value) {
            this._color = value
            paint.color = value
        }

    init{
        paint.color = color
        paint.strokeWidth = 12f
        paint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val circleWidth = width/2f
        val circleHeight = height/2f
        val radius = circleWidth.coerceAtMost(circleHeight)
        canvas?.drawCircle(circleWidth, circleHeight, radius, paint)
    }
}