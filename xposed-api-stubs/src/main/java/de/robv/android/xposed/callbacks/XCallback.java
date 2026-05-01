package de.robv.android.xposed.callbacks;

public abstract class XCallback implements Comparable<XCallback> {
    public static final int PRIORITY_DEFAULT = 50;
    public static final int PRIORITY_LOWEST = -10000;
    public static final int PRIORITY_HIGHEST = 10000;

    public final int priority;

    protected XCallback() {
        this(PRIORITY_DEFAULT);
    }

    protected XCallback(int priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(XCallback other) {
        return Integer.compare(other.priority, this.priority);
    }

    public static class Param {
    }
}
