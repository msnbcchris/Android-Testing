package com.nbcnews.today.uiautomator.covercrashtimer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

import com.nbcnews.common.watcher.HockeyWatcher;
import com.nbcnews.common.util.AppUtility;

public class TODAYCoverCrashTimerTablet extends UiAutomatorTestCase {
	   public void testLaunchAndIdle() throws UiObjectNotFoundException {
		   //
		   HockeyWatcher watcher = new HockeyWatcher("TODAY");
		   this.getUiDevice().registerWatcher("TheWatcher", watcher);
		   
		   //
		   long startTime;
		   UiObject todayApp;
		   boolean isForward;
		   int flingCounter = 1;
		   int inc = 1;
		   String crashTime;
		   long minuteCounter;

		   // loop thru 5 times
		   for (int testingRound = 0; testingRound < 5; testingRound++)
		   {
				// Turn on screen
				try {
					this.getUiDevice().wakeUp();			    	  
				}
				catch (android.os.RemoteException e) {
					System.out.println("Could not wake device");
					return;
				}

			   AppUtility util = new AppUtility();
			   util.launchApp("TODAY");
		   
			   this.sleep(3000);
		   
			   // Get the horizontal scrollable view and start swiping
			   UiScrollable mainScrollView = new UiScrollable(new UiSelector().scrollable(true));
			   mainScrollView.setAsHorizontalList();

			   // get Cover Opener button
			   UiObject coverOpener = new UiObject(new UiSelector().resourceId("com.nbcnews.today:id/coverOpener"));

			   isForward = true;
			   flingCounter = 1;
			   inc = 1;
			   minuteCounter = 1;

			   // we are assuming the app will crash at some point in this loop
			   // mark start time
////			   watcher.startTime = System.currentTimeMillis();
			   startTime = System.currentTimeMillis();
			   // 
			   
			   // Start by checking if app is alive (has not crashed)
			   todayApp = new UiObject(new UiSelector().packageName("com.nbcnews.today"));
			   
			   System.out.println("------------------------------------------------------------------");
			   System.out.println("Starting round " + (testingRound + 1) + " of 5 at " + this.getCurrentTimestampPrintable());

			   while (todayApp.exists())
			   {
				   // this loop ends only when the app crashes.
				   // the loop counts up and down between 1 and 20.
				   // when it's counting up (from 1 to 20), flingToEnd() is called.
				   // when it's counting down (from 20 to 1), flingToBeginning() is called.
				   // after each fling, check if app is still alive
				   
				   // print heartbeat
				   if ((System.currentTimeMillis() - startTime) > (minuteCounter * 60 * 1000))
				   {
					   System.out.println("No crash after " + minuteCounter + " minutes...");
					   minuteCounter++;
				   }
				   
				   // start scrolling
				   if (flingCounter == 1)
				   {
					   inc = 1;
					   isForward = true;
				   }
				   else if (flingCounter == 20)
				   {
					   inc = -1;
					   isForward = false;
				   }

				   flingCounter += inc;

				   if (isForward)
				   {
//					   System.out.println("forward: " + flingCounter);
//					   mainScrollView.flingToEnd(1);
					   mainScrollView.scrollForward(15);
				   }
				   else
				   {
//					   System.out.println("backward: " + flingCounter);
//					   mainScrollView.flingToBeginning(1);
					   mainScrollView.scrollBackward(15);
				   }

				   // when flingCounter gets back to 1 (after reaching 20 and counting back down to 1),
				   // tap the coverOpener button to truly get back to the beginning of the cover.
				   if (flingCounter == 1)
				   {
//					   System.out.println("resetting");
					   this.sleep(2000);
					   coverOpener.click();
				   }

				   // Check if app is alive (has not crashed)
				   todayApp = new UiObject(new UiSelector().packageName("com.nbcnews.today"));

			   }
			   // if we get to here, the app has ceased to exist
			   crashTime = this.formatTime(System.currentTimeMillis() - startTime);
			   System.out.println("CRASH ENCOUNTERED AT: " + crashTime + " into round " + (testingRound + 1));
			   System.out.println("Round " + (testingRound + 1) + " of 5 ended at " + this.getCurrentTimestampPrintable());
			   System.out.println("------------------------------------------------------------------");
			   
		   }

	   }
	   
	   private String formatTime(long millis)
	   {
		   long second = (millis / 1000) % 60;
		   long minute = (millis / (1000 * 60)) % 60;
		   long hour = (millis / (1000 * 60 * 60)) % 24;

		   return String.format("%02d:%02d:%02d", hour, minute, second);
	   }
	   
	   private String getCurrentTimestampPrintable()
	   {
		   DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		   Date date = new Date();
		   return dateFormat.format(date);
	   }
}
