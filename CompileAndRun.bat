@echo off
@title JavaCompile

setlocal enabledelayedexpansion

set rootDir=%cd%
set cdD=%rootDir%\src
set targetRoot=target
set aimD=%targetRoot%\classes
set bootClass=frog.test.Bootstrap
set resource=%rootDir%\resource
set outputName=calculator

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

xcopy %resource%\* %aimD% /s/e
cd %aimD%
jar -cvfm %outputName%.jar META-INF/MENIFEST.MF *

cd %rootDir%
copy %aimD%\%outputName%.jar %targetRoot%
echo del /f/s/q %aimD%\%outputName%.jar
del /f/s/q %aimD%\%outputName%.jar

echo compile finish

echo starting java application ...
java -jar -XX:+UseSerialGC -XX:+PrintGCDetails %targetRoot%\%outputName%.jar