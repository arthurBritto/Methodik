package com.arthurbritto.methodik.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.arthurbritto.methodik.R;
import com.arthurbritto.methodik.model.Panel;
import java.util.List;

/**
 * Adapter for the RecyclerView that displays a Panel of lists.
 */

public class PanelListAdapter extends RecyclerView.Adapter<PanelListAdapter.PanelViewHolder> {

    private final LayoutInflater mInflater;
    private List<Panel> lists; // Cached copy of lists
    private static ClickListener clickListener;

    PanelListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public PanelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new PanelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PanelViewHolder holder, int position) {
        if (lists != null) {
            Panel current = lists.get(position);
            holder.panelItemView.setText(current.getListName());
        } else {
            // Covers the case of data not being ready yet.
            holder.panelItemView.setText(R.string.no_word);
        }
    }

    /**
     * Associates a panel of lists with this adapter
     */
    void setPanelLists(List<Panel> panelLists) {
        lists = panelLists;
        notifyDataSetChanged();
    }

    /**
     * getItemCount() is called many times, and when it is first called,
     * mWords has not been updated (means initially, it's null, and we can't return null).
     */
    @Override
    public int getItemCount() {
        if (lists != null)
            return lists.size();
        else return 0;
    }

    /**
     * Gets the word at a given position.
     * This method is useful for identifying which word
     * was clicked or swiped in methods that handle user events.
     *
     * @param position The position of the word in the RecyclerView
     * @return The word at the given position
     */
    public Panel getPanelListAtPosition(int position) {
        return lists.get(position);
    }

    class PanelViewHolder extends RecyclerView.ViewHolder {
        private final TextView panelItemView;

        private PanelViewHolder(View itemView) {
            super(itemView);
            panelItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        PanelListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }

}
