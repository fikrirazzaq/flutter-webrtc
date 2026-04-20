package com.cloudwebrtc.webrtc.utils;

import android.os.Build;

import org.webrtc.EglBase;
import org.webrtc.EglBaseFactory;

public class EglUtils {
    private static EglBase rootEglBase;

    public static synchronized EglBase getRootEglBase() {
        if (rootEglBase == null) {
            // Avoid calling EglBase.create() / createEgl10() — they are static interface methods
            // that D8 (AGP 8.x) desugars inconsistently when the pre-built AAR and plugin source
            // are processed in separate compilation units, causing NoSuchMethodError at runtime.
            // EglBaseFactory is a regular class in org.webrtc package that can access the
            // package-private impl classes directly.
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
                rootEglBase = EglBaseFactory.createEgl10();
            else
                rootEglBase = EglBaseFactory.createEgl14();
        }

        return rootEglBase;
    }

    public static EglBase.Context getRootEglBaseContext() {
        EglBase eglBase = getRootEglBase();

        return eglBase == null ? null : eglBase.getEglBaseContext();
    }
}
