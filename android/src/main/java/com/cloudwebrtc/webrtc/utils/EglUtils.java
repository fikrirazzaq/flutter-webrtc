package com.cloudwebrtc.webrtc.utils;

import android.os.Build;

import org.webrtc.EglBase;
import org.webrtc.EglBase10Impl;
import org.webrtc.EglBase14Impl;

public class EglUtils {
    private static EglBase rootEglBase;

    public static synchronized EglBase getRootEglBase() {
        if (rootEglBase == null) {
            // Avoid calling EglBase.create() / createEgl10() — they are static interface methods
            // that D8 (AGP 8.x) desugars inconsistently when the pre-built AAR and plugin source
            // are processed in separate compilation units, causing NoSuchMethodError at runtime.
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
                rootEglBase = new EglBase10Impl(null, EglBase.CONFIG_PLAIN);
            else
                rootEglBase = new EglBase14Impl(null, EglBase.CONFIG_PLAIN);
        }

        return rootEglBase;
    }

    public static EglBase.Context getRootEglBaseContext() {
        EglBase eglBase = getRootEglBase();

        return eglBase == null ? null : eglBase.getEglBaseContext();
    }
}
