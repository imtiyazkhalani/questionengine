package omnicuris.com.sample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import omnicuris.com.sample.model.Question;

public class ResultActivity extends androidx.appcompat.app.AppCompatActivity
{

    private TextView tvResult;
    private ProgressBar progressBar;
    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        questionList = getIntent().getParcelableArrayListExtra("data");

        tvResult = (TextView) findViewById(R.id.tv_result_text);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        showProgress(true);
    }

    private void showProgress(boolean progress)
    {
        tvResult.setVisibility(progress ? View.GONE : View.VISIBLE);
        tvResult.setVisibility(progress ? View.VISIBLE : View.GONE);
    }

    private class ResultTask extends AsyncTask<Void, Void, Void>
    {
        int right, attemped;

        @Override
        protected Void doInBackground(Void... voids)
        {
            for (Question question : questionList)
            {
                if (question.isRightAnswer())
                    right++;

                if (question.isAttempted())
                    attemped++;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            showProgress(false);
            super.onPostExecute(aVoid);
            String result = "Total : " + questionList.size() + "\n" + "Right : " + right + "\n" + "Attempted : " + attemped + "\n";
            tvResult.setText(result);

        }
    }
}
