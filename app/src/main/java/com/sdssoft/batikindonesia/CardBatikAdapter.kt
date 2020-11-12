package com.sdssoft.batikindonesia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_baik.view.*
import java.util.ArrayList

class CardBatikAdapter : RecyclerView.Adapter<CardBatikAdapter.CardBatikViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null
    private val mData = ArrayList<Batik>()
    fun setData(items: ArrayList<Batik>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class CardBatikViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(batik: Batik) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(batik.link_batik)
                    .apply(RequestOptions())
                    .into(img_batik)
                txt_nama_batik.text = batik.nama_batik
                txt_asal_batik.text = batik.daerah_batik
                setOnClickListener { onItemClickCallback?.onItemClicked(batik) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardBatikViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_baik, parent, false)
        return CardBatikViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardBatikViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Batik)
    }
}