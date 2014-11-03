package com.nbcnews.today.uiautomator.misc;

import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class LaunchTODAY extends UiAutomatorTestCase {
	   public void testLaunchAndIdle() throws UiObjectNotFoundException {
		   
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
		      appsView.setAsHorizontalList();
		      UiObject todayApp = appsView.getChildByText(new UiSelector()
		         .className(android.widget.TextView.class.getName()), 
		         "TODAY");
		      todayApp.clickAndWaitForNewWindow();

		      // Validate that the package name is the expected one
		      UiObject packageValidation = new UiObject(new UiSelector()
		         .packageName("com.nbcnews.today"));
		      assertTrue("Could not find TODAY app",
		         packageValidation.exists());
		      
		      // Continue past the splash screen
		      UiObject continueButton = new UiObject(new UiSelector().text("Continue"));
		      continueButton.clickAndWaitForNewWindow();
		      
		      this.sleep(10000);
		      
		      // Tap the rainbow
		      UiCollection menuCollection = new UiCollection(new UiSelector().description("Navigate up")); 
		      UiObject menuButton = menuCollection.getChildByInstance(new UiSelector().className("android.widget.ImageView"), 1);
		      menuButton.clickAndWaitForNewWindow();
		      // and change to Top Stories
		      UiObject topStoriesButton = new UiObject(new UiSelector().text("Top Stories"));
		      topStoriesButton.clickAndWaitForNewWindow();
		      // Validate screen
		      UiObject topStoriesValidation = new UiObject(new UiSelector().text("TOP STORIES"));
		      assertTrue("Failed to switch to Top Stories", topStoriesValidation.exists());

		      this.sleep(10000);
		      
	   }
}
