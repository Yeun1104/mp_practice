package com.example.mp0904

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrderAdapter(val fruits: ArrayList<Fruit>) :
    RecyclerView.Adapter<OrderAdapter.MyViewHolder>() {

    // 합계가 바뀌었다고 MainActivity에 알려주는 리스너
    interface OnSelectChangedListener {
        fun onSelectChanged(items: ArrayList<Fruit>)
    }
    private var selectChangedListener: OnSelectChangedListener? = null
    fun setOnSelectChangedListener(listener: OnSelectChangedListener) {
        selectChangedListener = listener
    }

    // ① ViewHolder: 한 줄 안의 위젯들을 미리 찾아서 들고 있는 상자
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val price: TextView = itemView.findViewById(R.id.price)
        val quantity: TextView = itemView.findViewById(R.id.quantity)
        val cost: TextView = itemView.findViewById(R.id.cost)
        val plus: ImageView = itemView.findViewById(R.id.plus)
        val minus: ImageView = itemView.findViewById(R.id.minus)
        val delete: ImageView = itemView.findViewById(R.id.delete_item)
    }

    // ② 줄 화면(ViewHolder) 만들기: 화면에 보일 만큼만 만들어지고 재사용됨
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(view)
    }

    // ③ position번째 데이터를 ViewHolder에 채워 넣기
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = fruits[position]
        holder.image.setImageResource(item.mImage)
        holder.price.text = item.mPrice.toString()
        holder.quantity.text = "x ${item.mQuantity}"
        holder.cost.text = (item.mPrice * item.mQuantity).toString()

        // + : 수량 1 증가
        holder.plus.setOnClickListener {
            item.mQuantity += 1
            notifyItemChanged(holder.bindingAdapterPosition)   // 이 줄만 다시 그림
            selectChangedListener?.onSelectChanged(fruits)
        }
        // - : 수량 1 감소 (0 아래로는 안 감)
        holder.minus.setOnClickListener {
            if (item.mQuantity > 0) item.mQuantity -= 1
            notifyItemChanged(holder.bindingAdapterPosition)
            selectChangedListener?.onSelectChanged(fruits)
        }
        // × : 이 줄 삭제
        holder.delete.setOnClickListener {
            val pos = holder.bindingAdapterPosition   // 지금 실제 위치
            if (pos != RecyclerView.NO_POSITION) {
                fruits.removeAt(pos)
                notifyItemRemoved(pos)                 // 삭제 애니메이션과 함께 제거
                selectChangedListener?.onSelectChanged(fruits)
            }
        }
    }

    // ④ 전체 줄 수
    override fun getItemCount(): Int = fruits.size
}