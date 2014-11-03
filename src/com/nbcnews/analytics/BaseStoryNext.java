package com.nbcnews.analytics;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.nbcnews.common.util.AppUtility;

public class BaseStoryNext extends UiAutomatorTestCase {
	private String appName;
	private String appPackage;
	private String thumbSelectorID;
	private String storyPagerID;

	public BaseStoryNext(String appName, String appPackage, String thumbSelectorID, String storyPagerID) {
		this.appName = appName;
		this.appPackage = appPackage;
		this.thumbSelectorID = thumbSelectorID;
		this.storyPagerID = storyPagerID;
	}

	   public void testMain() throws UiObjectNotFoundException {		   
			// Turn on screen
			try {
				this.getUiDevice().wakeUp();			    	  
			}
			catch (android.os.RemoteException e) {
				System.out.println("Could not wake device");
				return;
			}
			
			// warm up is required to allow freshly installed app to load content
			// and to dismiss the left drawer
			this.launchAppAndWarmUp();

			this.sleep(10000);

			// click first tile
			UiObject tile = new UiObject(new UiSelector().resourceId(this.appPackage + ":" + this.thumbSelectorID).instance(0));
			tile.click();
			this.sleep(5000);
			
			// scroll over to next story 8x
			UiScrollable storyView = new UiScrollable(new UiSelector().resourceId(this.appPackage + ":" + this.storyPagerID));	//scrollable
//			UiObject coverOpener = new UiObject(new UiSelector().resourceId(this.coverOpenerID));
			storyView.setAsHorizontalList();
			int swipeCount = 8;
			for (int i = 0; i < swipeCount; i++)
			{
				storyView.scrollForward(15);
				this.sleep(3000);
			}
//			coverOpener.click();
	   }

	   // call this method to prepare a freshly installed app for testing
	   // it's mainly needed to dismiss the left drawer that may appear on launch of a freshly installed app
	   private void launchAppAndWarmUp() throws UiObjectNotFoundException
	   {
		   // launch the app
		   AppUtility util = new AppUtility();
		   util.launchApp(this.appName);
	   
//		   this.sleep(5000);
		   
		   UiObject leftDrawer = new UiObject(new UiSelector().resourceId(this.appPackage + ":id/left_drawer"));
		   
		   // try once every second for 10 tries to look for the left drawer and dismiss it
		   for (int i = 0; i < 10; i++)
		   {
			   //look for left drawer
			   if (leftDrawer.exists())
			   {
				   System.out.println("found left drawer, closing it...");
				   // dismiss left drawer
				   this.getUiDevice().pressBack();
				   break;
			   }
			   System.out.println("waiting for left drawer to appear so I can close it...");
			   this.sleep(1000);
		   }
		   System.out.println("warm up complete.");
		   // this line should exit the app
//		   this.getUiDevice().pressBack();
	   }
	   
}
