package com.bserver.util;

import com.bserver.context.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: FR
 * Time: 5/25/15 3:15 PM
 */
public class CacheIndex {
    private static final String E_TAG_INDEX_PREFIX="eTag_";
    private static final String MODIFY_INDEX_PREFIX="modify_";
    private static final List<String> CACHABLE_RESOURCE_EXTENSION =new ArrayList<String>();
    static {
        CACHABLE_RESOURCE_EXTENSION.add(".jpg");
        CACHABLE_RESOURCE_EXTENSION.add(".png");
        CACHABLE_RESOURCE_EXTENSION.add(".css");
        CACHABLE_RESOURCE_EXTENSION.add(".js");
    }
    private static ConcurrentHashMap<String, String> CACHE_INDEX = new ConcurrentHashMap<String, String>();

    public static boolean canClientUseLocalCache(Request request){
        if(!request.getMethod().equals(Constants.Control.METHOD_GET)){
            return false;
        }
        String url = request.getUrl();
        if(!CacheIndex.isCachable(url)){
            return false;
        }
        boolean isETagUseful = CacheIndex.isETagUseful(url, request.getHeader().getValue(Constants.HttpHeader.IF_NONE_MATCH));
        boolean isModifySince = CacheIndex.isModifySince(url, request.getHeader().getValue(Constants.HttpHeader.IF_MODIFIED_SINCE));
        return isETagUseful&&isModifySince;
    }

    public static boolean isCachable(String url){
        if(url.lastIndexOf(".")==-1){
            return false;
        }
        String extension = url.substring(url.lastIndexOf("."));
        return CACHABLE_RESOURCE_EXTENSION.contains(extension);
    }

    public static void addETagCacheIndex(String url, String eTag){
        CACHE_INDEX.put(E_TAG_INDEX_PREFIX + url, eTag);
    }

    public static boolean isETagUseful(String url, String eTag){
        if(CACHE_INDEX.get(E_TAG_INDEX_PREFIX+url)==null){
            return false;
        }
        if(CACHE_INDEX.get(E_TAG_INDEX_PREFIX+url).equals(eTag)){
            return true;
        }else {
            return false;
        }
    }

    public static void addModifyCacheIndex(String url, String timeStr){
        CACHE_INDEX.put(MODIFY_INDEX_PREFIX + url, timeStr);
    }

    public static boolean isModifySince(String url, String timeStr){
        if(CACHE_INDEX.get(MODIFY_INDEX_PREFIX+url)==null){
            return false;
        }
        if(CACHE_INDEX.get(MODIFY_INDEX_PREFIX+url).compareTo(timeStr)>0){
            return false;
        }
        return true;
    }
}
