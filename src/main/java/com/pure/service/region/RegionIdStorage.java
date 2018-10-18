package com.pure.service.region;

public class RegionIdStorage {

    private static ThreadLocal<String> regionIdContext = new ThreadLocal<>();

    public static String getRegionIdContext() {
        return regionIdContext.get();
    }

    public static void setRegionIdContext(String tenant) {
        regionIdContext.set(tenant);
    }

    public static void clear() {
        regionIdContext.set(null);
    }

}
