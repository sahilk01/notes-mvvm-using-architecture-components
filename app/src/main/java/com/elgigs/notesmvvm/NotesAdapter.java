package com.elgigs.notesmvvm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends ListAdapter<NotesEntity, NotesAdapter.NotesHolder> {
    private ItemClickListener listener;

    public NotesAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<NotesEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<NotesEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull NotesEntity oldItem, @NonNull NotesEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull NotesEntity oldItem, @NonNull NotesEntity newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };

    @NonNull
    @Override
    public NotesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notes, parent, false);
        return new NotesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesHolder holder, int position) {
        NotesEntity currentNote = getItem(position);
        holder.noteTitle.setText(currentNote.getTitle());
        holder.noteDescription.setText(currentNote.getDescription());
        holder.notePriority.setText(String.valueOf(currentNote.getPriority()));
    }


    public NotesEntity getNoteAt(int position) {
        return getItem(position);
    }


    class NotesHolder extends RecyclerView.ViewHolder {
        private TextView noteTitle, noteDescription, notePriority;

        public NotesHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.tv_title);
            noteDescription = itemView.findViewById(R.id.tv_description);
            notePriority = itemView.findViewById(R.id.tv_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }
    public interface ItemClickListener {
        void onItemClick(NotesEntity notesEntity);
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }
}
