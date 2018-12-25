package at.shockbytes.corey.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import at.shockbytes.corey.R
import at.shockbytes.corey.data.goal.Goal
import at.shockbytes.util.adapter.BaseAdapter
import kotterknife.bindView

/**
 * Author:  Martin Macheiner
 * Date:    08.03.2017
 */
class GoalAdapter(cxt: Context, data: List<Goal>) : BaseAdapter<Goal>(cxt, data.toMutableList()) {

    private var onGoalActionClickedListener: OnGoalActionClickedListener? = null

    interface OnGoalActionClickedListener {

        fun onDeleteGoalClicked(g: Goal)

        fun onFinishGoalClicked(g: Goal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseAdapter<Goal>.ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_goal, parent, false))
    }

    fun setOnGoalActionClickedListener(listener: OnGoalActionClickedListener) {
        onGoalActionClickedListener = listener
    }

    inner class ViewHolder(itemView: View) : BaseAdapter<Goal>.ViewHolder(itemView) {

        private val txtGoal: TextView by bindView(R.id.item_goal_text)
        private val imgBtnDone: ImageButton by bindView(R.id.item_goal_btn_done)

        override fun bindToView(t: Goal) {

            txtGoal.text = t.message

            if (t.done) {
                imgBtnDone.setImageResource(R.drawable.ic_cancel)
                txtGoal.paintFlags = txtGoal.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                imgBtnDone.setImageResource(R.drawable.ic_done)
                txtGoal.paintFlags = txtGoal.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            imgBtnDone.setOnClickListener{
                onClickDone()
            }
        }

        private fun onClickDone() {
            if (content.done) {
                onGoalActionClickedListener?.onDeleteGoalClicked(content)
            } else {
                onGoalActionClickedListener?.onFinishGoalClicked(content)
            }
        }

    }

}