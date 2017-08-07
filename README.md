# Switches 
### 自定义多个多开关
---

 要求 最低版本API 14(Android 4.0)版本以上

 效果预览<br/>
 <br/>
  ![image](20170807103954.gif)
  
### 介绍

   这是一个通用多按钮切换控件，支持对按钮文字，同时包括按钮、背景、字体等颜色设置，包括点击切换事件等，帮助大家在需要多个按钮的情况下使用

### 使用

#### 1.引用组件
 
To get a Git project into your build:
Step 1. Add the JitPack repository to your build file

gradle
Add it in your root build.gradle at the end of repositories:

	allprojects {
	   repositories {
         ...
         maven { url 'https://jitpack.io' }
       }
	}
 Step 2. Add the dependency

	dependencies {
	   compile 'com.github.yourbelief:switches:1.0.0'
	}

#### 2.属性设置
    <com.custom.switches.SwitchView
        app:border_color="#cccccc"//背景颜色
        app:btn_color="#cacaca"//按钮颜色
        app:text_color="#bcbcbc"//字体颜色
        android:id="@+id/my_switch"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="@android:color/background_dark" />
    
   设置按钮的个数同时文字<br>
   switchView.setStatus(new String[]{"备用","备用","正常"});
   
   设置按钮的状态切换
   
       switchView.setOnClickStateChange(new SwitchView.OnClickStateChange() {
          @Override
          public void onStateChange(int state, String statusText) {
              Toast.makeText(getApplicationContext(),statusText,Toast.LENGTH_LONG).show();
          }
        });

 
  