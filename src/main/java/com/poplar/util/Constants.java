package com.poplar.util;

/**
 * User: FR
 * Time: 5/25/15 2:29 PM
 */
public class Constants {

    //Carriage return
    public static final byte CR = 13;
    //Line feed character
    public static final byte LF = 10;

    public class Control{
        public static final String METHOD_GET="GET";
    }

    public class HttpHeader{
        public static final String ACCEPT="Accept";
        public static final String ACCEPT_CHARSET="Accept-Charset";
        public static final String ACCEPT_ENCODING="Accept-Encoding";
        public static final String CONTENT_ENCODING="Content-Encoding";
        public static final String ACCEPT_ENCODING_GZIP="gzip";
        public static final String ACCEPT_LANGUAGE="Accept-Language";
        public static final String CONNECTION="Connection";
        public static final String COOKIE="Cookie";
        public static final String CONTENT_LENGTH="Content-Length";
        public static final String CONTENT_TYPE="Content-Type";
        public static final String CONTENT_TYPE_MULTIPART="multipart/form-data";
        public static final String TRANSFER_ENCODING="Transfer-Encoding";
        public static final String TRANSFER_ENCODING_CHUNKED="chunked";
        public static final String CONTENT_TRANSFER_ENCODING="Content-Transfer-Encoding";
        public static final String CONTENT_TRANSFER_ENCODING_BINARY="binary";


        public static final String ETag="ETag";
        public static final String IF_NONE_MATCH="If-None-Match";

        public static final String CACHE_CONTROL="Cache-Control";

        public static final String Expires="Expires";

        public static final String IF_MODIFIED_SINCE="If-Modified-Since";
        public static final String LAST_MODIFIED="Last-Modified";

    }



}
