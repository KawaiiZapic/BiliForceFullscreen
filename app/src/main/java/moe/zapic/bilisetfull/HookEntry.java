package moe.zapic.bilisetfull;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;

import java.util.Arrays;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.XC_MethodHook;
public class HookEntry implements IXposedHookLoadPackage {

    public boolean inHookedActivity = false;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("tv.danmaku.bili")) return;
        XposedHelpers.findAndHookMethod(View.class, "setSystemUiVisibility", int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                 StackTraceElement[] stack = Thread.currentThread().getStackTrace();
                if (inHookedActivity && Arrays.stream(stack).noneMatch((it) -> it.getMethodName().startsWith("setFullScreen"))) {
                    param.setResult(null);
                }

            }
        });
        String[] forceList = {
                "com.bilibili.ship.theseus.detail.UnitedBizDetailsActivity"
        };
        XposedHelpers.findAndHookMethod(Activity.class, "onResume", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Activity activity = (Activity) param.thisObject;
                String activityName = activity.getClass().getName();
                if (Arrays.stream(forceList).anyMatch(activityName::startsWith)) {
                    inHookedActivity = true;
                    setFullScreen(activity);
                } else {
                    inHookedActivity = false;
                }
            }
        });
    }

    @SuppressLint("InlinedApi")
    public void setFullScreen(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
    }
}
