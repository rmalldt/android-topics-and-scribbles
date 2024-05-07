package com.rm.android_fundamentals.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.rm.android_fundamentals.R
import com.rm.android_fundamentals.base.model.NavDrawerSection
import com.rm.android_fundamentals.base.model.NavDrawerTopic

class CustomExpandableAdapter(
    private val context: Context,
    private val navDrawerData: List<NavDrawerTopic>
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        return navDrawerData.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return navDrawerData[groupPosition].sections.size
    }

    override fun getGroup(groupPosition: Int): NavDrawerTopic {
        return navDrawerData[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return navDrawerData[groupPosition].sections[childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var view: View? = convertView
        val topic = getGroup(groupPosition)

        if (view == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.recyclerview_item, null)
        }
        val topicTitle = view?.findViewById<TextView>(R.id.txtRvGrid)
        topicTitle?.text = topic.title
        return view!!
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var view: View? = convertView
        val section = getGroup(groupPosition).sections[childPosition]

        if (view == null) {
             view = LayoutInflater.from(parent?.context).inflate(R.layout.recyclerview_item, null)
        }
        val topicTitle = view?.findViewById<TextView>(R.id.txtRvGrid)
        topicTitle?.text = (section.data as NavDrawerSection).title
        return view!!
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}