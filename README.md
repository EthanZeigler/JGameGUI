# JGameGUI
A simple Java 2D game API

A Java Swing game API designed and created by Ethan Zeigler, class of '16. How to use this API:

Extend JGameGUI and implement its methods. Create a new main method and create a new object of your class with your desired screen size. If you received a template from Mr. Ulmer, this has already been done for you and you will change the WIDTH and HEIGHT variables.

When the game starts, onStart will be invoked. Here, you initialize your variables, set the frame rate using {@link JGameGUI#setFPS(int)}, which can speed up or slow down your game. About 60, which is the default, is good. load image and sound files, and add your Elements to your {@link Window}.

JGameGUI works by the developer adding {@link AbstractElement}s to {@link Window}s. These elements represent anything that can be drawn to the screen such as text, images, and buttons. All of these have pre-made objects for you to use.

TextElement represents written text.  
ImageElement represents a bitmap image.  
ButtonImageElement represents an image as a button. This also requires the image's size as an argument. The image can also be set to null and set to the size of the screen to detect clicks.  
CollidableImageElement has a predefined method for checking to see if two CollidableElements are touching.

{@link Window}s represent a list of Elements to be displayed. In the onStart method, create new elements and add them to the screen using {@link JGameGUI#setWindow(Window)}. {@link Animation}s can be applied to these Elements as well using the animation API, which is well documented and I will not explain here. Note that this is for late-year AP students only. First years will not understand this.

What about sound? Use the sound API. Create a new AudioClip in the onStart method because depending on the size of the file, it can cause lag spikes when loading. Using {@link AudioClip#play()}, the sound file can be played. Be sure to check out the other options including {@link AudioClip#loop()}, which will play forever until told to stop.

On each screen update, the onScreenUpdate method ({@link JGameGUI#onScreenUpdate(JGameGUI)} is invoked. Here you can move your elements around using their set x and y methods, change the shown window, as well as set new animations and image files if necessary. This is the heart of your game.

When the window is closed or you want to end the game, call the {@link JGameGUI#stop()} method which will close the window and shut down the program. As the program shuts down either by the stop method or the window being closed, the onStop method is called. This can be used to save files or other things you want to do. It is a good thing to call {@link AudioClip#dispose()}
