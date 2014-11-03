package com.nbcnews.today.uiautomator.hockeycrashwatcher;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HockeyCrashWatcher extends UiAutomatorTestCase {
	   public void testLaunchAndIdle() throws UiObjectNotFoundException {

		   HockeyWatcher watcher = new HockeyWatcher();
		   this.getUiDevice().registerWatcher("TheWatcher", watcher);
		   
		   while (true) {
			   	  // Turn on screen
			      try {
				   	  this.getUiDevice().wakeUp();			    	  
			      }
			      catch (android.os.RemoteException e)
			      {
			    	  System.out.println("Could not wake device");
			    	  return;
			      }

			      // Launch app if not already
			      UiObject packageValidation = new UiObject(new UiSelector()
			         .packageName("com.nbcnews.today"));
			      if (! packageValidation.exists()) {
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
//				      appsView.setAsHorizontalList();
				      UiObject todayApp = appsView.getChildByText(new UiSelector()
				         .className(android.widget.TextView.class.getName()), 
				         "TODAY");
				      todayApp.clickAndWaitForNewWindow();

				      this.sleep(15000);

				      // Continue past the splash screen
				      UiObject continueButton = new UiObject(new UiSelector().text("Continue"));
				      if (continueButton.exists()) {
				    	  continueButton.clickAndWaitForNewWindow();
					      this.sleep(10000);
				      }
				      
			      }
			      // Validate that the package name is the expected one
//			      UiObject packageValidation = new UiObject(new UiSelector()
//			         .packageName("com.nbcnews.today"));
//			      assertTrue("Could not find TODAY app",
//			         packageValidation.exists());
//			      
//			      this.sleep(5000);

			      // Run Monkey Tool
			      Runtime rt = Runtime.getRuntime();
			      System.out.println("starting monkey");
//			      Process monkeytool = new ProcessBuilder()
//			      .command("monkey", "-v", "-p com.nbcnews.today", "--kill-process-after-error", "--pct-syskeys 0", "--throttle 1000", "500")
//			      .redirectErrorStream(true)
//			      .start();
			      Process pr = null;
			      try {
//			    	  pr = rt.exec("monkey -v -p com.nbcnews.today --kill-process-after-error --pct-syskeys 0 --throttle 1000 500");
//			    	  pr = rt.exec("monkey -v -p com.nbcnews.today --kill-process-after-error --pct-syskeys 0 --pct-trackball 0 --throttle 250 500");
			    	  pr = rt.exec("monkey -v -p com.nbcnews.today --kill-process-after-error --pct-syskeys 0 --pct-trackball 0 --pct-nav 0 --pct-majornav 0 --pct-anyevent 0 --throttle 250 500");
			    	  BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			    	  String line = null;
			    	  while ((line = in.readLine()) != null) {
			    		  System.out.println(line);
			    	  }
//			    	  InputStream in = pr.getInputStream();
//			    	  InputStream er = pr.getErrorStream();
//			    	  System.setIn(in);

//				      for (int i = 0; i < 500; i++)
//				      {
//				    	  System.out.println("Seconds to restart: " + (500 - i));
//				    	  this.sleep(1000);
//				    	  UiObject something = new UiObject(new UiSelector().description("TODAY"));
//				    	  something.exists();
//				      }
			      }
			      catch(java.lang.Exception e) {
			    	  assertTrue(false);
			      }
//			      finally {
//			    	  if (pr != null)
//			    		  pr.destroy();
//			      }
//			      System.out.println("ending monkey");

//			      this.sleep(30000);
		   }
	   }
}
