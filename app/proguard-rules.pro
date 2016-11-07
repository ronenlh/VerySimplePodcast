# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-dontwarn rx.**

-dontwarn okio.**

-dontwarn com.squareup.okhttp.*

-dontwarn retrofit.appengine.UrlFetchClient

-dontwarn org.simpleframework.xml.stream.*


-keep class retrofit.** { *; }

-dontwarn retrofit2.Platform$Java8

-keepclasseswithmembers class * {

@retrofit.http.* <methods>; }

-keepattributes Signature
-keepattributes *Annotation*

# This rule will properly ProGuard all the model classes in
# the package com.yourcompany.models. Modify to fit the structure
# of your app.
-keepclassmembers class com.ronen.studio08.verysimplepodcast.model.FeedSnippet {
  *;
}
