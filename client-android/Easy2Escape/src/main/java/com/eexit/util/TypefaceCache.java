package com.eexit.util;

import android.graphics.Typeface;
import android.content.res.AssetManager;
import java.util.Hashtable;

public class TypefaceCache {
    private static final Hashtable<String, Typeface> CACHE = new Hashtable<String, Typeface>();
    public static Typeface get(AssetManager manager, String name) {
	synchronized (CACHE) {
	    if (!CACHE.containsKey(name)) {
		Typeface t = Typeface.createFromAsset(manager, name);
		CACHE.put(name, t);
	    }
	    return CACHE.get(name);
	}
    }
}
