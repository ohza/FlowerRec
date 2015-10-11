#include <jni.h>
#include <android/log.h>
#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/features2d/features2d.hpp>

#include <iostream>

#include <opencv2/nonfree/nonfree.hpp>

using namespace cv;
using namespace std;

#define  LOG_TAG    "nonfree_jni_demo"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)

typedef unsigned char uchar;

int doFlowerRecNative(const char * imgInFile);

extern "C" {
    JNIEXPORT void JNICALL Java_com_example_flowerrec_NonfreeJNILib_doFlowerRec(JNIEnv * env, jobject obj, jstring bitmappath);
};

JNIEXPORT void JNICALL Java_com_example_flowerrec_NonfreeJNILib_doFlowerRec(JNIEnv * env, jobject obj , jstring bitmappath)
{
	const char * imgInFile = env->GetStringUTFChars(bitmappath, 0);
	doFlowerRecNative(imgInFile);
}

int doFlowerRecNative(const char * imgInFile)
{
	const char * imgOutFile = "/sdcard/FlowerRec/img1_res.jpg";

	Mat image;
	image = imread(imgInFile, CV_LOAD_IMAGE_COLOR);
	if(! image.data )
	{
		LOGI("Could not open or find the image!\n");
		return -1;
	}

	vector<KeyPoint> keypoints;
	Mat descriptors;

	// Create a SIFT keypoint detector.
	SiftFeatureDetector detector;
	detector.detect(image, keypoints);
	LOGI("Detected %d keypoints\n", (int) keypoints.size());

	// Compute feature description.
	detector.compute(image,keypoints, descriptors);

	// Store description to "descriptors.des".
	FileStorage fs;
	fs.open("/sdcard/FlowerRec/descriptors.xml", FileStorage::WRITE);
	fs << "Des" << descriptors;
	fs.release();

	LOGI("Done writing descriptors.\n");
	return 0;
}
