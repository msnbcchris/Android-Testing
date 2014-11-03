package com.nbcnews.common.util;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.core.UiDevice;

public class AppUtility {
	   public void launchApp(String appName) throws UiObjectNotFoundException {
		   
		   // Tap HOME button.
		   UiDevice device = UiDevice.getInstance();
		   device.pressHome();

		   // Tap Apps button 
		   UiObject appsButton = new UiObject(new UiSelector().description("Apps"));
		   appsButton.clickAndWaitForNewWindow();

		   // Tap Apps tab
		   UiObject appsTab = new UiObject(new UiSelector().description("Apps"));
		   if (appsTab.exists())
			   appsTab.click();

		   // Get the horizontal scrollable view and tap TODAY app icon
		   UiScrollable appsView = new UiScrollable(new UiSelector().scrollable(true));
		   appsView.setAsHorizontalList();
		   UiObject todayApp = appsView.getChildByText(new UiSelector()
		   		.className(android.widget.TextView.class.getName()), 
				   appName);
		   todayApp.clickAndWaitForNewWindow();

	   }

}
