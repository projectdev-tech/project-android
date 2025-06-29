package com.project.projectnew.ordercreation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.project.projectnew.R;
import java.util.List;

public class SuggestionsAdapter extends RecyclerView.Adapter<SuggestionsAdapter.ViewHolder> {

    private List<String> suggestions;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String suggestion);
    }

    public SuggestionsAdapter(List<String> suggestions, OnItemClickListener listener) {
        this.suggestions = suggestions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_suggestion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String suggestion = suggestions.get(position);
        holder.bind(suggestion, listener);
    }

    @Override
    public int getItemCount() {
        return suggestions.size();
    }

    // Metode untuk memperbarui data di adapter
    public void updateData(List<String> newSuggestions) {
        this.suggestions.clear();
        this.suggestions.addAll(newSuggestions);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSuggestion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSuggestion = itemView.findViewById(R.id.tvSuggestion);
        }

        public void bind(final String suggestion, final OnItemClickListener listener) {
            tvSuggestion.setText(suggestion);
            itemView.setOnClickListener(v -> listener.onItemClick(suggestion));
        }
    }
}