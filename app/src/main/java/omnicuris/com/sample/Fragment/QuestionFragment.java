package omnicuris.com.sample.Fragment;


import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import omnicuris.com.sample.R;
import omnicuris.com.sample.model.Question;

public class QuestionFragment extends BaseFragment
{

    private TextView mTvQuestion;
    private RadioGroup mRgOptions;
    private Question mQuestion;
    private TextView tv_question_lable;
    private RadioButton rbtnOp1, rbtnOp2, rbtnOp3, rbtnOp4;



    public static QuestionFragment newInstance(Question mQuestion,int pos,int totalQuetions)
    {
        QuestionFragment questionFragment = new QuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", mQuestion);
        bundle.putInt("position",pos);
        bundle.putInt("totalquetions",totalQuetions);
        questionFragment.setArguments(bundle);
        return questionFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mainView = inflater.inflate(R.layout.question_fragment, null);
        mQuestion = getArguments().getParcelable("data");
        initViews();
        return mainView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        mTvQuestion.setText(getHtmlText(mQuestion.getQuestion()));

        if (mQuestion.getOptions() != null && mQuestion.getOptions().size() != 0)
        {
            switch (mQuestion.getOptions().size())
            {
                case 1:
                    rbtnOp1.setText(mQuestion.getOptions().get(0));
                    rbtnOp2.setVisibility(View.GONE);
                    rbtnOp3.setVisibility(View.GONE);
                    rbtnOp4.setVisibility(View.GONE);
                    break;

                case 2:
                    rbtnOp1.setText(mQuestion.getOptions().get(0));
                    rbtnOp2.setText(mQuestion.getOptions().get(1));
                    rbtnOp3.setVisibility(View.GONE);
                    rbtnOp4.setVisibility(View.GONE);
                    break;

                case 3:
                    rbtnOp1.setText(mQuestion.getOptions().get(0));
                    rbtnOp2.setText(mQuestion.getOptions().get(1));
                    rbtnOp3.setText(mQuestion.getOptions().get(2));
                    rbtnOp4.setVisibility(View.GONE);
                    break;

                case 4:
                    rbtnOp1.setText(mQuestion.getOptions().get(0));
                    rbtnOp2.setText(mQuestion.getOptions().get(1));
                    rbtnOp3.setText(mQuestion.getOptions().get(2));
                    rbtnOp4.setText(mQuestion.getOptions().get(3));
                    break;
            }

        }
        else
        {
            Toast.makeText(getActivity(), R.string.no_options_question_text, Toast.LENGTH_LONG).show();
        }

    }

    private Spanned getHtmlText(String text)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            return Html.fromHtml(mQuestion.getQuestion(), Html.FROM_HTML_MODE_LEGACY);
        }
        else
        {
            return Html.fromHtml(mQuestion.getQuestion());
        }
    }

    @Override
    protected void initViews()
    {

        mTvQuestion = (TextView) findViewById(R.id.tv_question_text);

        tv_question_lable=(TextView)findViewById(R.id.tv_question_lable);
        mRgOptions = (RadioGroup) findViewById(R.id.rg_options);
        mRgOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                mQuestion.setAttempted(true);
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                mQuestion.setRightAnswer(radioButton.getText().toString().trim().equals(mQuestion.getAnswer().trim()));
            }
        });
        Bundle  bundle=getArguments();
        if(bundle !=null){
            int pos=bundle.getInt("position");
            int totalQuetion=bundle.getInt("totalquetions");
            tv_question_lable.setText("Quetion : "+(pos+1)+ File.separator+totalQuetion);

        }
        rbtnOp1 = (RadioButton) findViewById(R.id.rbtn_option1);
        rbtnOp2 = (RadioButton) findViewById(R.id.rbtn_option2);
        rbtnOp3 = (RadioButton) findViewById(R.id.rbtn_option3);
        rbtnOp4 = (RadioButton) findViewById(R.id.rbtn_option4);


    }
}
