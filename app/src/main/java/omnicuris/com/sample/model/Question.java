package omnicuris.com.sample.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Question implements Parcelable
{

    public static final Creator<Question> CREATOR = new Creator<Question>()
    {
        @Override
        public Question createFromParcel(Parcel in)
        {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size)
        {
            return new Question[size];
        }
    };
    private String question;
    private List<String> options;
    private String answer;

    @Expose
    private boolean isRightAnswer, isAttempted;

    protected Question(Parcel in)
    {
        question = in.readString();
        options = in.createStringArrayList();
        answer = in.readString();
        isRightAnswer = in.readByte() != 0;
        isAttempted = in.readByte() != 0;
    }

    public String getQuestion()
    {
        return question;
    }

    public void setQuestion(String question)
    {
        this.question = question;
    }

    public List<String> getOptions()
    {
        return options;
    }

    public void setOptions(List<String> options)
    {
        this.options = options;
    }

    public String getAnswer()
    {
        return answer;
    }

    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    public boolean isRightAnswer()
    {
        return isRightAnswer;
        //        return false;
    }

    public void setRightAnswer(boolean rightAnswer)
    {
        isRightAnswer = rightAnswer;
    }

    public boolean isAttempted()
    {
        return isAttempted;
        //        return false;
    }

    public void setAttempted(boolean attempted)
    {
        isAttempted = attempted;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(question);
        dest.writeStringList(options);
        dest.writeString(answer);
        dest.writeByte((byte) (isRightAnswer ? 1 : 0));
        dest.writeByte((byte) (isAttempted ? 1 : 0));
    }
}
