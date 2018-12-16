package pl.stan;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.net.MediaType;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import pl.stan.controller.Controller;
import pl.stan.controller.RootDirController;
import pl.stan.util.JsonConverter;

import static pl.stan.controller.Controller.writeJson;

public class Server {

    private HttpServer httpServer;

    public void startServer(int port, Executor executor) throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        createContext("/", new RootDirController("/index.html"));
        createContext("/api", (HttpExchange ex) -> {
            Map<String, Object> k = new HashMap<>();
            k.put("str", "mystring");
            k.put("bool", true);
            k.put("integer", 123);
            k.put("double", 123.123d);
            k.put("list", Lists.newArrayList("test1", 3, "t2", "te3", "kkk", true));
            k.put("map", ImmutableMap.<String, Object>builder().put("integer", 2).put("str", "stringus").build());
            writeJson(ex, JsonConverter.dumpJson(k));
        });
        httpServer.setExecutor(executor);
        httpServer.start();
    }

    private void createContext(String mapping, HttpHandler handler) {
        httpServer.createContext(mapping, handler);
    }

    private void createContext(String mapping, Controller controller) {
        httpServer.createContext(mapping, controller);
    }

}
