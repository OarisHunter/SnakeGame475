package psu.pqt5055.snake;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScoresFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoresFragment extends Fragment {

    private ScoresFragment scoresFragment;
    private Context context;
    private View parentView;
    private SnakeDatabase mSnakeDb;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ScoresFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScoresFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScoresFragment newInstance(String param1, String param2) {
        ScoresFragment fragment = new ScoresFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_scores, container, false);

        scoresFragment = this;
        context = getActivity();
        mSnakeDb = SnakeDatabase.getInstance(context);

        View.OnClickListener onClickListener = itemView -> {
//            int selectedScoreId = (int) itemView.getTag();
        };

        RecyclerView recyclerView = parentView.findViewById(R.id.score_list);
        List<Score> scores = mSnakeDb.scoreDAO().getScores();
        recyclerView.setAdapter(new ScoreAdapter(scores, onClickListener));

        return parentView;
    }

    private class ScoreAdapter extends RecyclerView.Adapter<ScoreHolder> {

        private final List<Score> mScores;
        private final View.OnClickListener mOnClickListener;

        public ScoreAdapter(List<Score> scores, View.OnClickListener onClickListener) {
            mScores = scores;
            mOnClickListener = onClickListener;
        }

        @NonNull
        @Override
        public ScoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ScoreHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ScoreHolder holder, int position) {
            Score score = mScores.get(position);
            holder.bind(score);
            holder.itemView.setTag(score.getMId());
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mScores.size();
        }
    }

    private static class ScoreHolder extends RecyclerView.ViewHolder {

        private final TextView mNameTextView;

        public ScoreHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.score_list_item, parent, false));
            mNameTextView = itemView.findViewById(R.id.score_item);
        }

        public void bind(Score score) {
            mNameTextView.setText(Long.toString(score.getMScore()));
        }
    }
}