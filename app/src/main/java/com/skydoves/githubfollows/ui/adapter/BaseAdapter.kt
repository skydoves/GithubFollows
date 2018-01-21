package com.skydoves.githubfollows.ui.adapter

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.githubfollows.ui.viewholder.BaseViewHolder
import java.util.*

/**
 * Developed by skydoves on 2018-01-21.
 * Copyright (c) 2018 skydoves rights reserved.
 */

abstract class BaseAdapter : RecyclerView.Adapter<BaseViewHolder>() {
    val sections = ArrayList<MutableList<Any>>()

    fun sections(): MutableList<MutableList<Any>> {
        return sections
    }

    fun clearSections() {
        sections.clear()
    }

    fun <T> addSection(section: List<T>) {
        sections.add(ArrayList<Any>(section))
    }

    fun <T> addSections(sections: List<List<T>>) {
        for (section in sections) {
            addSection(section)
        }
    }

    fun <T> setSection(location: Int, section: List<T>) {
        sections[location] = ArrayList<Any>(section)
    }

    fun <T> insertSection(location: Int, section: List<T>) {
        sections.add(location, ArrayList<Any>(section))
    }

    fun <T> removeSection(location: Int) {
        sections.removeAt(location)
    }

    fun <T> sectionOrderChange(location: Int) {
        Collections.reverse(sections[location])
    }

    /**
     * Fetch the layout id associated with a sectionRow.
     */
    protected abstract fun layout(sectionRow: SectionRow): Int

    /**
     * Returns a new KSViewHolder given a layout and view.
     */
    protected abstract fun viewHolder(@LayoutRes layout: Int, view: View): BaseViewHolder

    override fun onViewDetachedFromWindow(holder: BaseViewHolder) {
        super.onViewDetachedFromWindow(holder)
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        super.onViewAttachedToWindow(holder)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, @LayoutRes layout: Int): BaseViewHolder {
        val view = inflateView(viewGroup, layout)
        return viewHolder(layout, view)
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        val data = objectFromPosition(position)

        try {
            viewHolder.bindData(data)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemViewType(position: Int): Int {
        return layout(sectionRowFromPosition(position))
    }

    override fun getItemCount(): Int {
        var itemCount = 0
        for (section in sections) {
            itemCount += section.size
        }

        return itemCount
    }

    /**
     * Gets the data object associated with a sectionRow.
     */
    protected fun objectFromSectionRow(sectionRow: SectionRow): Any {
        return sections[sectionRow.section()][sectionRow.row()]
    }

    protected fun sectionCount(section: Int): Int {
        return if (section > sections().size - 1) {
            0
        } else sections()[section].size
    }

    /**
     * Gets the data object associated with a position.
     */
    protected fun objectFromPosition(position: Int): Any {
        return objectFromSectionRow(sectionRowFromPosition(position))
    }

    private fun sectionRowFromPosition(position: Int): SectionRow {
        val sectionRow = SectionRow()
        var cursor = 0
        for (section in sections) {
            for (item in section) {
                if (cursor == position) {
                    return sectionRow
                }
                cursor++
                sectionRow.nextRow()
            }
            sectionRow.nextSection()
        }

        throw RuntimeException("Position $position not found in sections")
    }

    private fun inflateView(viewGroup: ViewGroup, @LayoutRes viewType: Int): View {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return layoutInflater.inflate(viewType, viewGroup, false)
    }

    /**
     * SectionRows allow RecyclerViews to be structured into sections of rows.
     */
    protected inner class SectionRow {
        private var section: Int = 0
        private var row: Int = 0

        constructor() {
            section = 0
            row = 0
        }

        constructor(section: Int, row: Int) {
            this.section = section
            this.row = row
        }

        fun section(): Int {
            return section
        }

        fun row(): Int {
            return row
        }

        fun nextRow() {
            row++
        }

        fun nextSection() {
            section++
            row = 0
        }
    }
}