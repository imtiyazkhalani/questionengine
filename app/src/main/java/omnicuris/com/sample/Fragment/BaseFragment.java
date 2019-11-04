package omnicuris.com.sample.Fragment;

import android.view.View;

import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment
{
    protected View mainView;

    protected View findViewById(int resId)
    {
        if (mainView != null)
            return mainView.findViewById(resId);
        return null;
    }

    protected abstract void initViews();
}
