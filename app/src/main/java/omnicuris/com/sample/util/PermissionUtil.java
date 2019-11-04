package omnicuris.com.sample.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class PermissionUtil
{
    public static boolean hasPermission(Context context, String permission)
    {
        if (!isNullOrEmpty(permission) && ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        return false;
    }

    public static final boolean isNullOrEmpty(final String inputString)
    {
        return inputString == null || inputString.length() < 1;
    }

    public static void checkPermissions(Activity context, final PermissionsCallback callback, String permissions)
    {
        if (!hasPermission(context, permissions))
        {
            android.support.v4.app.ActivityCompat.requestPermissions(context, new String[]{ permissions }, 101);
        }
        else
        {
            callback.result(true, false);
        }
    }


    public static interface PermissionsCallback
    {

        public void result(boolean success, boolean rationale);

    }
}
