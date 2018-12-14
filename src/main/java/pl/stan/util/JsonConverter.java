package pl.stan.util;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public final class JsonConverter {
    
    private static final String STRING_DELIM = "\"";
    
    public static String dumpJson(Map<String, Object> obj) {
        return dumpJsonRecursive(obj);
    }
    
    private static String dumpJsonRecursive(Map<String, Object> obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(Map.Entry<String, Object> entry: obj.entrySet()) {
            String key = entry.getKey();
            String value = getValue(entry.getValue());
            sb.append(STRING_DELIM);
            sb.append(key);
            sb.append(STRING_DELIM);
            sb.append(":");
            sb.append(value);
            sb.append(",");
        }
        if (!obj.isEmpty()) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("}");
        return sb.toString();
    }
    
    @SuppressWarnings("unchecked")
    private static String getValue(Object obj) {
        if (obj instanceof Map) {
            return dumpJsonRecursive((Map<String, Object>) obj);
        } else if (obj instanceof Collection) {
            return "[" +((Collection<?>) obj)
                .stream()
                .map(JsonConverter::getValue)
                .collect(Collectors.joining(",")) + "]";
        } else if (obj instanceof String) {
            return STRING_DELIM + obj + STRING_DELIM;
        }else {
            return obj.toString();
        }
    }

}
