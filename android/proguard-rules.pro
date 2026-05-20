# Retrofit rules
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature, InnerClasses, EnclosingMethod

# Gson rules
-keepattributes *Annotation*
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Hilt rules
-keep class * extends class com.google.dagger.hilt.android.internal.managers.ViewComponentManager$FragmentComponentBuilder { *; }

# Room rules
-keep class * extends androidx.room.RoomDatabase
-dontwarn javax.annotation.processing.**
