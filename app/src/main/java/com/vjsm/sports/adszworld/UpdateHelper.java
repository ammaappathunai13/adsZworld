package com.vjsm.sports.adszworld;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class UpdateHelper {
    public  static String Key_Enable="is_Update";
    public  static String Key_Version="version";

    public  static String Key_url_="update_url";
    public interface onUpdateCheckLinstener{
        void onUpdateCheckListener(String urlApp);
    }
    public static Builder with(Context context){
        return new Builder(context);
    }
    private onUpdateCheckLinstener onUpdateCheckLinstener;
    private Context context;
    public UpdateHelper(Context context,UpdateHelper.onUpdateCheckLinstener onUpdateCheckLinstener) {
        this.onUpdateCheckLinstener = onUpdateCheckLinstener;
        this.context = context;
    }
    public void check(){
        FirebaseRemoteConfig remoteConfig=FirebaseRemoteConfig.getInstance();
        if(remoteConfig.getBoolean(Key_Enable)){
            String CurrentVersion=remoteConfig.getString(Key_Version);
            String appversion=getAppVersion(context);
            String UpdateUrl=remoteConfig.getString(Key_url_);
if(!TextUtils.equals(CurrentVersion,appversion)&&onUpdateCheckLinstener!=null)
    onUpdateCheckLinstener.onUpdateCheckListener(UpdateUrl);
        }
    }

    private String getAppVersion(Context context) {
String result="";
try{
    result=context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionName;
    result=result.replaceAll("[a-zA-Z]|-","");
}catch (PackageManager.NameNotFoundException e){
    e.printStackTrace();
}
return result;
    }

    public  static class Builder{

        private Context context;
        private onUpdateCheckLinstener onUpdateCheckLinstener;

        public Builder(Context context) {
            this.context = context;
        }
        public Builder onUpdateChecker(onUpdateCheckLinstener onUpdateCheckLinstener){
            this.onUpdateCheckLinstener=onUpdateCheckLinstener;
            return this;
        }
        public  UpdateHelper build(){
            return new UpdateHelper(context,onUpdateCheckLinstener);

        }
        public UpdateHelper check(){
            UpdateHelper updateHelper=build();
            updateHelper.check();
            return updateHelper;
        }
    }

}
