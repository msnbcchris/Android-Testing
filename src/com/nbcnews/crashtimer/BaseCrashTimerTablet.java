package com.nbcnews.crashtimer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

import com.nbcnews.common.watcher.HockeyWatcher;
import com.nbcnews.common.util.AppUtility;

class WeDoneCrashed extends Exception
{
    //Parameterless Constructor
    public WeDoneCrashed () {}
}

public class BaseCrashTimerTablet extends UiAutomatorTestCase {
	private String appName;
	private String appPackage;
	private String mainScrollViewID;
	private boolean isMainScrollHorizontal;
	private String thumbSelectorID;
	private String coverOpenerID;
	private String slideshowBackDesc;
	private String drawerOpenerID;
	private String leftDrawerID;
	private boolean changeSections;
	private boolean printDebug;
	
	public BaseCrashTimerTablet(String appName, String appPackage, String mainScrollViewID, boolean isMainScrollHorizontal, String thumbSelectorID, String coverOpenerID, String slideshowBackDesc, String drawerOpenerID, String leftDrawerID, boolean changeSections, boolean printDebug) {
		this.appName = appName;
		this.appPackage = appPackage;
		this.mainScrollViewID = mainScrollViewID;
		this.isMainScrollHorizontal = isMainScrollHorizontal;
		this.thumbSelectorID = thumbSelectorID;
		this.coverOpenerID = coverOpenerID;
		this.slideshowBackDesc = slideshowBackDesc;
		this.drawerOpenerID = drawerOpenerID;
		this.leftDrawerID = leftDrawerID;
		this.changeSections = changeSections;
		this.printDebug = printDebug;
	}
	   public void testLaunchAndIdle() throws UiObjectNotFoundException {
		   //
		   HockeyWatcher watcher = new HockeyWatcher(this.appName);
		   this.getUiDevice().registerWatcher("TheWatcher", watcher);
		   
		   //
		   long startTime;
		   UiScrollable mainScrollView;
//		   UiCollection thumbCollection;
		   String crashTime;
		   long minuteCounter;
		   boolean isScrollForward;
		   Random rn = new Random();

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
			this.warmUp();
	   
		   // loop thru 5 times
		   for (int testingRound = 0; testingRound < 5; testingRound++)
		   {
			   AppUtility util = new AppUtility();
			   util.launchApp(this.appName);
		   
			   this.sleep(5000);
			   
			   // Get the horizontal scrollable view and start swiping
			   mainScrollView = new UiScrollable(new UiSelector().resourceId(this.appPackage + ":" + this.mainScrollViewID));
			   if (this.isMainScrollHorizontal) { 
				   mainScrollView.setAsHorizontalList();
			   }
			   else {
				   mainScrollView.setAsVerticalList();
			   }

			   /*
			   //
			   thumbCollection = new UiCollection(new UiSelector().resourceId("com.nbcnews.today:id/gridItemThumbnailContainer"));
			   System.out.println("Thumbs: " + thumbCollection.getChildCount());
			   System.out.println("class: " + thumbCollection.getChildByInstance(new UiSelector(), 1).getClassName());
			   System.out.println("cont-desc: " + thumbCollection.getChildByInstance(new UiSelector(), 1).getContentDescription());
			   UiCollection chillin = new UiCollection(new UiSelector().className("android.widget.LinearLayout"));
			   System.out.println("Scrollable children: " + chillin.getChildCount());
			   for (int c = 0; c < chillin.getChildCount(); c++)
			   {
				   System.out.println("Child " + c + ": " + chillin.getChildByInstance(new UiSelector(), c).getBounds().toShortString());
				   System.out.println("Child " + c + ": " + chillin.getChildByInstance(new UiSelector(), c).getClassName());
			   }
*/
			   
			   minuteCounter = 1;

			   // we are assuming the app will crash at some point in this loop
			   // mark start time
			   startTime = System.currentTimeMillis();
			   
			   System.out.println("------------------------------------------------------------------");
			   System.out.println("Starting round " + (testingRound + 1) + " of 5 at " + this.getCurrentTimestampPrintable());

			   try
			   {
				   // this loop ends only when the app crashes.
				   while (true)
				   {
					   // Check if app is alive (has not crashed)
					   this.checkPulse();
	
					   // print heartbeat
					   if ((System.currentTimeMillis() - startTime) > (minuteCounter * 60 * 1000))
					   {
						   System.out.println("Crash-free in round " + (testingRound + 1) + " after " + minuteCounter + " minutes...");
						   minuteCounter++;
					   }
					   
					   if (this.changeSections)
					   {
						   // select a section
						   // Check if app is alive (has not crashed)
						   this.checkPulse();
						   UiObject drawerButton = new UiObject(new UiSelector().resourceId(this.appPackage + ":" + this.drawerOpenerID));
						   drawerButton.click();
					   
						   UiCollection sectionList = new UiCollection(new UiSelector().resourceId(this.appPackage + ":" + "id/sectionList").instance(3));
//							UiCollection sectionList = (UiCollection)(new UiCollection(new UiSelector().resourceId("com.zumobi.msnbc:id/sectionText").text("SECTIONS"))).getFromParent(new UiSelector().resourceId(this.appPackage + ":" + "id/sectionList"));
						   int childCount = sectionList.getChildCount(new UiSelector().className("android.widget.TextView"));
						
						   UiObject child;
						   int childIndex = rn.nextInt(childCount);
						   child = sectionList.getChild(new UiSelector().className("android.widget.TextView").instance(childIndex));
//							System.out.println("Tapping section: " + child.getText());
						   // Check if app is alive (has not crashed)
						   this.checkPulse();
						   child.click();
						   this.sleep(1000);
					   }
						
					   // start scrolling cover view
					   isScrollForward = rn.nextBoolean(); 
					   for (int s = rn.nextInt(3) + 1; s > 0; s--)	// + 1 avoids 0 scrolling
					   {
						   if (isScrollForward)
						   {
							   // Check if app is alive (has not crashed)
							   this.checkPulse();
			
							   this.printDiagnostic("Cover scroll forward " + s);
							   mainScrollView.scrollForward(15);
						   }
						   else
						   {
							   // Check if app is alive (has not crashed)
							   this.checkPulse();
			
							   this.printDiagnostic("Cover scroll backward " + s);
							   mainScrollView.scrollBackward(15);
						   }
						   this.sleep(1000);
					   }
	
					   UiSelector thumbSelector = new UiSelector().resourceId(this.appPackage + ":" + this.thumbSelectorID);
					   int inst = rn.nextInt(10);
					   int maxTries = 10;
					   UiObject aThumb = new UiObject(thumbSelector.instance(inst));
					   while ((! aThumb.exists()) && maxTries != 0)
					   {
						   maxTries--;
	//					   this.printDiagnostic("out of range: " + inst);
						   inst = rn.nextInt(10);
						   aThumb = new UiObject(thumbSelector.instance(inst));
					   }
					   
					   if (aThumb.exists())
					   {
						   // Check if app is alive (has not crashed)
						   this.checkPulse();
		
						   this.printDiagnostic("tapping tile #: " + inst);
						   aThumb.click();
						   this.sleep(5000);
						   
						   // now, depending on what screen we went to (story or ss), swipe around then leave.
						   UiObject coverOpener = new UiObject(new UiSelector().resourceId(this.coverOpenerID));
			
						   // Story - Swipe around and leave
						   //UiObject storyView = new UiObject(new UiSelector().resourceId("com.nbcnews.today:id/storyComponents"));
						   UiScrollable storyView = new UiScrollable(new UiSelector().resourceId(this.appPackage + ":id/storyPager"));	//scrollable
						   if (storyView.exists())
						   {
							   // scroll down a bit
							   storyView.setAsVerticalList();
							   for (int s = rn.nextInt(5) + 1; s > 0; s--)	// + 1 avoids 0 scrolling
							   {
								   // Check if app is alive (has not crashed)
								   this.checkPulse();
				
								   this.printDiagnostic("Story scroll down... " + s);
								   storyView.scrollForward(15);
								   this.sleep(1000);
							   }
	
							   // scroll over some
							   storyView.setAsHorizontalList();
							   isScrollForward = rn.nextBoolean();
							   for (int s = rn.nextInt(5) + 1; s > 0; s--)	// + 1 avoids 0 scrolling
							   {
								   if (isScrollForward)
								   {
									   // Check if app is alive (has not crashed)
									   this.checkPulse();
					
									   this.printDiagnostic("Story scroll forward... " + s);
									   storyView.scrollForward(15);
								   }
								   else
								   {
									   // Check if app is alive (has not crashed)
									   this.checkPulse();
					
									   this.printDiagnostic("Story scroll backward... " + s);
									   storyView.scrollBackward(15);
								   }
								   this.sleep(3000);
							   }
							   
							   // Check if app is alive (has not crashed)
							   this.checkPulse();
			
							   this.printDiagnostic("Dismissing Story");
							   coverOpener.click();
						   }
						   
						   //Link out
						   UiScrollable webStoryView = new UiScrollable(new UiSelector().resourceId(this.appPackage + ":id/webView"));	//scrollable
						   if (webStoryView.exists())
						   {
							   // scroll down a bit
							   webStoryView.setAsVerticalList();
							   for (int s = rn.nextInt(5) + 1; s > 0; s--)	// + 1 avoids 0 scrolling
							   {
								   // Check if app is alive (has not crashed)
								   this.checkPulse();
				
								   this.printDiagnostic("WebStory scroll down... " + s);
								   webStoryView.scrollForward(15);
								   this.sleep(1000);
							   }

							   // Check if app is alive (has not crashed)
							   this.checkPulse();
			
							   this.printDiagnostic("Dismissing WebStory");
							   coverOpener.click();
						   }
						   
						   // SlideShow - Swipe through and leave
						   UiObject slideshowBack = new UiObject(new UiSelector().description(this.slideshowBackDesc));
						   if (slideshowBack.exists())
						   {
							   // Check if app is alive (has not crashed)
							   this.checkPulse();
			
//							   this.printDiagnostic("Dismissing Slideshow");
							   System.out.println("Dismissing Slideshow");
							   slideshowBack.click();
						   }
						   
						   // Video - Just dismiss the video
						   UiObject videoView = new UiObject(new UiSelector().className("android.widget.VideoView"));
						   UiObject videoPullHandle = new UiObject(new UiSelector().resourceId(this.appPackage + ":id/pullHandle"));
						   if (videoView.exists())
						   {
							   // Check if app is alive (has not crashed)
							   this.checkPulse();
			
							   this.printDiagnostic("Dismissing Video");
//							   System.out.println("Dismissing Video");
							   if (videoPullHandle.exists())
							   {
								   videoPullHandle.click();
							   }
							   else
							   {
								   this.getUiDevice().pressBack();
							   }
						   }
					   }
				   
					   // Check if app is alive (has not crashed)
					   this.checkPulse();
				   }
			   }
			   catch (WeDoneCrashed e)
			   {
				   ;
			   }
			   
			   // if we get to here, the app has ceased to exist
			   crashTime = this.formatTime(System.currentTimeMillis() - startTime);
			   System.out.println("CRASH ENCOUNTERED AT: " + crashTime + " into round " + (testingRound + 1));
			   System.out.println("Round " + (testingRound + 1) + " of 5 ended at " + this.getCurrentTimestampPrintable());
			   System.out.println("------------------------------------------------------------------");
		   }

	   }
	   
	   // call this method to prepare a freshly installed app for testing
	   // it's mainly needed to dismiss the left drawer that may appear on launch of a freshly installed app
	   private void warmUp() throws UiObjectNotFoundException
	   {
		   // launch the app
		   AppUtility util = new AppUtility();
		   util.launchApp(this.appName);
	   
//		   this.sleep(5000);
		   
		   UiObject leftDrawer = new UiObject(new UiSelector().resourceId(this.appPackage + ":" + this.leftDrawerID));
		   
		   // try once every second for 25 tries to look for the left drawer and dismiss it
		   for (int i = 0; i < 25; i++)
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
		   System.out.println("warm up complete. exiting app...");
		   // this line should exit the app
		   this.getUiDevice().pressBack();
	   }
	   
	   private String formatTime(long millis)
	   {
		   long second = (millis / 1000) % 60;
		   long minute = (millis / (1000 * 60)) % 60;
		   long hour = (millis / (1000 * 60 * 60)) % 24;

		   return String.format("%02d:%02d:%02d", hour, minute, second);
	   }
	   
	   private String getCurrentTimestampPrintable()
	   {
		   DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		   Date date = new Date();
		   return dateFormat.format(date);
	   }
	   
	   private void checkPulse() throws WeDoneCrashed
	   {
		   UiObject theApp = new UiObject(new UiSelector().packageName(this.appPackage));
		   if (!theApp.exists())
			   throw new WeDoneCrashed();
		   
		   // here check if the section list is displayed (as indicated by the presence of the beak)
		   // sometimes tapping a tile will accidentally hit the category selector
		    UiObject beak = new UiObject (new UiSelector().resourceId(this.appPackage + ":id/beakTop"));
		    if (beak.exists())
		    	this.getUiDevice().pressBack();
		    	
	   }
	   
	   private void printDiagnostic(String msg)
	   {
		   if (this.printDebug) 
			   System.out.println(msg);
	   }
}
