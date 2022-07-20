//==================================================================================================================================
//==============================================                        ============================================================
//============================================== [블록암호 - SimpleCipher] ===========================================================
//==============================================                        ============================================================
//==================================================================================================================================

#include <stdio.h>
#include <string.h>

void Enc(int PT[128], int key[10]);
void Dec(int CT[128], int key[10]);
void printState(int state[128]);

// 암호화
void Enc(int PT[128], int key[10]) { // 97 ^ 
    int num = 0;
    printf("\nEnc 내부\n");
    while(num < 128) {
        for(int i=0; i<10; i++) {
            PT[num] = PT[num] ^ key[i];
            num++;
        }
    }
}

// 복호화
void Dec(int CT[128], int key[10]) {
    int num = 0;
    while(num < 128){
        for(int i=0; i<10; i++) {
            CT[num] = CT[num] ^ key[i];
            num++;
        }
    }
}
 
// 암호문 출력 함수
void printState(int state[128]) {
    for (int i=0; i<128; i++) {
        printf("%d ", state[i]);
    } printf("\n");
}

void printStateASCII(int state[128]) {
    for (int i=0; i<128; i++) {
        printf("%c ", state[i]);
    } printf("\n");
}
void printState_AES(unsigned char state[16]) {
    for(int i=0; i<16; i++) {
        printf("%02X ", state[i]);
    } printf("\n");
}
