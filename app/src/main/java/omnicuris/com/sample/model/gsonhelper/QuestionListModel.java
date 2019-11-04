package omnicuris.com.sample.model.gsonhelper;

import java.util.List;

import omnicuris.com.sample.model.Question;

public class QuestionListModel
{
    private List<Question> QuestionList;

    public List<Question> getmQuestionList()
    {
        return QuestionList;

    }

    public void setmQuestionList(List<Question> mQuestionList)
    {
        this.QuestionList = mQuestionList;
    }
}
