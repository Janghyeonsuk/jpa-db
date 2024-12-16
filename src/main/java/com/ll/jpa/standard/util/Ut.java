package com.ll.jpa.standard.util;

import lombok.SneakyThrows;

public class Ut {
    public static class thread {
        @SneakyThrows
        public static void sleep(int millis) {
            Thread.sleep(millis);
        }
    }
}
