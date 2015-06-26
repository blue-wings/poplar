package com.poplar.server.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 * User: FR
 * Time: 6/2/15 10:15 AM
 */
public class CompressUtil {

    public static byte[] compressByGzip(String content) {
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes("UTF-8"));
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            GZIPOutputStream gos = new GZIPOutputStream(os);
            int count;
            byte[] data = new byte[1024];
            while ((count = is.read(data)) != -1) {
                gos.write(data, 0, count);
                gos.flush();
            }
            gos.finish();
            gos.close();
            return os.toByteArray();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

}
