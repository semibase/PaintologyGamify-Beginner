package com.paintology.lite.total.beginner.Youtube.ui.menu.defaultMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.paintology.lite.total.beginner.R;
import com.paintology.lite.total.beginner.Youtube.ui.menu.MenuItem;

import java.util.List;

class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    @NonNull
    private final Context context;
    @NonNull
    private final List<MenuItem> menuItems;

    MenuAdapter(@NonNull Context context, @NonNull List<MenuItem> menuItems) {
        this.context = context;
        this.menuItems = menuItems;
    }

    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.root.setOnClickListener(menuItems.get(position).getOnClickListener());
        holder.textView.setText(menuItems.get(position).getText());
        holder.textView.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, menuItems.get(position).getIcon()), null, null, null);
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View root;
        final TextView textView;

        ViewHolder(View menuItemView) {
            super(menuItemView);
            root = menuItemView;
            textView = menuItemView.findViewById(R.id.text);
        }
    }
}