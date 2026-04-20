package org.webrtc;

// Bridge class in org.webrtc package to access package-private EglBase impl classes.
// EglUtils calls these instead of EglBase.create() / createEgl10(), which are static
// interface methods that AGP 8.x D8 desugars inconsistently across compilation units.
public class EglBaseFactory {
    public static EglBase createEgl14() {
        return new EglBase14Impl(null, EglBase.CONFIG_PLAIN);
    }

    public static EglBase createEgl10() {
        return new EglBase10Impl(null, EglBase.CONFIG_PLAIN);
    }
}
