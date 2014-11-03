package com.nbcnews.today.uiautomator.misc;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class TabletCoverControlStress extends UiAutomatorTestCase {
	   public void testLaunchAndIdle() throws UiObjectNotFoundException {
/*		   
		      // Tap HOME button.
		      this.getUiDevice().pressHome();
		      // Tap Apps button 
		      UiObject appsButton = new UiObject(new UiSelector().description("Apps"));
		      appsButton.clickAndWaitForNewWindow();
		      // Tap Apps tab
		      UiObject appsTab = new UiObject(new UiSelector().description("Apps"));
		      appsTab.click();
		      // Get the horizontal scrollable view and tap TODAY app icon
		      UiScrollable appsView = new UiScrollable(new UiSelector().scrollable(true));
//		      appsView.setAsHorizontalList();
		      UiObject todayApp = appsView.getChildByText(new UiSelector()
		         .className(android.widget.TextView.class.getName()), 
		         "TODAY");
		      todayApp.clickAndWaitForNewWindow();
		      // Validate that the package name is the expected one
		      UiObject packageValidation = new UiObject(new UiSelector()
		         .packageName("com.nbcnews.today"));
		      assertTrue("Could not find TODAY app",
		         packageValidation.exists());
		      
		      this.sleep(5000);

		      // Continue past the splash screen
		      UiObject continueButton = new UiObject(new UiSelector().text("Continue"));
		      if (continueButton.exists()) {
		    	  continueButton.clickAndWaitForNewWindow();
			      this.sleep(5000);
		      }
*/
		      // Tap Sections button
		      UiObject sectionsButton = new UiObject(new UiSelector().text("Sections"));
		      sectionsButton.click();
		      

		      // Get sections list view
		      UiCollection sectionsListView = new UiCollection(new UiSelector().className("android.widget.ListView"));
		      assertTrue("Could not find Sections menu", sectionsListView.exists());

		      sectionsListView.swipeUp(10);
		      
/*
		      // Iterate through sections.
		      // 0 is already selected at startup so start at 1.
		      int countSections = sectionsListView.getChildCount();
		      for (int i = 1; i < countSections; i++) {
		    	  sectionsListView.getChildByInstance(new UiSelector().className("android.widget.TextView"), i)
		    	  	.clickAndWaitForNewWindow();

		    	  if (i < countSections -1) {
				      // Tap Sections button
				      sectionsButton.click();
		    	  }
		      }
*/
		      // Tap Top Stories button
//		      UiObject topStoriesButton = new UiObject(new UiSelector().text("Top Stories"));
//		      topStoriesButton.click();
		      
	   }
}
