@echo off
@title JavaCompile

setlocal enabledelayedexpansion

set cdD=%cd%\src
set aimD=classes
set bootClass=frog.test.TestApp

if exist %aimD% (
	echo update project
	rd /s /Q %aimD%
) else (
	echo build project
)

md %aimD%

echo start compile ...
for /r src %%s in (*) do (
    if "%%~xs" neq ".java" (
		set tmpD=%%~dps
		set "tmpD=!tmpD:%cdD%=%aimD%!"
        if not exist !tmpD! mkdir !tmpD!
		
		set tmp=%%s
        set "tmp=!tmp:%cdD%=%aimD%!"
		
		echo copy resouce file
		copy %%s !tmp!
    ) else (
    	echo javac -encoding UTF-8 -classpath src;lib\* %%s -d %aimD%
        javac -encoding UTF-8 -classpath src;lib\* %%s -d %aimD%
	)
)
echo compile finish

echo starting java application ...
java -XX:+UseSerialGC -XX:+PrintGCDetails -classpath %aimD%;lib\* %bootClass%