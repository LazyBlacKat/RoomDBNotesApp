package com.example.roomdbnotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class NoteRVAdapter extends ListAdapter<NoteModal, NoteRVAdapter.ViewHolder> {

    private OnItemClickListener listener;

    NoteRVAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<NoteModal> DIFF_CALLBACK = new DiffUtil.ItemCallback<NoteModal>() {
        @Override
        public boolean areItemsTheSame(NoteModal oldItem, NoteModal newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(NoteModal oldItem, NoteModal newItem) {
            return oldItem.getNoteTitle().equals(newItem.getNoteTitle()) &&
                    oldItem.getNoteDescription().equals(newItem.getNoteDescription());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_rv_item, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteModal model = getNoteAt(position);
        holder.noteTitleTV.setText(model.getNoteTitle());
        holder.noteDescTV.setText(model.getNoteDescription());
    }

    public NoteModal getNoteAt(int position) {
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitleTV, noteDescTV;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitleTV = itemView.findViewById(R.id.idTVNoteTitle);
            noteDescTV = itemView.findViewById(R.id.idTVNote);

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

    public interface OnItemClickListener {
        void onItemClick(NoteModal model);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
