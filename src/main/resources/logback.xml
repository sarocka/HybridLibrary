<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!--directory where log files are stored-->
    <property name="LOG_DIR" value="src/main/logs"></property>
    <property name="FILE_PREFIX" value="hybridlibrary"></property>
    <!--appender name arbitrary-->
<appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
<file>${LOG_DIR}/${FILE_PREFIX}.log</file>
<encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
<Pattern>%d{yyyy-MM-dd HH:mm:ss} - %msg%n</Pattern> <!--date pattern in log file-->
</encoder>
<rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
<!--directory where archived log files go; %i index which will be appended to log file name, eg. logFileName1;-->
<fileNamePattern>${LOG_DIR}/archived/${FILE_PREFIX}.%d{yyyy-MM-dd HH:mm:ss}.%i.log</fileNamePattern>
    <!--new log file created every day or once the file size reaches 10mb-->
<timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
    <maxFileSize>10MB</maxFileSize>
</timeBasedFileNamingAndTriggeringPolicy>

</rollingPolicy>

</appender>
<!--default log level-->
<root level="info">
    <appender-ref ref="FILE"></appender-ref>
</root>


</configuration>