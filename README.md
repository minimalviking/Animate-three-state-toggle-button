# Animated three state toggle button

![](http://i.imgur.com/ScRsR9b.gif)

A button with 3 states, each state can have custom:
* text
* text color (defaults to black)
* background color (defaults to transparent)
* border color (defaults to transparent)

Additionaly, you can configure following global attributes:
* length of animation between states (200ms by default)
* border width (0 by default)
* corner radius (0 by default)

Here’s the xml configuration used in the gif above:
```
<com.minimalviking.animatedthreestatetogglebutton.AnimatedThreeStateToggleButton
		android:id="@+id/button"
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"
		android:gravity="center"
		android:textSize="20dp"
		custom:animation_length="450"
		custom:border_width="5"
		custom:color_bg0="@android:color/white"
		custom:color_bg1="#3955A8"
		custom:color_bg2="#21D381"
		custom:color_border0="#3955A8"
		custom:color_text0="#3955A8"
		custom:color_text1="@android:color/white"
		custom:color_text2="@android:color/white"
		custom:corner_radius="45"
		custom:text0="Follow"
		custom:text1="Following"
		custom:text2="Chat" />
```

##Installation
Add it in your root build.gradle at the end of repositories:
```
allprojects {
	repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

Add the dependency
```
dependencies {
	        compile 'com.github.minimalviking:Animated-three-state-toggle-button:1.0'
	}
```



