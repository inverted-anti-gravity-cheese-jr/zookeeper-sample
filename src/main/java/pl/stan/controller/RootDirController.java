package pl.stan.controller;

import java.io.IOException;
import java.io.InputStream;

import com.google.common.base.Strings;
import com.google.common.net.MediaType;
import com.sun.net.httpserver.HttpExchange;

public class RootDirController extends Controller {
    
    private final String root;
    
    public RootDirController(String root) {
        this.root = root;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String uri = exchange.getRequestURI().toString();
        if ("/".equals(uri) && !Strings.isNullOrEmpty(root) && root.startsWith("/")) {
            uri = root;
        }
        InputStream fStream = getClass().getResourceAsStream(uri);
        MediaType contentType = MediaType.HTML_UTF_8;
        if (uri.endsWith("css")) {
            contentType = MediaType.CSS_UTF_8;
        } else if (uri.endsWith("js")) {
            contentType = MediaType.JAVASCRIPT_UTF_8;
        }
        writeFromStream(exchange, fStream, contentType);
    }
}
