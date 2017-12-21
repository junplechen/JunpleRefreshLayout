package com.junple.refresh;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junple on 2017/12/20.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> m_list;

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById( R.id.text);
        }
    }

    public  MyAdapter() {

        m_list = new ArrayList<>();
        for( int i=0; i<20; i++) {
            m_list.add("item"+(i+1));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.item_recy_test, parent,false);
        ViewHolder holder = new ViewHolder( view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textView.setText( m_list.get( position));
    }

    @Override
    public int getItemCount() {
        return m_list.size();
    }
}
