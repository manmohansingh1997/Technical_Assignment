package com.sonuproject.pago_assignment.Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sonuproject.pago_assignment.Adapter.DashboardRecyclerAdapter
import com.sonuproject.pago_assignment.R
import com.sonuproject.pago_assignment.model.Pagodata


class DashboardFragment : Fragment() {

    lateinit var recyclerDashboard: RecyclerView

    lateinit var layoutManager: RecyclerView.LayoutManager


    lateinit var recyclerAdapter: DashboardRecyclerAdapter
    val pagoInfoList = arrayListOf<Pagodata>(

        Pagodata(
            "2017", "02", "Dec", "Order", " #3480",
            "Shajed Evan", "evenshajed@gmail.com", "Total", "$154.00", "Processing"
        )
        ,
        Pagodata(
            "2017", "16", "Dec", "Order", " #3480",
            "Tom Moodi", "tom@tomdesign.com", "Total", "$204.00", "On Hold"
        )
        ,
        Pagodata(
            "2017", "25", "Dec", "Order", " #3480",
            "Shajed Evan", "evenshajed@gmail.com", "Total", "$154.00", "Processing"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)

        layoutManager = LinearLayoutManager(activity)
        recyclerAdapter =
            DashboardRecyclerAdapter(
                activity as Context,
                pagoInfoList
            )
        recyclerDashboard.adapter = recyclerAdapter
        recyclerDashboard.layoutManager = layoutManager

        return view


    }


}
