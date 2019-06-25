#include<stdio.h>
#include<string.h>

char *MakeCRC(char *BitString){
	static char Res[17];
	char CRC[16];
	int i;
	char DoInvert;
	for(i=0;i<16;++i){
		CRC[i]=0;
	}
	for(i=0;i<strlen(BitString);++i){
		DoInvert=('1'==BitString[i])^CRC[15];
		CRC[15]=CRC[14]^DoInvert;
		CRC[14]=CRC[13];
		CRC[13]=CRC[12];
		CRC[12]=CRC[11];
		CRC[11]=CRC[10];
		CRC[10]=CRC[9];
		CRC[9]=CRC[8];
		CRC[8]=CRC[7];
		CRC[7]=CRC[6];
		CRC[6]=CRC[5];
		CRC[5]=CRC[4];
		CRC[4]=CRC[3];
		CRC[3]=CRC[2];
		CRC[2]=CRC[1]^DoInvert;
		CRC[1]=CRC[0];
		CRC[0]=DoInvert;
	}
	for(i=0;i<16;++i){
		Res[15-i]=CRC[i] ?'1':'0';
	}
	Res[16]=0;
	return Res;
}

int main(int argc, char *argv[]){
	char*Data,*Result;
	Data="0011111101111110";
	Result=MakeCRC(Data);
	printf("CRC of [%s] is [%s] with P=[11000000000000101]\n",Data,Result);
}



