package org.dpcq.ai.util;

import java.util.stream.Stream;

public class MdToJsonUtil {
    public static String convert(String mdString) {
        // 按换行符分割并过滤代码块标记
        return Stream.of(mdString.split("\\R"))
                .filter(line -> !line.startsWith("```json") && !line.equals("```"))
                .collect(StringBuilder::new, (sb, line) -> {
                    // 处理转义引号和换行
                    String processed = line.replace("\\\"", "\"");
                    sb.append(processed).append("\n");
                }, StringBuilder::append)
                .toString()
                .trim(); // 去除末尾换行
    }
}
