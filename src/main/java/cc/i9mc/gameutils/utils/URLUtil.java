package cc.i9mc.gameutils.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Optional;

public class URLUtil {
    public static String readFromURL(String url, String def) {
        return Optional.ofNullable(readFromURL(url)).orElse(def);
    }

    public static String readFromURL(String url, Charset charset, String def) {
        return Optional.ofNullable(readFromURL(url, charset)).orElse(def);
    }

    public static String readFromURL(String url) {
        try (InputStream inputStream = new URL(url).openStream(); BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
            return new String(readFully(bufferedInputStream));
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public static String readFromURL(String url, Charset charset) {
        try (InputStream inputStream = new URL(url).openStream(); BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
            return new String(readFully(bufferedInputStream), charset);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public static String readFully(InputStream inputStream, Charset charset) throws IOException {
        return new String(readFully(inputStream), charset);
    }

    public static byte[] readFully(InputStream inputStream) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len;
        while ((len = inputStream.read(buf)) > 0) {
            stream.write(buf, 0, len);
        }
        return stream.toByteArray();
    }
}
