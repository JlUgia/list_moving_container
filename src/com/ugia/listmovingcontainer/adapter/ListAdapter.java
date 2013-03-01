/*
 * Copyright 2013 Jose L Ugia - @jl_ugia
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ugia.listmovingcontainer.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ugia.listmovingcontainer.R;

public class ListAdapter extends BaseAdapter {

    class ViewHolder {
        TextView label;

        public ViewHolder(View view) {
            label = (TextView) view.findViewById(R.id.LabelText);
        }
    }

    private final Context mContext;
    private final LayoutInflater mInflater;
    private final List<String> items;

    public ListAdapter(Context context, List<String> _items) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        items = _items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public String getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null || !(convertView.getTag() instanceof ViewHolder)) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.label.setText(getItem(position));

        return convertView;
    }
}
