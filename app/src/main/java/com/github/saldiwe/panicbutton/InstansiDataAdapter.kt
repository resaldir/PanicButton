package com.github.saldiwe.panicbutton

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.saldiwe.panicbutton.base.BaseAppAdapter
import com.github.saldiwe.panicbutton.base.BaseViewHolder
import com.github.saldiwe.panicbutton.data.Instansi
import kotlinx.android.synthetic.main.view_data.view.*
import org.jetbrains.anko.startActivity

class InstansiDataAdapter(private val dataItemList: ArrayList<Instansi>) : BaseAppAdapter() {

    override fun getItemCount(): Int = dataItemList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        InstansiDataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_data, parent, false))

    fun clearItems() {
        dataItemList.clear()
        notifyDataSetChanged()
    }

    fun setInstansiData(instansiData: List<Instansi>) {
        dataItemList.addAll(instansiData)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.clear()
        holder.onBind(position)
    }

    inner class InstansiDataViewHolder(itemView: View): BaseViewHolder(itemView) {
        override fun clear() {
            itemView.img_icon.setImageDrawable(null)
            itemView.tv_nama.text = ""
            itemView.tv_nomor.text = ""
        }

        override fun onBind(position: Int) {
            inflateData(dataItemList[position])
        }

        private fun inflateData(instansi: Instansi) {

            instansi.icon?.let {
                itemView.img_icon.setImageResource(it)
            }
            itemView.tv_nama.text = instansi.namaInstansi
            itemView.tv_nomor.text = instansi.nomorInstansi

            itemView.btn_call.setOnClickListener {
                callInstansiNumber(instansi.nomorInstansi)
            }
        }

        private fun callInstansiNumber(instansiNumber: String) {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$instansiNumber")
            itemView.context.startActivity(intent)
        }

    }

}