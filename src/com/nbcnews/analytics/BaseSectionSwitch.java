package com.nbcnews.analytics;

import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.nbcnews.common.util.AppUtility;

public class BaseSectionSwitch extends UiAutomatorTestCase {
	private String appName;
	private String appPackage;
	private String drawerOpenerID;
	private String sectionListID;
	private String sectionTextClass;

	public BaseSectionSwitch(String appName, String appPackage, String drawerOpenerID, String sectionListID, String sectionTextClass) {
		this.appName = appName;
		this.appPackage = appPackage;
		this.drawerOpenerID = drawerOpenerID;
		this.sectionListID = sectionListID;
		this.sectionTextClass = sectionTextClass;
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

			this.sleep(5000);

			UiObject drawerButton = new UiObject(new UiSelector().resourceId(this.appPackage + ":" + this.drawerOpenerID));
			drawerButton.click();
			
			UiCollection sectionList = new UiCollection(new UiSelector().resourceId(this.appPackage + ":" + this.sectionListID).instance(3));
			int childCount = sectionList.getChildCount(new UiSelector().className(this.sectionTextClass));
			
			//
			UiObject child;
			for (int i = 1; i < childCount; i++)
			{
				int s = 9; // number of sections to tap
				child = sectionList.getChild(new UiSelector().className(this.sectionTextClass).instance(i));
				System.out.println("Tapping section " + i + " of " + s + ":" + child.getText());
				child.click();
				this.sleep(1000);
				if (i == s)
					break;
				else
					drawerButton.click();
			}
			
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
