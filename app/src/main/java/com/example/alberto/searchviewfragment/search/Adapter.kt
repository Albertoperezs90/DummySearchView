package com.example.alberto.searchviewfragment.search

import android.content.Context
import android.support.design.widget.CoordinatorLayout.Behavior.setTag
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.nio.file.Files.size
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import com.example.alberto.searchviewfragment.MainFragment
import com.example.alberto.searchviewfragment.R


/**
 * Created by alberto on 09/07/2018.
 */

class Adapter(var context: Context, var employeeArrayList: MutableList<String>, val fragment: MainFragment) : BaseAdapter(), Filterable {

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val oReturn = FilterResults()
                val results = mutableListOf<String>()
                if (orig == null)
                    orig = employeeArrayList
                if (constraint != null) {
                    if (orig != null && orig!!.size > 0) {
                        for (g in orig!!) {
                            if (g.toLowerCase()
                                            .contains(constraint.toString()))
                                results.add(g)
                        }
                    }
                    oReturn.values = results
                }
                return oReturn
            }

            override fun publishResults(constraint: CharSequence,
                                        results: FilterResults) {
                employeeArrayList = results.values as MutableList<String>
                notifyDataSetChanged()
            }
        }
    }

    var orig: MutableList<String>? = null


    inner class EmployeeHolder {
        internal var name: TextView? = null
        internal var age: TextView? = null
    }

    override fun notifyDataSetChanged() {
        super.notifyDataSetChanged()
    }


    override fun getCount(): Int {
        return employeeArrayList.size
    }

    override fun getItem(position: Int): Any {
        return employeeArrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: EmployeeHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row, parent, false)
            holder = EmployeeHolder()
            holder.name = convertView!!.findViewById(R.id.txtName)
            holder.age = convertView!!.findViewById(R.id.txtAge)
            convertView!!.setTag(holder)
        } else {
            holder = convertView.tag as EmployeeHolder
        }

        holder.name!!.setText(employeeArrayList[position])
        holder.age!!.setText(employeeArrayList[position])



        return convertView

    }

}