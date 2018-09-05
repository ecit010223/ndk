/*
 *  Created by zyh on 2018/9/5.
 */

#ifndef NDK_PLUS_H
#define NDK_PLUS_H

int counter;
int readOnly;
int readWrite;

enum{E_ONE=1,E_TWO=2,E_THREE,E_FOUR};
enum Safe{S_ONE = 1, S_TWO = 2, S_THREE, S_FOUR};
enum Unsafe{U_ONE=1,U_TWO=2,U_THREE,U_FOUR};
enum JEnum{J_ONE=1,J_TWO=2,J_THREE,J_FOUR};

struct Point{
    int x;
    int y;
};

void defaultValueFunc(int a=1,int b=2,int c=3){}

void overloadFunc(double d){}
void overloadFunc(int i){}

#endif //NDK_PLUS_H
