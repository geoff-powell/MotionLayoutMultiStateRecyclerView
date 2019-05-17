package com.example.motionlayoutmultistaterecyclerview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_dummy.view.*

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        motion_layout.setTransitionListener(object: TransitionAdapter() {
            override fun allowsTransition(transition: MotionScene.Transition): Boolean {
                Log.d(TAG, "allowsTransition start ${transition.startConstraintSetId} end ${transition.endConstraintSetId}")
                return super.allowsTransition(transition)
            }

            override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {
                Log.d(TAG, "onTransitionStarted start $startId end $endId")
            }

            override fun onTransitionChange(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) {
                Log.d(TAG, "onTransitionChange start $startId end $endId progress $progress")
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                Log.d(TAG, "onTransitionCompleted currentId $currentId ")
            }
        })

        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

                val items: List<Int> = (0 until 20).toList()

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                    return object : RecyclerView.ViewHolder(
                        LayoutInflater.from(parent.context).inflate(
                            R.layout.item_dummy,
                            parent,
                            false
                        )
                    ) {}
                }

                override fun getItemCount(): Int {
                    return items.size
                }

                @SuppressLint("SetTextI18n")
                override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                    holder.itemView.title.text = "Title-${items[position]}"
                }
            }
        }
    }
}
