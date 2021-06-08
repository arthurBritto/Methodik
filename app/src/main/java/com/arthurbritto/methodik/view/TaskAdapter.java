package com.arthurbritto.methodik.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.arthurbritto.methodik.R;
import com.arthurbritto.methodik.model.Task;

import java.util.List;

/**
 * Adapter for the RecyclerView that displays a tasks of Tasklists.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final LayoutInflater inflater;
    private List<Task> tasks; // Cached copy of tasks
    private static ClickListener clickListener;

    TaskAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        if (tasks != null) {
            Task current = tasks.get(position);
            holder.taskItemView.setText(current.getName());
        } else {
            // Covers the case of data not being ready yet.
            holder.taskItemView.setText(R.string.no_task);
        }
    }

    /**
     * Associates a task of Tasklist with this adapter
     */
    void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    /**
     * getItemCount() is called many times, and when it is first called,
     * tasks has not been updated (means initially, it's null, and we can't return null).
     */
    @Override
    public int getItemCount() {
        if (tasks != null)
            return tasks.size();
        else return 0;
    }

    /**
     * Gets the task at a given position.
     * This method is useful for identifying which task
     * was clicked or swiped in methods that handle user events.
     *
     * @param position The position of the task in the RecyclerView
     * @return The task at the given position
     */
    public Task getTaskAtPosition(int position) {
        return tasks.get(position);
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView taskItemView;

        private TaskViewHolder(View itemView) {
            super(itemView);
            taskItemView = itemView.findViewById(R.id.textView);
            itemView.setOnLongClickListener(view -> {
                clickListener.onItemLongClick(view, getAdapterPosition());
                return true;
            });
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        TaskAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemLongClick(View v, int position);
    }

}
