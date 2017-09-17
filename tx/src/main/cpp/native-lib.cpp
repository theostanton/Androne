//
// Created by Theo Stanton on 09/09/2017.
//
#include <jni.h>
#include <string>
#include <vector>

#include <android/log.h>
#include <android/looper.h>
#include <android_native_app_glue.h>

#include <pio/gpio.h>
#include <pio/peripheral_manager_client.h>

#include <time.h>

#define NANOS_IN_SECOND 1000000000

static long currentTimeInNanos() {

    struct timespec res;
    clock_gettime(CLOCK_MONOTONIC, &res);
    return (res.tv_sec * NANOS_IN_SECOND) + res.tv_nsec;
}

const char *TAG = "TX";

#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, TAG, __VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN, TAG, __VA_ARGS__)
#define LOG(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)
#define ASSERT(cond, ...) if (!(cond)) { __android_log_assert(#cond, TAG, __VA_ARGS__);}

extern "C"
JNIEXPORT jintArray

JNICALL
Java_com_theostanton_tx_TX_getReadings(
        JNIEnv *env,
        jobject) {

    LOG("getReadings");

    int length = 10;
    std::string GPIO = "BCM4";

    APeripheralManagerClient *client = APeripheralManagerClient_new();
    AGpio *gpio;

    int openResult = APeripheralManagerClient_openGpio(client, GPIO.c_str(), &gpio);
    ASSERT(openResult == 0, "failed to open GPIO: %s", GPIO.c_str());

    int setDirectionResult = AGpio_setDirection(gpio, AGPIO_DIRECTION_IN);
    ASSERT(setDirectionResult == 0, "failed to set direction for GPIO: %s", GPIO.c_str());

    int setEdgeTriggerResult = AGpio_setEdgeTriggerType(gpio, AGPIO_EDGE_BOTH);
    ASSERT(setEdgeTriggerResult == 0, "failed to edge trigger for GPIO: %s", GPIO.c_str());

    int fd;
    int getPollingFdResult = AGpio_getPollingFd(gpio, &fd);
    ASSERT(getPollingFdResult == 0, "failed to get polling file descriptor for GPIO: %s",
           GPIO.c_str());

    ALooper *looper = ALooper_prepare(ALOOPER_PREPARE_ALLOW_NON_CALLBACKS);
    ASSERT(looper, "failed to get looper for the current thread");
    int addFdResult = ALooper_addFd(looper, fd, LOOPER_ID_USER, ALOOPER_EVENT_INPUT, NULL, NULL);
    ASSERT(addFdResult > 0, "failed to add file description to looper");

    long last = currentTimeInNanos();
    jintArray array = env->NewIntArray(20);

    int i = -1;
    while (i < 10) {
        LOG("polling i=%d", i);
        android_poll_source *source;
        long now = currentTimeInNanos();
        long gap = now - last;
        last = now;
        ALooper_pollOnce(2000, NULL, NULL, (void **) &source);
        int ackInterruptResult = AGpio_ackInterruptEvent(fd);
        ASSERT(ackInterruptResult == 0, "failed to ack interrupt");
        if (i == -1 && gap > 5000000) {
            i = 0;
        }
        if (i >= 0) {
            jint elements[] = {gap};
            env->SetIntArrayRegion(array, i, i + 1, elements);
            i += 1;
        }
    }

    ALooper_removeFd(looper, fd);
    AGpio_delete(gpio);
    APeripheralManagerClient_delete(client);

    return array;
}
