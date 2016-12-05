# WifiState

Quick concept for connecting Wi-Fi and error status animation。

&#160; &#160; &#160; &#160;![tu.gif](https://github.com/GreendaMi/WifiState/blob/master/preview%5B3%5D.gif?raw=true "")

&#160; &#160; &#160; &#160;用于显示Wifi状态的视图，分为连接中，连接成功，和连接失败。可以设定视图宽度（高度自动计算），前景色和背景色。
[效果设计来自material](https://material.uplabs.com/posts/connecting-and-error-state-wi-fi-animation)。

&#160; &#160; &#160; &#160;![tu.gif](https://github.com/GreendaMi/WifiState/blob/master/wifi2.gif?raw=true "")

引入依赖：
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
 dependencies {
	        compile 'com.github.GreendaMi:WifiState:1,0'
	}
```

xml中使用
```xml
    <top.greendami.wifistate.WifiState
        android:id="@+id/wifiState"
        android:layout_width="300dp"
        android:layout_height="300dp"
        android:background="#00000000"
        />
```
java代码
```java
mWifiState =(WifiState) findViewById(R.id.wifiState);
//设置状态
mWifiState.setSTATE(WifiState.STATE.OK);
mWifiState.setSTATE(WifiState.STATE.LOADING);
mWifiState.setSTATE(WifiState.STATE.ERROR);
```
