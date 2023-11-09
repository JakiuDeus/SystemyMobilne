package me.jorlowski;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TaskListFragment extends Fragment {
    public static final String KEY_EXTRA_TASK_ID = "keyExtraTaskId";
    private static final String KEY_SUBTITLE_SET = "keySubtitleSet";
    private View view;
    private TaskAdapter adapter;
    private RecyclerView recyclerView;
    private boolean subtitleVisible;

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_task_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (subtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.new_task) {
            Task task = new Task();
            TaskStorage.getInstance().addTask(task);
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra(TaskListFragment.KEY_EXTRA_TASK_ID, task.getId());
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.show_subtitle) {
            subtitleVisible = !subtitleVisible;
            getActivity().invalidateOptionsMenu();
            updateSubtitle();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        view = getView();
        recyclerView = view.findViewById(R.id.task_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_SUBTITLE_SET, subtitleVisible);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState != null) {
            subtitleVisible = savedInstanceState.getBoolean(KEY_SUBTITLE_SET);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_list, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView taskItemName;
        private TextView taskItemDate;
        private ImageView iconImageView;
        private CheckBox taskItemDone;
        private Task task;

        public TaskHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_task, parent, false));
            itemView.setOnClickListener(this);

            taskItemName = itemView.findViewById(R.id.task_item_name);
            taskItemDate = itemView.findViewById(R.id.task_item_date);
            iconImageView = itemView.findViewById(R.id.icon_image_view);
            taskItemDone = itemView.findViewById(R.id.task_item_done);
        }

        public void bind(Task task) {
            this.task = task;
            taskItemName.setText(task.getName());
            Locale locale = new Locale("pl", "PL");
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd.MM.yyyy", locale);
            taskItemDate.setText(dateFormat.format(task.getDate()));
            if (task.getCategory().equals(Category.HOME)) {
                iconImageView.setImageResource(R.drawable.ic_house);
            } else {
                iconImageView.setImageResource(R.drawable.ic_school);
            }

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra(KEY_EXTRA_TASK_ID, task.getId());
            startActivity(intent);
        }

        public CheckBox getCheckBox() {
            return taskItemDone;
        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        private List<Task> tasks;
        public TaskAdapter(List<Task> tasks) {
            this.tasks = tasks;
        }
        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TaskHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            Task task = tasks.get(position);
            holder.bind(task);
            CheckBox checkBox = holder.getCheckBox();
            boolean isDone = tasks.get(position).isDone();
            TextView taskItemName = holder.taskItemName;
            checkBox.setChecked(isDone);
            if (isDone) {
                taskItemName.setPaintFlags(taskItemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            checkBox.setOnCheckedChangeListener(((buttonView, isChecked) -> {
                tasks.get(holder.getBindingAdapterPosition()).setDone(isChecked);
                if (isChecked) {
                   taskItemName.setPaintFlags(taskItemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    taskItemName.setPaintFlags(taskItemName.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                }
                updateSubtitle();
            }));
        }

        @Override
        public int getItemCount() {
            return tasks.size();
        }
    }

    private void updateView() {
        TaskStorage taskStorage = TaskStorage.getInstance();
        List<Task> tasks = taskStorage.getTasks();

        if (adapter == null) {
            adapter = new TaskAdapter(tasks);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        updateSubtitle();
    }

    public void updateSubtitle() {
        TaskStorage taskStorage = TaskStorage.getInstance();
        List<Task> tasks = taskStorage.getTasks();
        int todoTaskCount = 0;
        for (Task task : tasks) {
            if (!task.isDone()) {
                todoTaskCount++;
            }
        }
        String subtitle = getString(R.string.subtitle_format, todoTaskCount);
        if (!subtitleVisible) {
            subtitle = null;
        }
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.getSupportActionBar().setSubtitle(subtitle);
    }
}
