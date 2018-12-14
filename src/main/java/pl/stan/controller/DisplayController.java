package pl.stan.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.net.MediaType;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import pl.stan.util.JsonConverter;

public class DisplayController {
    
    public void startServer(int port, Executor executor) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        httpServer.createContext("/", (ex) -> {
            writeFromStream(ex, getClass().getResourceAsStream("/index.html"), MediaType.HTML_UTF_8);
        });
        httpServer.createContext("/api", (ex) -> {
            Map<String, Object> k = new HashMap<>();
            k.put("str", "mystring");
            k.put("bool", true);
            k.put("integer", 123);
            k.put("double", 123.123d);
            k.put("list", Lists.newArrayList("test1", 3, "t2", "te3", "kkk", true));
            k.put("map", ImmutableMap.<String, Object>builder()
                    .put("integer", 2)
                    .put("str", "stringus")
                    .build());
            writeJson(ex, JsonConverter.dumpJson(k));
        });
        httpServer.setExecutor(executor);
        httpServer.start();
    }
    
    private void write(HttpExchange exchange, String body) throws IOException {
        writeBody(exchange, body, MediaType.PLAIN_TEXT_UTF_8);
    }
    
    private void writeJson(HttpExchange exchange, String body) throws IOException {
        writeBody(exchange, body, MediaType.JSON_UTF_8);
    }
    
    private void writeBody(HttpExchange exchange, String body, MediaType contentType) throws IOException {
        exchange.getResponseHeaders().put("Content-Type", Lists.newArrayList(contentType.toString()));
        exchange.sendResponseHeaders(200, body.length());
        try (var buffer = exchange.getResponseBody()) {
            buffer.write(body.getBytes());
        }
    }
    
    private void writeFromStream(HttpExchange exchange, InputStream stream, MediaType contentType) throws IOException {
        exchange.getResponseHeaders().put("Content-Type", Lists.newArrayList(contentType.toString()));
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int length = copyToBuffer(stream, byteBuffer);
        exchange.sendResponseHeaders(200, length);
        try (var buffer = exchange.getResponseBody()) {
            buffer.write(byteBuffer.toByteArray());
            byteBuffer.close();
        }
    }
    
    private int copyToBuffer(InputStream is, OutputStream os) throws IOException {
        byte[] buffer = new byte[1024];
        int len, finalLen = 0;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
            finalLen += len;
        }
        return finalLen;
    }

}
