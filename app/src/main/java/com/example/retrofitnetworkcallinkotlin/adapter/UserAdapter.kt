package com.example.retrofitnetworkcallinkotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitnetworkcallinkotlin.R
import com.example.retrofitnetworkcallinkotlin.data.User
import com.facebook.shimmer.ShimmerFrameLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view.view.*
import java.util.ArrayList

class UserAdapter(val userList: List<User>, val context: Context): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

/*    private var isShimmer = true
    private var isShimmerNumber = 5*/

    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private lateinit var shimmerFrameLayout: ShimmerFrameLayout
        fun bindItem(user: User) {
         //   itemView.img_item.setImageResource(R.drawable.app_logo)
            Picasso.get().load(user.avatarUrl).into(itemView.img_item)
            itemView.img_title.text = user.login
            itemView.img_description.text = user.nodeId
        //    shimmerFrameLayout = itemView.findViewById(R.id.shimmerLayout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int){
        holder.bindItem(userList[position])

        holder.itemView.setOnClickListener {
            var itemPosition = userList[position]
            Toast.makeText(context, "You Clicked "+ itemPosition.login, Toast.LENGTH_LONG).show()
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}