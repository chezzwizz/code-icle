/* MIT License
 * 
 * Copyright (c) 2021 Chuck
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.awt.*;
import java.awt.event.*;

/* AWT for Java™ Graphics Noobs.
 * 
 * If you have worked in any API with graphical layouts, you will most likely have some
 * preference about vernacular and the way you orient your contextual thinking. You are
 * better prepared if you try to forget that and boil down your understanding to the more
 * low level concepts of graphics rendering.
 * 
 * For the most part, or at least as far as I have been able to find, the Oracle™ Java™
 * reference documentation for the API tends to use some common field terms such as
 * raster, vector, screen buffer, image buffer, screen, printer, etc. You will likely
 * recognize many of the terms as they are commonly abstracted in many graphics APIs in,
 * similar fashion. But there tend to be slight differences in the object, or rather class
 * notation preferences. Since I am more familiar with the Win32 platform API, and being 
 * that I primarily write my applications on a Windows machine/platform, I will tend towards
 * using Win32 API vernacular where there is any ambiguity for low level concepts, and .NET
 * vernacular where more high level object descriptions is appropriate. Keeping in mind
 * There are a great many differences between how Java™ and .NET go about implementing
 * various graphics constructs. flagrantly switching between the two is not for the
 * faint of heart.
 * 
 * For instance, the Oracle™ Java™ (official) APIs for AWT have several objects that are
 * needed to create an instance of a "Frame" object. A Frame being the base level object
 * for a "window" or application "frame". Where as in Win32 there is a series of data
 * structures and system API procedure calls which form a "window". Similarly, in .NET,
 * a "form" is a "window" that is created from an object which populates the lower level
 * data structures using properties or members that the user can set to customize the
 * various configuration elements of a "window". Essentially, because Java™ is intended to be
 * widely adapted for any and all possible platforms, there may be attempts to abstract
 * away from "conflict" terms or potentially to adapt to a more widely accepted
 * international standard for UNIX systems known as POSIX. And while POSIX includes
 * considerations for Windows platforms, it seems to be more often associated with UNIX and
 * variants. You can sort out the details as you see necessary.
 * 
 * In the following application object, I will try to explain the various classes and objects
 * needed to create a basic application frame used to build on for a Java™ GUI application.
 */


/**
 * Our primary application class. One such object is instantiated for our application process
 * and calls our member function <code>main</code> as the default entry point. Once we are in
 * <code>main</code> our instructions will layout the data necessary to create a frame object
 * which will then render as our visual GUI on our desktop of choice.
 * 
 * @author Cid
 *
 */
public class AppFrame{
	
	/* For the most part, the API calls that we need to make should be consistent across all
	 * platforms for which a Java™ virtual machine is implemented. The entire purpose for such
	 * a high level abstraction is in fact to make it seamless between platforms, and as such
	 * once you learn how to do it for your platform of choice, there are likely to only be
	 * very few instances where some special tweaking needs to be made for other platforms.
	 * 
	 */
	
	/**
	 * Our always required entry point with an include String array of command line arguments.
	 * 
	 * @param args Our command line arguments passed in from the JVM.
	 */
	public static void main(String[] args) {
		
		/* ** Graphics Environments **
		 * One of the first objects we need is one which Java™ uses to organize the various types
		 * of graphical devices that can co-exist on any given platform. Since your screen is not
		 * the only type of device that can render graphical output, it is possible that there are
		 * several different "rendering capable" devices which you can use with similar constructs
		 * with Java™.
		 * 
		 * Now that doesn't mean that just because a particular environment has a device that such
		 * a device can be used to render any construct that uses such, but for the most part, you
		 * can't know until you try! (I wonder if a frame can be rendered directly to the printer...)
		 * 
		 * For the purpose of accessing the collection of various devices, we first need to check our
		 * Java™ VM factory for available devices. To do that, we use the `GraphicsEnvironment` class.
		 * This class provides a member function to get an object reference to a `GraphicsEnvironment`
		 * object. Since the class is abstract, it is possible to extend this class and create a custom
		 * environment, in case we have the need for a custom implementation. For our intents and purpose,
		 * we can just call on `GraphicsEnvironment.getLocalGraphicsEnvironment()`.
		 */
		
		GraphicsEnvironment localEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
		/* An additional static method, `isHeadless`, allows us to check for available display, keyboard,
		 * and mouse support in our general graphics environment.
		 */
		boolean graphicsEnvIsHeadless = GraphicsEnvironment.isHeadless();
		
		/* Printing our output to our system output console allows us to see what we got.
		 * On most desktop and lap-top computers, we would expect this result to be `false`. A result of
		 * `true` means that our display, keyboard, and mouse are NOT supported in our graphics environment.
		 */
		System.out.println(String.format("graphicsEnvIsHeadless: %b", graphicsEnvIsHeadless));
		
		/* Now that we have a valid `GraphicsEnvironment` representing our local environment, we are given
		 * several different utilities. The `GraphicsEnvironment` object is not only good for our display,
		 * but also has data related to fonts, the ability to prepare a `Graphics2D` object, data related to
		 * the maximum bounding rectangle as well as the coordinates for the centering of our window, and font
		 * registration and mapping preference for locale specific application fonts. For the time, lets look 
		 * at the following three methods:
		 * 
		 * 		- getScreenDevices
		 * 		- getDefaultScreenDevice
		 * 		- isHeadlessInstance
		 * 
		 * The first two of these deal with getting our available screen devices. Our screen devices are of type
		 * `GraphicsDevice` and calling the `getScreenDevices` method will get us an array of `GraphicsDevice`.
		 * In general, a `GraphicsDevice` object represents virtual and real world graphics handlers which include
		 * both displays and printers. Our `GraphicsEnvironment` however only seems to provide an array of screen
		 * devices which represent our physical and virtual screens. We can also zero in on our default or primary
		 * screen device by using the `getDefaultScreenDevice` method. This allows us to avoid manually iterating
		 * through the collection of available devices to find our primary.
		 * 
		 * You will also note that I included `isHeadlessInstance`. This is similar to our `GraphicsEnvironment.isHeadless()`
		 * method, except this particular method is only available on our object instance. We should still expect that
		 * we get a result of `false` here if support for our display, keyboard, and mouse is available. As a side note,
		 * the Oracle™ Java™ documentation for this method is unclear and would seem to imply that a `true` result indicates
		 * that the device CAN support display, keyboard, and mouse. While the documentation for the static implementation
		 * (`GraphicsEnvironment.isHeadless`) implies that a result of `true` means that display, keyboard, and mouse are NOT
		 * available. Running both on my machine indicates that both returned `false` and my machine has display, keyboard, and
		 * mouse support.
		 */
		
		boolean localEnvIsHeadlessInstance = localEnv.isHeadlessInstance();
		
		System.out.println(String.format("localEnvIsHeadlessInstance: %b", localEnvIsHeadlessInstance));
		
		/* With our `localEnv` object, we can now obtain a reference to our default screen display
		 */
		
		GraphicsDevice defaultScreen = localEnv.getDefaultScreenDevice();
		
		/* As noted previously, our `GraphicsDevice` object is a representation of a screen object which in this case is our
		 * default screen. `GraphicsDevice` objects can also represent printers and potentially other devices as well
		 * (e.g. A car's wind-shield projection HUD.) Each instance of our `GraphicsDevice` itself contains some set of
		 * `GraphicsConfiguration` objects.
		 * 
		 * The `GaphicsConfiguration` object it self describes the capabilities of the `GraphicsDevice` with which it is
		 * associated. According to the Oracle™ Java™ official documentation for JDK 8, this is the representation of
		 * graphical modes and drawing capabilities. On a Microsoft™ Windows™ platform, this represents the displays
		 * selection of resolution and color depths. For X11, `GraphicsConfiguration` objects represent the "visual"s.
		 * 
		 * With our `GraphicsConfiguration` objects, we have the various associated image creation and capability query
		 * methods available. (See documentation for details.)
		 * 
		 * For now, all we are going to do is query our `defaultScreen` device for our default configuration and use it
		 * to create a new `Frame` object.
		 */
		
		Frame appFrame = new Frame(defaultScreen.getDefaultConfiguration());
		
		/* And viola, we have a shiny new Frame/Form/Window/Dialog/AppBorder/... Without a few more calls, we most likely
		 * won't see it rendered to the display. We can safely assume that this instantiation only constructs the object
		 * in memory for us. Once available however, we can now set the size and location of our new Frame, and call a
		 * method to display it on our `defaultScreen` object.
		 */
		
		appFrame.setSize(100, 100);
		appFrame.setLocation(localEnv.getCenterPoint()); // Should position our new `Frame` smack in the center.
		// appFrame.show(); <-- Deprecated... as in too old.
		appFrame.setVisible(true);
		
		/* At this point, we have our window and it will display as we should expect. But we have one problem. That is our
		 * window will not respond to any "events". If we try to close the window without adding the following `WindowListener`,
		 * nothing will happen. By adding a `WindowListener` object, we can perform a dispose call and close the window when
		 * the user clicks the "close" icon or chooses "close" from the context menu.
		 * 
		 * Since `WindowListener` is actually an interface, we can create an inner class which implements all of the required
		 * methods. Each one will report it's call to the console so we know when the call occurs.
		 */
		
		class EventListener implements WindowListener {
			private final String msgFormat = "Called: %s";

						
			@Override
			public void windowOpened(WindowEvent e) {
				System.out.println(String.format(msgFormat, "windowOpened"));
			}

			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println(String.format(msgFormat, "windowClosing"));
				appFrame.dispose();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				System.out.println(String.format(msgFormat, "windowClosed"));
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				System.out.println(String.format(msgFormat, "windowIconified"));
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				System.out.println(String.format(msgFormat, "windowDeiconified"));
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				System.out.println(String.format(msgFormat, "windowActivated"));
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				System.out.println(String.format(msgFormat, "windowDeactivated"));
			}
		}
		
		/* Now we can add the new class to our `appFrame` by calling the `addWindowListener` method.
		 */
		
		appFrame.addWindowListener(new EventListener());
		
		/* [From Java™ Platform Standard Edition 8 reference documentation at http://oracle.com]
		 * Frames are capable of generating the following types of WindowEvents:
		 *	WINDOW_OPENED
		 *  WINDOW_CLOSING: If the program doesn't explicitly hide or dispose the window while processing this event,
		 *  				the window close operation is canceled.
		 *  WINDOW_CLOSED
		 *  WINDOW_ICONIFIED
		 *  WINDOW_DEICONIFIED
		 *  WINDOW_ACTIVATED
		 *  WINDOW_DEACTIVATED
		 *  WINDOW_GAINED_FOCUS
		 *  WINDOW_LOST_FOCUS
		 *  WINDOW_STATE_CHANGED
		 */
		
		/* All said and done, we have a functioning `Frame` which can host our application logic and display it all in an esthetically
		 * pleasing GUI. Looking through the Java™ documentation for AWT, we have several different controls that we can use to populate
		 * our new `Frame` and provide our end user with input options and feedback.
		 */
	}

}
