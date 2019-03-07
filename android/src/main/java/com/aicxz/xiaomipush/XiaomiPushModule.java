package com.aicxz.xiaomipush;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.List;

public class XiaomiPushModule extends ReactContextBaseJavaModule {

    private Context mContext;

    private static String mEvent;
    private static WritableMap mParams = null;

    private static ReactApplicationContext reactContext = null;
    public static final String APP_ID = "2882303761517878499";
    public static final String APP_KEY = "5621787875499";

    private final static String RECEIVE_REG_ID = "XiaomiPushReceiveRegId";


    public XiaomiPushModule() {
        super(reactContext);
    }

    public XiaomiPushModule(ReactApplicationContext reactContext) {
        super(reactContext);

        mContext = reactContext;
    }

    @Override
    public String getName() {

        return "RNXiaomiPush";

    }

    /**
     * 初始化推送服务
     */
    @ReactMethod
    public void registerPush() {
        if(shouldInit()) {
            MiPushClient.registerPush(mContext.getApplicationContext(), APP_ID, APP_KEY);
            reactContext = getReactApplicationContext();
        }
    }

    /**
     * 关闭推送
     */
    @ReactMethod
    public void unregisterPush() {
        MiPushClient.unregisterPush(mContext.getApplicationContext());
    }

    /**
     * 暂停推送
     */
    @ReactMethod
    public void pausePush() {
        MiPushClient.pausePush(mContext.getApplicationContext(), null);
    }

    /**
     * 恢复推送
     */
    @ReactMethod
    public void resumePush() {
        MiPushClient.resumePush(mContext.getApplicationContext(), null);
    }

    /**
     * 获取RegId
     */
    @ReactMethod
    public void getRegId(Promise promise) {
        String regId = MiPushClient.getRegId(mContext.getApplicationContext());
        promise.resolve(regId);
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = mContext.getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    private static void sendEvent() {
        reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(mEvent, mParams);

        XiaomiPushModule.mParams = null;
    }

    public static class XiaomiPushReceiver extends PushMessageReceiver {

        private String mRegId;
        private long mResultCode = -1;
        private String mReason;
        private String mCommand;
        private String mMessage;
        private String mTopic;
        private String mAlias;
        private String mUserAccount;
        private String mStartTime;
        private String mEndTime;

        @Override
        public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
            mMessage = message.getContent();
            if(!TextUtils.isEmpty(message.getTopic())) {
                mTopic=message.getTopic();
            } else if(!TextUtils.isEmpty(message.getAlias())) {
                mAlias=message.getAlias();
            } else if(!TextUtils.isEmpty(message.getUserAccount())) {
                mUserAccount=message.getUserAccount();
            }
        }

        @Override
        public void onNotificationMessageClicked(Context context, MiPushMessage message) {
            mMessage = message.getContent();
            if(!TextUtils.isEmpty(message.getTopic())) {
                mTopic=message.getTopic();
            } else if(!TextUtils.isEmpty(message.getAlias())) {
                mAlias=message.getAlias();
            } else if(!TextUtils.isEmpty(message.getUserAccount())) {
                mUserAccount=message.getUserAccount();
            }
        }

        @Override
        public void onNotificationMessageArrived(Context context, MiPushMessage message) {
            mMessage = message.getContent();
            if(!TextUtils.isEmpty(message.getTopic())) {
                mTopic=message.getTopic();
            } else if(!TextUtils.isEmpty(message.getAlias())) {
                mAlias=message.getAlias();
            } else if(!TextUtils.isEmpty(message.getUserAccount())) {
                mUserAccount=message.getUserAccount();
            }
        }

        @Override
        public void onCommandResult(Context context, MiPushCommandMessage message) {
            String command = message.getCommand();
            List<String> arguments = message.getCommandArguments();
            String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
            String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);

            Log.d("XiaoMiPush", "Command:" + command);
            Log.d("XiaoMiPush", "ResultCode" + message.getResultCode() + "");

            if (MiPushClient.COMMAND_REGISTER.equals(command)) {
                if (message.getResultCode() == ErrorCode.SUCCESS) {

                    mRegId = cmdArg1;

                    XiaomiPushModule.mEvent = RECEIVE_REG_ID;
                    XiaomiPushModule.mParams = Arguments.createMap();
                    XiaomiPushModule.mParams.putString("regId", mRegId);
                    XiaomiPushModule.sendEvent();

                    Log.d("XiaoMiPush", "RegId:" + mRegId);
                }
            } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
                if (message.getResultCode() == ErrorCode.SUCCESS) {
                    mAlias = cmdArg1;
                }
            } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
                if (message.getResultCode() == ErrorCode.SUCCESS) {
                    mAlias = cmdArg1;
                }
            } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
                if (message.getResultCode() == ErrorCode.SUCCESS) {
                    mTopic = cmdArg1;
                }
            } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
                if (message.getResultCode() == ErrorCode.SUCCESS) {
                    mTopic = cmdArg1;
                }
            } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
                if (message.getResultCode() == ErrorCode.SUCCESS) {
                    mStartTime = cmdArg1;
                    mEndTime = cmdArg2;
                }
            }
        }


    }
}
