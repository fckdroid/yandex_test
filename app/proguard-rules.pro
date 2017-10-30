# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keep class org.greenrobot.greendao.**
-dontwarn org.greenrobot.greendao.**
-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {

}
-keep class **$Properties
-keep class suhockii.dev.translator.data.repositories.database.** { *; }
-keep class suhockii.dev.translator.data.network.models.** { *; }
-keep class suhockii.dev.translator.ui.base.** { *; }
-ignorewarnings
