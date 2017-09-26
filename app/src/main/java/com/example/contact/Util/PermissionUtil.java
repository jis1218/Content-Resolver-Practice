package com.example.contact.Util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 정인섭 on 2017-09-26.
 */

public class PermissionUtil {

    private int req_code;
    private String permissions[];

    public PermissionUtil(int req_code, String[] perm) {
        this.req_code = req_code;
        this.permissions = perm;
    }

    public boolean checkPermission(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return requestPermission(activity);
        } else {
            return true;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean requestPermission(Activity activity) {

        List<String> whatWeRequire = new ArrayList<>();

        for (String i : permissions) {
            if (activity.checkSelfPermission(i) != PackageManager.PERMISSION_GRANTED) {
                whatWeRequire.add(i);
            }
        }

        if (whatWeRequire.size() > 0) {
            String[] theFinal = whatWeRequire.toArray(new String[whatWeRequire.size()]);
            activity.requestPermissions(theFinal, req_code);
            return false;
        } else {
            return true;
        }

    }

    public boolean afterRequestPermission(int requestCode, int[] granted) {

        if (requestCode == req_code) {
            boolean isGranted = true;
            for (int i : granted) {
                if (i != PackageManager.PERMISSION_GRANTED) {
                    isGranted = false;
                    break;
                }
            }
             return isGranted;
        }
        return false;
    }
}
