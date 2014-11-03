package com.nbcnews.today.uiautomator.hockeycrashwatcher;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.core.UiWatcher;

public class HockeyWatcher implements UiWatcher {
	public boolean checkForCondition() {
		// HockeyApp update popup
		// Update Available
		// Show information about the new update?
		// Dismiss
		// Show
		UiObject updateTitle = new UiObject(new UiSelector().text("Update Available"));
		if (updateTitle.exists()) {
			System.out.println("Handling Update Available");
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
			System.out.println("Handling Crash");
			UiObject dismissButton = new UiObject(new UiSelector().text("Send"));
			try {
				dismissButton.click();
				return true;
			}
			catch (UiObjectNotFoundException e) {
				return false;
			}
		}

		UiObject stoppedTitle = new UiObject(new UiSelector().text("Unfortunately, TODAY has stopped."));
		if (stoppedTitle.exists()) {
			System.out.println("Handling Has Stopped");
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
