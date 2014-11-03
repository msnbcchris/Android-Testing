package com.nbcnews.today.uiautomator.misc;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

import com.nbcnews.today.uiautomator.hockeycrashwatcher.HockeyWatcher;
import com.nbcnews.common.util.AppUtility;

public class LaunchTODAYTablet extends UiAutomatorTestCase {
	   public void testLaunchAndIdle() throws UiObjectNotFoundException {
		   //
		   HockeyWatcher watcher = new HockeyWatcher();
		   this.getUiDevice().registerWatcher("TheWatcher", watcher);
		   
		   //
		   long startTime;

		   // loop thru 5 times
		   for (int i = 0; i < 5; i++)
		   {
			   AppUtility util = new AppUtility();
			   util.launchApp("TODAY");
		   
			   this.sleep(3000);
		   
			   // Get the horizontal scrollable view and start swiping
			   UiScrollable mainScrollView = new UiScrollable(new UiSelector().scrollable(true));
			   mainScrollView.setAsHorizontalList();

			   // get Cover Opener button
			   UiObject coverOpener = new UiObject(new UiSelector().resourceId("com.nbcnews.today:id/coverOpener"));

			   // we are assuming the app will crash at some point in this loop
			   // mark start time
			   startTime = System.currentTimeMillis();
			   while (true)
			   {
				   // start scrolling
				   mainScrollView.flingToEnd(20);
				   mainScrollView.flingToBeginning(20);
				   this.sleep(3000);
				   coverOpener.click();
			   }
			   
		   }

	   }
}
