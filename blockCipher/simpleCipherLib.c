#include <stdio.h>
#include <string.h>

void Enc(int PT[128], int key[10]);
void Dec(int CT[128], int key[10]);
void printState(int state[128]);

// 암호화
void Enc(int PT[128], int key[10]) {
    int num = 0;
    // int statelen = sizeof(PT) / sizeof(int);
    // int keylen = sizeof(key) / sizeof(int);
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
    // int statelen = sizeof(CT) / sizeof(int);
    // int keylen = sizeof(key) / sizeof(int);
    // printf("strlenCT = %d", i);
    while(num < 128){
        for(int i=0; i<10; i++) {
            CT[num] = CT[num] ^ key[i];
            num++;
        }
    }
}
 
// 암호문 출력 함수
void printState(int state[128]) {
    // int statelen = sizeof(state) / sizeof(int);
    for (int i=0; i<128; i++) {
        printf("%d ", state[i]);
    } printf("\n");
}

void printStateASCII(int state[128]) {
    // int statelen = sizeof(state) / sizeof(int);
    for (int i=0; i<128; i++) {
        printf("%d ", state[i]);
    } printf("\n");
}

int main() {
    char key[] = "mykeymykey";
    int PT[128];
    PT[0] = 80;
    PT[1] = 80;
    PT[2] = 80;
    PT[3] = 80;
    PT[4] = 80;
    for (int i = 5; i < 128; i++) {
        PT[i] = 0; 
    }
    int intkey[10];
    for (int i = 0; i < 10; i++) {
        intkey[i] = key[i];
        // printf("%d", intkey[i]);
    }
    Enc(PT, intkey);
    printState(PT);
    Dec(PT, intkey);
    printState(PT);

    return 0;

}