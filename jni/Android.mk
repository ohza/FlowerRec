# Android.mk for compiling sift_test using OpenCV4Android
# Created by Guohui Wang
# Email: robertwgh_at_gmail_com
# Data: 02/25/2014

LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE    := nonfree_prebuilt
LOCAL_SRC_FILES := libnonfree.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := opencv_java_prebuilt
LOCAL_SRC_FILES := libopencv_java.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
# Modify LOCAL_C_INCLUDES with your path to OpenCV for Android.
LOCAL_C_INCLUDES:= /AndroidLibs/OpenCV-2.4.9-android-sdk/sdk/native/jni/include /android-ndk-r10e/sources/cxx-stl/gnu-libstdc++/4.9/libs/armeabi/include /android-ndk-r10e/sources/cxx-stl/gnu-libstdc++/4.9/include
LOCAL_MODULE    := flowerrec
LOCAL_CFLAGS    := -Werror -O3 -ffast-math
LOCAL_LDLIBS    += -llog -ldl 
LOCAL_SHARED_LIBRARIES := nonfree_prebuilt opencv_java_prebuilt
LOCAL_SRC_FILES := FlowerRec.cpp
include $(BUILD_SHARED_LIBRARY)
