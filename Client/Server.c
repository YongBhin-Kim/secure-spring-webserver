#include <jni.h>
#include "Server.h"
#include <stdio.h>
// #include "../blockCipher/blockCipherLib.h"
#include "../blockCipher/simpleCipherLib.h"

JNIEXPORT jintArray JNICALL Java_Client_Enc(JNIEnv *env, jobject jobj, jintArray arr2)
{
    jint *byteBuf;
    int size, i;
    jintArray out;
    char key[] = "mykeymykey";
    int intkey[10];
    for (int i = 0; i < 10; i++) {
        intkey[i] = key[i];
        // printf("%d", intkey[i]);
    }
    
    out = (*env)->NewIntArray(env, 128);
    size = (*env)->GetArrayLength(env, arr2);
    byteBuf = (*env)->GetIntArrayElements(env, arr2, 0);
    int tmp[128];
    for (int i = 0; i < 128; i++) {
        if (byteBuf[i] > 255) {
            tmp[i] = 0;
        }
        else if (byteBuf[i] < 0) {
            tmp[i] = 0;
        }
        else {
            tmp[i] = byteBuf[i];
        }
    }
    printf("[입력 메시지] \n");
    printState(tmp);

    Enc(tmp, intkey);
    printf("[암호화] \n");
    printState(tmp);
    // for (int i = 0; i < 128; i++) {
    //     printf("%d", byteBuf[i]);
    // }

    (*env)->SetIntArrayRegion(env, out, 0, 128, tmp);
    (*env)->ReleaseIntArrayElements(env, arr2, byteBuf, 0);

    return out;
}


JNIEXPORT jintArray JNICALL Java_Client_Dec(JNIEnv *env, jobject jobj, jintArray arr2)
{
    jint *byteBuf;
    int size, i;
    jintArray out;
    char key[] = "mykeymykey";
    int intkey[10];
    for (int i = 0; i < 10; i++) {
        intkey[i] = key[i];
    }
    
    out = (*env)->NewIntArray(env, 128);
    size = (*env)->GetArrayLength(env, arr2);
    byteBuf = (*env)->GetIntArrayElements(env, arr2, 0);
    int tmp[128];
    // printf("[받은 메시지 : C ]\n");
    for (int i = 0; i < 128; i++) {
        tmp[i] = byteBuf[i];
        // printf("%d ", tmp[i]);
    }
    Dec(tmp, intkey);

    (*env)->SetIntArrayRegion(env, out, 0, 128, tmp);
    (*env)->ReleaseIntArrayElements(env, arr2, byteBuf, 0);

    return out;
}