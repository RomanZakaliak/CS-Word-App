package com.example.CSWordApp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.CSWordApp.R;

import java.util.List;
import java.util.Map;

/**
 * Created by mattr on 7/5/2017.
 */

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    // Declare context object
    private final Context mContext;
    private final List<String> mParents;
    private final Map<String, List<String>> mChildrenMap;

    /**
     * Constructor
     * @param context - Target activity context
     * @param parents - List of Parents (parents)
     * @param childrenMap - Map populated with our values and their associations (childrenMap)
     */
    public MyExpandableListAdapter(Context context, List<String> parents, Map<String, List<String>> childrenMap) {
        this.mContext = context;
        this.mParents = parents;
        this.mChildrenMap = childrenMap;
    }

    /**
     * @return - the number of "parents" or groups
     */
    @Override
    public int getGroupCount() {
        return mParents.size();
    }

    /**
     * @param groupPosition - the position we are in the map, each map position may have a different count
     * @return - the number of children in each group (under each parent)
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildrenMap.get(mParents.get(groupPosition)).size();
    }

    /**
     * @param groupPosition - group index
     * @return - for any specific position, return the group name
     */
    @Override
    public Object getGroup(int groupPosition) {
        return mParents.get(groupPosition);
    }

    /**
     * @param groupPosition - target group index
     * @param childPosition - target child index
     * @return - the name of the child (cycles through each parent (or group), and each child of each parent
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildrenMap.get(mParents.get(groupPosition)).get(childPosition);
    }

    /**
     * @param groupPosition - target group index
     * @return the groupPosition as the ID
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * @param groupPosition
     * @param childPosition
     * @return the childPosition as the ID
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    // not touched
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Get the value of the group item (parent), apply to the TextView using LayoutInflater
     * @param groupPosition
     * @param isExpanded
     * @param convertView // recycles old views in Adapters to increase performance
     * @param parent
     * @return the actual value
     **/
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        // Get the value of the parent item
        String parentValue = (String) getGroup(groupPosition);

        // Create the convertView object if it is null
        if(convertView == null) {
            // Inflate the view in our list_parent.xml
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_parent, null);
        }

        // Update the TextView in our convertView (based on the list_parent.xml)
        TextView parentTextView = convertView.findViewById(R.id.list_item_parent);
        parentTextView.setText(parentValue);

        return convertView;
    }

    /**
     * Update and return the value of convertView
     * @param groupPosition
     * @param childPosition
     * @param isLastChild - is target element is last child
     * @param convertView // recycles old views in Adapters to increase performance
     * @param parent
     * @return
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        // get the value of the child item, in the parent (group)
        String childValue = (String) getChild(groupPosition, childPosition);

        // Create the convertView object if it is null
        if(convertView == null) {
            // Inflate the view in our list_parent.xml
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_child, null);
        }

        // Update the TextView in our convertView (based on the list_child.xml)
        TextView childTextView = convertView.findViewById(R.id.list_item_child);
        childTextView.setText(childValue);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true; // true - we want the child element to be selectable
    }
}