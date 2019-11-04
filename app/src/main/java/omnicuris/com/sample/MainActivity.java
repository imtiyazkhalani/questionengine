package omnicuris.com.sample;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import omnicuris.com.sample.Fragment.QuestionFragment;
import omnicuris.com.sample.model.Question;
import omnicuris.com.sample.model.gsonhelper.QuestionListModel;
import omnicuris.com.sample.util.FileUtils;
import omnicuris.com.sample.util.PermissionUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final String QUESTION_FILE_NAME = "question.json";
    boolean doubleBackToExitPressedOnce = false;
    private ViewPager mViewPager;
    private ProgressBar mProgressBar;
    private List<Question> mQuestionList;
    private QuestionAdapter questionAdapter;
    private ImageView btnNext, btnPrevious;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.btn_finish_test).setOnClickListener(this);

        mQuestionList = new ArrayList<>();
        questionAdapter = new QuestionAdapter(getSupportFragmentManager());

        mProgressBar = findViewById(R.id.pb_question);

        mViewPager = findViewById(R.id.myViewPager);
        mViewPager.setAdapter(questionAdapter);


        btnNext = (ImageView) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);
        btnPrevious = (ImageView) findViewById(R.id.btn_previous);
        btnPrevious.setOnClickListener(this);


        if (PermissionUtil.hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            new ReadQuestionTask().execute();
        }
        else
        {
            PermissionUtil.checkPermissions(MainActivity.this, new PermissionUtil.PermissionsCallback()
            {
                @Override
                public void result(boolean result, boolean rationale)
                {
                    new ReadQuestionTask().execute();
                }
            }, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (requestCode == 101)
        {
            PermissionUtil.checkPermissions(MainActivity.this, new PermissionUtil.PermissionsCallback()
            {
                @Override
                public void result(boolean result, boolean rationale)
                {
                    new ReadQuestionTask().execute();
                }
            }, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_next:
            case R.id.btn_previous:
                //if(mViewPager.getCurrentItem()+1 == mQuestionList.size()) // never comes across this condition
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + (v.getId() == R.id.btn_next ? 1 : -1));
                break;

            case R.id.btn_finish_test:
                AlertDialog.Builder mBuilder = new AlertDialog.Builder();
                mBuilder.setTitle("Finish Test");
                mBuilder.setMessage("Are you sure want to finish test?");
                mBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                        intent.putParcelableArrayListExtra("data", questionAdapter);
                        startActivity(intent);
                        finish();
                    }
                });

                mBuilder.setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });

                mBuilder.show()

                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        if (doubleBackToExitPressedOnce)
        {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.exit_message, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable()
        {

            @Override
            public void run()
            {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private void showProgress(boolean progress)
    {
        mViewPager.setVisibility(progress ? View.GONE : View.VISIBLE);
        mProgressBar.setVisibility(progress ? View.VISIBLE : View.GONE);
    }

    private class QuestionAdapter extends FragmentStatePagerAdapter
    {
        public QuestionAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            return QuestionFragment.newInstance(mQuestionList.get(position), position, mQuestionList.size());
        }

        @Override
        public int getCount()
        {
            return mQuestionList != null ? mQuestionList.size() : 0;
        }
    }

    class ReadQuestionTask extends AsyncTask<Void, Void, Void>
    {

        protected void onPreExecute()
        {
            showProgress(true);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            Gson gson = new Gson();
            QuestionListModel model = gson.fromJson(FileUtils.readTextFile(Environment.getExternalStorageDirectory() + File.separator + QUESTION_FILE_NAME), QuestionListModel.class);
            mQuestionList.addAll(model.getmQuestionList());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            showProgress(false);
            questionAdapter.notifyDataSetChanged();
        }


    }

}
