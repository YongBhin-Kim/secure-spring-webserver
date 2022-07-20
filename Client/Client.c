#include <jni.h>
#include "Client.h"
#include <stdio.h>
// ========================================================================
// 블록암호 헤더 참조
// ========================================================================

// [SimpleCipher]
// #include "../blockCipher/simpleCipherLib.h"

// [AES]
#include "../blockCipher/aesLib.h"

JNIEXPORT jintArray JNICALL Java_Client_Enc(JNIEnv *env, jobject jobj, jintArray arr2)
{
    jint *byteBuf;
    int size, i;
    jintArray out;
    unsigned char key[] = {0x2b, 0x7e, 0x15, 0x16, 0x28, 0xae, 0xd2, 0xa6, 0xab, 0xf7, 0x16, 0x47, 0x77, 0x11, 0x1e, 0x3c};
    unsigned char EncBuff[16];
    
    out = (*env)->NewIntArray(env, 16);
    size = (*env)->GetArrayLength(env, arr2);
    byteBuf = (*env)->GetIntArrayElements(env, arr2, 0);
    unsigned char tmp[16];
    int tmp2[16];
    for (int i = 0; i < 16; i++) {
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
    printf("[입력 메시지(hex)] \n");
    printState_AES(tmp);

    AES_ECB_Encrypt(tmp, key, EncBuff, 16);
    printf("[암호화(hex)] \n");
    printState_AES(EncBuff);
    for (int i=0; i<16; i++) {
        tmp2[i] = (int)EncBuff[i];
    }

    (*env)->SetIntArrayRegion(env, out, 0, 16, tmp2);
    (*env)->ReleaseIntArrayElements(env, arr2, byteBuf, 0);

    return out;
}


JNIEXPORT jintArray JNICALL Java_Client_Dec(JNIEnv *env, jobject jobj, jintArray arr2)
{
    jint *byteBuf;
    int size, i;
    jintArray out;
    unsigned char DecBuff[16];
    unsigned char key[] = {0x2b, 0x7e, 0x15, 0x16, 0x28, 0xae, 0xd2, 0xa6, 0xab, 0xf7, 0x16, 0x47, 0x77, 0x11, 0x1e, 0x3c};
    out = (*env)->NewIntArray(env, 16);
    size = (*env)->GetArrayLength(env, arr2);
    byteBuf = (*env)->GetIntArrayElements(env, arr2, 0);
    unsigned char tmp[16];
    int tmp2[16];
    for (int i = 0; i < 16; i++) {
        tmp[i] = byteBuf[i];
    }

    AES_ECB_Decrypt(tmp, key, DecBuff, 16);

    printf("[복호화(hex)]\n");
    printState_AES(DecBuff);
    for (int i=0; i<16; i++) {
        tmp2[i] = (int)DecBuff[i];
    }

    (*env)->SetIntArrayRegion(env, out, 0, 16, tmp2);
    (*env)->ReleaseIntArrayElements(env, arr2, byteBuf, 0);

    return out;
}