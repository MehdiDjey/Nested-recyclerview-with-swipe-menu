package com.example.nestedRecyclerViewSwipeMenu

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import java.text.SimpleDateFormat


class TasksAdapter(private val tasks: ArrayList<HashMap<String, String>>) :
    RecyclerView.Adapter<TasksAdapter.ViewHolder>() {
    private val taskByDates = tasks.groupBy { it["time"] }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sdf = SimpleDateFormat("MM/dd/yyyy")
        val dateListTasks = taskByDates.values.toMutableList()
        holder.date.text = sdf.format(dateListTasks[position][0]["time"]?.toLong())

        holder.rv.layoutManager = LinearLayoutManager(
            holder.itemView.context,
            LinearLayoutManager.VERTICAL,
            false
        )

        val adapter = TaskAdapter(dateListTasks[position] as ArrayList<HashMap<String, String>>)
        holder.rv.adapter = adapter
        swipeMenu(holder.rv, dateListTasks, position)
    }


    private fun swipeMenu(
        recyclerView: RecyclerView,
        dateListTasks: MutableList<List<HashMap<String, String>>>,
        groupPosition: Int
    ) {
        var currentTaskPosition: Int = 0
        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            // replace with  ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
            // for  right and left swipe
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                currentTaskPosition = viewHolder.adapterPosition
                // Show delete confirmation if swipped left
                if (swipeDir == ItemTouchHelper.DOWN) {
                    //TODO : auto action directly on swipe left
                    // Fade out the view as it is swiped out of the parent's bounds

                } else if (swipeDir == ItemTouchHelper.RIGHT) {
                    // Show edit dialog
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val paint = Paint()
                    val icon: Bitmap
                    val itemView = viewHolder.itemView
                    val height =
                        itemView.bottom.toFloat() - itemView.top.toFloat()
                    val width = height / 3
                    if (dX > 0) { // on Swipe right
                        //TODO : setup the right swipe action
                        /*           paint.color = ContextCompat.getColor(viewHolder.itemView.context,R.color.colorAccent)
                                   val background = RectF(
                                       itemView.left.toFloat(),
                                       itemView.top.toFloat(),
                                       dX,
                                       itemView.bottom.toFloat()
                                   )
                                   c.drawRect(background, paint)
                                   icon = BitmapFactory.decodeResource(
                                       viewHolder.itemView.resources,
                                       android.R.drawable.ic_dialog_map
                                   )
                                   val iconDest = RectF(
                                       itemView.left.toFloat() + width,
                                       itemView.top.toFloat() + width,
                                       itemView.left.toFloat() + 2 * width,
                                       itemView.bottom.toFloat() - width
                                   )
                                   c.drawBitmap(icon, null, iconDest, paint)*/
                    } else if (dX < 0) {// On swipe left

                        // color resource
                        paint.color =
                            ContextCompat.getColor(viewHolder.itemView.context, R.color.red)

                        // Draw the rect
                        c.drawRect(
                            itemView.right.toFloat() + dX / 2,
                            itemView.top.toFloat(),
                            itemView.right.toFloat(),
                            itemView.bottom.toFloat(),
                            paint
                        )

                        //Icon resource
                        icon = BitmapFactory.decodeResource(
                            viewHolder.itemView.context.resources,
                            android.R.drawable.ic_menu_delete
                        )
                        //Set the image icon for Left swipe
                        c.drawBitmap(
                            icon,
                            (itemView.right.toFloat() + dX / 3),
                            itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top
                                .toFloat() - icon.height) / 2,
                            paint
                        )
                    }

                    // Fade out the view as it is swiped out of the parent's bounds
                    /* val alpha: Float =
                         ALPHA_FULL - Math.abs(dX) / viewHolder.itemView.width.toFloat()
                     viewHolder.itemView.alpha = alpha
                     viewHolder.itemView.translationX = dX*/

                }
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX / 3,
                    dY,
                    actionState,
                    isCurrentlyActive
                )

                recyclerView.addOnItemTouchListener(object : OnItemTouchListener {
                    override fun onInterceptTouchEvent(
                        rv: RecyclerView,
                        e: MotionEvent
                    ): Boolean {

                        val viewSwipedRight =
                            rv.findChildViewUnder(e.x - rv.width, e.y)

                        if (e.action == MotionEvent.ACTION_DOWN && viewSwipedRight != null) {
                            //TODO : do your stuff here on click on background
                            Toast.makeText(
                                viewHolder.itemView.context,
                                "" + dateListTasks[groupPosition][currentTaskPosition]["task"],
                                Toast.LENGTH_SHORT
                            ).show()
                            return true

                        }
                        return false
                    }

                    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
                    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
                })
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item_dates, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return taskByDates.count()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.locationDate)
        val rv: RecyclerView = itemView.findViewById(R.id.locationList)
    }
}