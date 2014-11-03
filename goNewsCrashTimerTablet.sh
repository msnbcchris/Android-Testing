ant clean
ant build
adb push ./bin/TODAYdotAPPTests.jar /data/local/tmp/
adb shell uiautomator runtest TODAYdotAPPTests.jar -c com.nbcnews.crashtimer.NEWSCrashTimerTablet
