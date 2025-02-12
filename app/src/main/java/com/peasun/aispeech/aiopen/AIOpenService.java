package com.peasun.aispeech.aiopen;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Shahsen on 2020/2/23.
 */
public class AIOpenService extends Service {
    private String TAG = "AIOpenService";

    AIOpenReceiver aiOpenReceiver = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            Bundle data = intent.getExtras();
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action)) {
                switch (action) {
                    case AIOpenConstant.AI_OPEN_ACTION_KTV: {
                        if (data != null) {
                            //play text
                            String command = data.getString("common");
                            if (!TextUtils.isEmpty(command)) {
                                if ("search".equals(command)) {
                                    String keyword = data.getString("keyword");

                                    String singer = data.getString("singerName");
                                    String songName = data.getString("songName");

                                    Log.d(TAG, "receive :" + command + ", " + keyword + ", singer " + singer + ", song " + songName);
                                    //todo

                                } else if ("control".equals(command)) {
                                    String ctlAction = data.getString("action");
                                    String playIndex = data.getString("PlaySongIndex");

                                    Log.d(TAG, "receive :" + command + ", " + ctlAction + ", index " + playIndex);
                                    //todo

                                }
                            }

                        }
                    }
                    break;
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //register karaoke app to ai server
        AIOpenUtils.registerKaraokeApp(this);

        //register receiver
        aiOpenReceiver = AIOpenUtils.registerKaraokeReceiver(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(aiOpenReceiver);
    }
}
