package pl.stan.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.google.common.collect.Lists;
import com.google.common.net.MediaType;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class Controller implements HttpHandler {
    
    public static void write(HttpExchange exchange, String body) throws IOException {
        writeBody(exchange, body, MediaType.PLAIN_TEXT_UTF_8);
    }
    
    public static void writeJson(HttpExchange exchange, String body) throws IOException {
        writeBody(exchange, body, MediaType.JSON_UTF_8);
    }
    
    public static void writeBody(HttpExchange exchange, String body, MediaType contentType) throws IOException {
        exchange.getResponseHeaders().put("Content-Type", Lists.newArrayList(contentType.toString()));
        exchange.sendResponseHeaders(200, body.length());
        try (var buffer = exchange.getResponseBody()) {
            buffer.write(body.getBytes());
        }
    }
    
    public static void writeFromStream(HttpExchange exchange, InputStream stream, MediaType contentType) throws IOException {
        exchange.getResponseHeaders().put("Content-Type", Lists.newArrayList(contentType.toString()));
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int length = copyToBuffer(stream, byteBuffer);
        exchange.sendResponseHeaders(200, length);
        try (var buffer = exchange.getResponseBody()) {
            buffer.write(byteBuffer.toByteArray());
            byteBuffer.close();
        }
    }
    
    protected static int copyToBuffer(InputStream is, OutputStream os) throws IOException {
        byte[] buffer = new byte[1024];
        int len, finalLen = 0;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
            finalLen += len;
        }
        return finalLen;
    }

}
