@rem Gradle wrapper para Windows
@rem Android Studio lo usa automáticamente

@if "%DEBUG%"=="" @echo off
@rem Set local scope for the variables
setlocal

set DIRNAME=%~dp0
set APP_HOME=%DIRNAME%

set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar

java -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*
