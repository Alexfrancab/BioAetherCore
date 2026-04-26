# Bio-Aether Core - ProGuard Rules

# Mantener clases principales
-keep class com.bioaether.core.** { *; }

# WebView con JavaScript
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebChromeClient {
    public void *(android.webkit.WebView, java.lang.String);
}

# Interfaces JavaScript (si se añaden)
-keepclassmembers class com.bioaether.core.** {
    @android.webkit.JavascriptInterface <methods>;
}

# AndroidX
-keep class androidx.** { *; }
-dontwarn androidx.**

# Material Components
-keep class com.google.android.material.** { *; }
-dontwarn com.google.android.material.**
