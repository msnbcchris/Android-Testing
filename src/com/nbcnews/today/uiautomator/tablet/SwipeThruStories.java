package com.nbcnews.today.uiautomator.tablet;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

import com.nbcnews.common.util.AppUtility;

public class SwipeThruStories extends UiAutomatorTestCase {
	   public void testSwipeThruStories() throws UiObjectNotFoundException {
		   
		   // Launch app
		   AppUtility util = new AppUtility();
		   util.launchApp("TODAY");

		   // Validate that the app launched
		   UiObject packageValidation = new UiObject(new UiSelector()
		   		.packageName("com.nbcnews.today"));
		   assertTrue("Could not find TODAY app", packageValidation.exists());

//		      // Continue past the splash screen
//		      UiObject continueButton = new UiObject(new UiSelector().text("Continue"));
//		      if (continueButton.exists()) {
//		    	  continueButton.clickAndWaitForNewWindow();
//			      this.sleep(10000);
//		      }

		   //
	   }
}
