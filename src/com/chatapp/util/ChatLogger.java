package com.chatapp.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatLogger {

    private static final String LOG_DIRECTORY = "logs";
    private static final String LOG_FILE = LOG_DIRECTORY + "/chat.log";

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static synchronized void log(String message) {

        try {

            File directory = new File(LOG_DIRECTORY);

            if (!directory.exists()) {

                directory.mkdirs();

            }

            try (FileWriter writer = new FileWriter(LOG_FILE, true)) {

                String timestamp =
                        LocalDateTime.now().format(FORMATTER);

                writer.write("[" + timestamp + "] " + message);

                writer.write(System.lineSeparator());

            }

        } catch (IOException e) {

            System.out.println(
                    "Logger Error : " + e.getMessage()
            );

        }

    }

}