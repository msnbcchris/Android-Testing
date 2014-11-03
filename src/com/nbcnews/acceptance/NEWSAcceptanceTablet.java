package com.nbcnews.acceptance;

import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.nbcnews.common.util.AppUtility;
import com.nbcnews.common.watcher.HockeyWatcher;

public class NEWSAcceptanceTablet extends UiAutomatorTestCase {
	private String appName;
	public void testLaunchAndIdle() throws UiObjectNotFoundException {
		this.appName = "NBC NEWS 2.0";
		   HockeyWatcher watcher = new HockeyWatcher(this.appName);
		   this.getUiDevice().registerWatcher("TheWatcher", watcher);

			// Turn on screen
			try {
				this.getUiDevice().wakeUp();			    	  
			}
			catch (android.os.RemoteException e) {
				System.out.println("Could not wake device");
				return;
			}

		   AppUtility util = new AppUtility();
		   util.launchApp(this.appName);
	   
		   this.sleep(30000);
		   UiScrollable mainScrollView;
		   mainScrollView = new UiScrollable(new UiSelector().resourceId("com.zumobi.msnbc:id/listsContainer"));
		   mainScrollView.setAsVerticalList();
		   System.out.println("Forward...");
		   mainScrollView.scrollForward(15);
		   System.out.println("Forward...");
		   mainScrollView.scrollForward(15);
		   System.out.println("Forward...");
		   mainScrollView.scrollForward(15);
		   this.sleep(10000);
	}
}
