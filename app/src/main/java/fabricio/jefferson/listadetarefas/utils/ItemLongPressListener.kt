package fabricio.jefferson.listadetarefas.utils

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class ItemLongPressListener(context: Context, recyclerView: RecyclerView, val clickListener: ClickListener?) : RecyclerView.OnItemTouchListener {

    private val gestureDetector: GestureDetector

    init {
        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener(){
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                val child = recyclerView.findChildViewUnder(e.x,e.y)
                if(child != null && clickListener != null){
                    clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child))
                }
            }
        })
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val child = rv.findChildViewUnder(e.x,e.y)
        if(child != null && clickListener != null && gestureDetector.onTouchEvent(e)){
            clickListener.onClick(child, rv.getChildAdapterPosition(child))
        }

        return false
    }


    override fun onTouchEvent(p0: RecyclerView, p1: MotionEvent) {

    }

    override fun onRequestDisallowInterceptTouchEvent(p0: Boolean) {

    }


    interface ClickListener {
        fun onClick(view: View, position: Int)
        fun onLongClick(view: View?, position: Int)
    }


}