package com.bnctech.myapplication3;

import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This is an example usage of the AnimatedExpandableListView class.
 *
 * It is an activity that holds a listview which is populated with 100 groups
 * where each group has from 1 to 100 children (so the first group will have one
 * child, the second will have two children and so on...).
 */
public class MainActivity extends AppCompatActivity {
    private AnimatedExpandableListView listView;
    private ExampleAdapter adapter;
    RecyclerAdapter recyclerAdapter;
    ArrayList<Item> childItem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        List<GroupItem> items = new ArrayList<GroupItem>();

        // Populate our list with groups and it's children
        for(int i = 1; i < 100; i++) {
            GroupItem item = new GroupItem();

            item.title = "Group " + i;

            for(int j = 0; j < i; j++) {
                ChildItem child = new ChildItem();
                child.title = "Awesome item " + j;
                child.hint = "Too awesome";

//                childItem.add(new Item(child.title,child.hint));

                item.items.add(child);
            }

            items.add(item);
        }

//        recyclerAdapter = new RecyclerAdapter(this, childItem);

        adapter = new ExampleAdapter(this);
        adapter.setData(items); /*items는 List<GroupItem>의 크기가 100인 콜렉션*/

        listView = (AnimatedExpandableListView) findViewById(R.id.listView);
        listView.setAdapter((ExpandableListAdapter) adapter);

        // In order to show animations, we need to use a custom click handler
        // for our ExpandableListView.
        listView.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.e("groupPosition",groupPosition+"");    /*todo delete*/
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group
                // expansion/collapse.
                if (listView.isGroupExpanded(groupPosition)) {
                    listView.collapseGroupWithAnimation(groupPosition);
                } else {
                    listView.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }

        });
    }

    private static class GroupItem {
        String title;
        List<ChildItem> items;
    }

    private static class ChildItem {
        String title;
        String hint;
    }

    private static class ChildHolder {
//        TextView title;
//        TextView hint;
        TextView beforePrice;
        TextView afterPrice;
    }

    private static class GroupHolder {
        TextView title;
    }

    /**
     * Adapter for our list of {@link GroupItem}s.
     */
    private class ExampleAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
        private LayoutInflater inflater;

        private List<GroupItem> items;

        RecyclerView recyclerView;

        public ExampleAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void setData(List<GroupItem> items) {
            this.items = items;
        }

        @Override
        public ChildItem getChild(int groupPosition, int childPosition) {
            return items.get(groupPosition).items.get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder holder;
            ChildItem item = getChild(groupPosition, childPosition);
            if (convertView == null) {
                holder = new ChildHolder();
//                convertView = inflater.inflate(R.layout.list_item, parent, false);
                convertView = inflater.inflate(R.layout.activity_child,parent,false);
                recyclerView = convertView.findViewById(R.id.recyclerView);
                recyclerView.setAdapter(recyclerAdapter);

                LinearLayoutManager mManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(mManager);

                holder.beforePrice = convertView.findViewById(R.id.child_item_beforePrice);
                holder.afterPrice = convertView.findViewById(R.id.child_item_afterPrice);
//                holder.title = (TextView) convertView.findViewById(R.id.textTitle);
//                holder.hint = (TextView) convertView.findViewById(R.id.textHint);
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder) convertView.getTag();
            }

//            holder.title.setText(item.title);
//            holder.hint.setText(item.hint);

            return convertView;
        }

        @Override
        public int getRealChildrenCount(int groupPosition) {
            return items.get(groupPosition).items.size();
        }

        @Override
        public GroupItem getGroup(int groupPosition) {
            return items.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return items.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHolder holder;
            GroupItem item = getGroup(groupPosition);
            if (convertView == null) {
                holder = new GroupHolder();
                convertView = inflater.inflate(R.layout.group_item, parent, false);
                holder.title = (TextView) convertView.findViewById(R.id.textTitle);
                convertView.setTag(holder);
            } else {
                holder = (GroupHolder) convertView.getTag();
            }

            holder.title.setText(item.title);

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int arg0, int arg1) {
            return true;
        }

    }

}