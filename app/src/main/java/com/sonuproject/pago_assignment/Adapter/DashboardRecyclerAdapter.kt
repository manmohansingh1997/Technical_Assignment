package com.sonuproject.pago_assignment.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sonuproject.pago_assignment.R
import com.sonuproject.pago_assignment.model.Pagodata

class DashboardRecyclerAdapter(val context: Context, val itemList: ArrayList<Pagodata>) :
    RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_dashboard_single_row, parent, false
        )
        return DashboardViewHolder(
            view
        )
    }

    class DashboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textViewOrderYear: TextView = view.findViewById(R.id.textViewOrderYear)
        val textViewOrderDate: TextView = view.findViewById(R.id.textViewOrderDate)
        val textViewOrderMonth: TextView = view.findViewById(R.id.textViewOrderMonth)
        val textViewOrderNumber: TextView = view.findViewById(R.id.textViewOrderNumber)
        val textViewCustomerName: TextView = view.findViewById(R.id.textViewCustomerName)
        val textViewCustomerEmail: TextView = view.findViewById(R.id.textViewCustomerEmail)
        val textViewTotal: TextView = view.findViewById(R.id.textViewTotal)
        val textViewOrderStatus: TextView = view.findViewById(R.id.textViewOrderStatus)
    }


    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {


        val pagodata1 = itemList[position]

        holder.textViewOrderStatus.setTextColor(
            if (pagodata1.status == "On Hold") ContextCompat.getColor(
                context,
                android.R.color.holo_red_dark
            ) else ContextCompat.getColor(context, android.R.color.black)
        )



        holder.textViewOrderYear.text = pagodata1.yearof
        holder.textViewOrderDate.text = pagodata1.dateof
        holder.textViewOrderMonth.text = pagodata1.monthof
        holder.textViewOrderNumber.text = pagodata1.orderseq
        holder.textViewCustomerName.text = pagodata1.nameof
        holder.textViewCustomerEmail.text = pagodata1.mail
        holder.textViewTotal.text = pagodata1.rs
        holder.textViewOrderStatus.text = pagodata1.status

    }
}