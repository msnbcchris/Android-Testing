package com.nbcnews.common.watcher;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.core.UiWatcher;

public class HockeyWatcher implements UiWatcher {
	
	private String appName;
	
	public HockeyWatcher(String appName) {
		this.appName = appName;
	}
	
	public boolean checkForCondition() {
		// HockeyApp update popup
		// Update Available
		// Show information about the new update?
		// Dismiss
		// Show
		UiObject updateTitle = new UiObject(new UiSelector().text("Update Available"));
		if (updateTitle.exists()) {
			System.out.println("Dismissing Update Available popup");
			UiObject dismissButton = new UiObject(new UiSelector().text("Dismiss"));
			try {
				dismissButton.click();
				return true;
			}
			catch (UiObjectNotFoundException e) {
				return false;
			}
		}
		
		// HockeyApp crash popup
		// Crash Data
		// The app found information about previous crashes. Would you like to send this data to the developer?
		// Dismiss
		// Send
		UiObject crashTitle = new UiObject(new UiSelector().text("Crash Data"));
		if (crashTitle.exists()) {
			System.out.println("Sending crash data.");
			UiObject dismissButton = new UiObject(new UiSelector().text("Send"));
			try {
				dismissButton.click();
				return true;
			}
			catch (UiObjectNotFoundException e) {
				return false;
			}
		}

		UiObject stoppedTitle = new UiObject(new UiSelector().text("Unfortunately, " + this.appName + " has stopped."));
		if (stoppedTitle.exists()) {
			System.out.println("Handling crash popup.");
			UiObject dismissButton = new UiObject(new UiSelector().text("OK"));
			try {
				dismissButton.click();
				return true;
			}
			catch (UiObjectNotFoundException e) {
				return false;
			}
		}

		return false;
	}

}
