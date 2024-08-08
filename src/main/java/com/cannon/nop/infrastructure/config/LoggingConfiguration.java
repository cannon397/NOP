package com.cannon.nop.infrastructure.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import org.slf4j.LoggerFactory;

//logback setting
public class LoggingConfiguration {

    public static void configureLogging() {
        // Logger context를 가져옵니다.
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        // ConsoleAppender 생성 및 설정
        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
        consoleAppender.setContext(context);

        // Pattern encoder 설정
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
        encoder.start();

        // Appender에 encoder 연결
        consoleAppender.setEncoder(encoder);
        consoleAppender.start();

        // 루트 로거 설정
        Logger rootLogger = context.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.DEBUG);
        rootLogger.addAppender(consoleAppender);
    }
}