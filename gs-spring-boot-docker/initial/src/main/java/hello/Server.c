#include <jni.h>
#include "../hello_Server.h"
#include "hello_Server.h"
#include <stdio.h>
// #include "../blockCipher/blockCipherLib.h"
#include "simpleCipherLib.h"
#include "aesLib.h"

JNIEXPORT jintArray JNICALL Java_hello_Server_Enc(JNIEnv *env, jobject jobj, jintArray arr2)
{
    jint *byteBuf;
    int size, i;
    jintArray out;
    unsigned char key[] = {0x2b, 0x7e, 0x15, 0x16, 0x28, 0xae, 0xd2, 0xa6, 0xab, 0xf7, 0x16, 0x47, 0x77, 0x11, 0x1e, 0x3c};
    unsigned char EncBuff[80];
    // unsigned char DecBuff[80];
    // int intkey[10];
    // for (int i = 0; i < 10; i++) {
        // intkey[i] = key[i];
    // }
    
    out = (*env)->NewIntArray(env, 128);
    size = (*env)->GetArrayLength(env, arr2);
    byteBuf = (*env)->GetIntArrayElements(env, arr2, 0);
    unsigned char tmp[128];
    int tmp2[128];
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
    printf("[입력 메시지(hex)] \n");
    // printState(tmp);
    printState_AES(tmp);

    // Enc(tmp, intkey);
    AES_ECB_Encrypt(tmp, key, EncBuff, 16);
    printf("[암호화(hex)] \n");
    // printState(tmp);
    printState_AES(EncBuff);
    // for (int i = 0; i < 128; i++) {
    //     printf("%d", byteBuf[i]);
    // }
    for (int i=0; i<128; i++) {
        tmp2[i] = (int)EncBuff[i];
    }

    (*env)->SetIntArrayRegion(env, out, 0, 128, tmp2);
    (*env)->ReleaseIntArrayElements(env, arr2, byteBuf, 0);

    return out;
}


JNIEXPORT jintArray JNICALL Java_hello_Server_Dec(JNIEnv *env, jobject jobj, jintArray arr2)
{
    jint *byteBuf;
    int size, i;
    jintArray out;
    unsigned char DecBuff[80];
    // char key[] = "mykeymykey";
    unsigned char key[] = {0x2b, 0x7e, 0x15, 0x16, 0x28, 0xae, 0xd2, 0xa6, 0xab, 0xf7, 0x16, 0x47, 0x77, 0x11, 0x1e, 0x3c};
    // int intkey[10];
    // for (int i = 0; i < 10; i++) {
    //     intkey[i] = key[i];
    // }
    out = (*env)->NewIntArray(env, 128);
    size = (*env)->GetArrayLength(env, arr2);
    byteBuf = (*env)->GetIntArrayElements(env, arr2, 0);
    // int tmp[128];
    unsigned char tmp[128];
    int tmp2[128];
    // printf("[받은 메시지 : C ]\n");
    for (int i = 0; i < 128; i++) {
        tmp[i] = byteBuf[i];
        // printf("%d ", tmp[i]);
    }
    // Dec(tmp, intkey);
    AES_ECB_Decrypt(tmp, key, DecBuff, 16);
    printf("[복호화(hex)]\n");
    // printState(tmp);
    printState_AES(DecBuff);
    for (int i=0; i<128; i++) {
        tmp2[i] = (int)DecBuff[i];
    }

    (*env)->SetIntArrayRegion(env, out, 0, 128, tmp2);
    (*env)->ReleaseIntArrayElements(env, arr2, byteBuf, 0);

    return out;
}