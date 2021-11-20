@echo off
@title JavaCompile

setlocal enabledelayedexpansion

set rootDir=%cd%
set cdD=%rootDir%\src
set targetRoot=target
set aimD=%targetRoot%\classes
set bootClass=sch.frog.Bootstrap
set outputName=calculator

if exist %aimD% (
	echo update project
	rd /s /Q %aimD%
) else (
	echo build project
)

md %aimD%

echo start compile ...

rem for /r src %%s in (*) do (
rem     if "%%~xs" neq ".java" (
rem 		set tmpD=%%~dps
rem 		set "tmpD=!tmpD:%cdD%=%aimD%!"
rem         if not exist !tmpD! mkdir !tmpD!
rem 		
rem 		set tmp=%%s
rem         set "tmp=!tmp:%cdD%=%aimD%!"
rem 		
rem 		echo copy resouce file
rem 		copy %%s !tmp!
rem     ) else (
rem     	echo javac -encoding UTF-8 -classpath src;lib\* %%s -d %aimD%
rem         javac -encoding UTF-8 -classpath src;lib\* %%s -d %aimD%
rem 	)
rem )

javac -encoding UTF-8 -classpath src src\sch\frog\*.java -d %aimD%

cd %aimD%

md META-INF
echo Main-Class: %bootClass% > META-INF/boot
jar -cvfm %outputName%.jar META-INF/boot *

cd %rootDir%
copy %aimD%\%outputName%.jar %targetRoot%
echo del /f/s/q %aimD%\%outputName%.jar
del /f/s/q %aimD%\%outputName%.jar

echo compile finish

echo starting java application ...
java -jar -XX:+UseSerialGC -XX:+PrintGCDetails %targetRoot%\%outputName%.jar