GLOBAL strToInt

section .text 

strToInt:
	MOV r11, 0;	for input please use big letters (A , B , C ...)
	MOV r8, 0;   	move value 0 to r8 for the first scope of calculation formula
	MOV bl, [rdi];	move value in first 8 BIts in bl
	CMP rsi,2;
	JL .error;
	CMP rsi,36;
	JG .error;	
	CMP bl, '-';  	test if negative number
	JE .flag;	if - -->.flag
	JMP .turn;	jump to .turn
	

.turn:
	CMP bl, '0';	   comparing with different numbers to transfer value in decimal system
	JE .null;	   when it's equal to some number - jump to needed function			
	CMP bl, '1';
	JE .1;
	CMP bl, '2';
	JE .2;
	CMP bl, '3';
	JE .3;
	CMP bl, '4';
	JE .4;
	CMP bl, '5';
	JE .5;
	CMP bl, '6';
	JE .6;
	CMP bl, '7';
	JE .7;
	CMP bl, '8';
	JE .8;
	CMP bl, '9';
	JE .9;
	CMP bl, 'A';
	JE .10;
	CMP bl, 'B';
	JE .11;
	CMP bl, 'C';
	JE .12;
	CMP bl, 'D';
	JE .13;
	CMP bl, 'E';
	JE .14;
	CMP bl, 'F';
	JE .15;
	CMP bl, 'G';
	JE .16;
	CMP bl, 'H';
	JE .17;
	CMP bl, 'I';
	JE .18;
	CMP bl, 'J';
	JE .19;
	CMP bl, 'K';
	JE .20;
	CMP bl, 'L';
	JE .21;
	CMP bl, 'M';
	JE .22;
	CMP bl, 'N';
	JE .23;
	CMP bl, 'O';
	JE .24;
	CMP bl, 'P';
	JE .25;
	CMP bl, 'Q';
	JE .26;
	CMP bl, 'R';
	JE .27;
	CMP bl, 'S';
	JE .28;
	CMP bl, 'T';
	JE .29;
	CMP bl, 'U';
	JE .30;
	CMP bl, 'V';
	JE .31;
	CMP bl, 'W';
	JE .32;
	CMP bl, 'X';
	JE .33;
	CMP bl, 'Y';
	JE .34;
	CMP bl, 'Z';
	JE .35;
.add:
	CMP r9, rsi;	compare number with basis
	JAE .error;	if >= -->.error
	MUL r8;  	rax = rax * r8; 
	ADD rax, r9;	rax = rax + r9
	INC rdi;	rdi = rdi + 1
	MOV bl, [rdi]; 	move the value of the next adress into bl
	CMP bl, 0;	comparing if it end of the line
	JE .end;	if yes --> end
	MOV r8, rsi;    r8 was 0 for the first expression, that is different from others. We decided to assign each time basis, but not to write special function for the first scopes.
	JMP .turn;	recursion
	
.error:
	MOV rax, 0;	rax = 0
	RET
	
.flag:	
	MOV r11, 1; 	set negative flag 1
	JMP .turn;	jump .turn

.negative:	
	IMUL rax, (-1);	rax = -rax
	RET

.end:	
	CMP r11, 1;	test if negative flag true
	JE .negative; 	yes --> .negative	
	RET;



.null:
	MOV r9, 0; 	assign decimal value for the number 	   
	JMP .add;	jump to .add

.1:
	MOV r9, 1;
	JMP .add;

.2:
	MOV r9, 2;
	JMP .add;

.3:
	MOV r9, 3;
	JMP .add;
.4:
	MOV r9, 4;
	JMP .add;
.5:
	MOV r9, 5;
	JMP .add;
.6:
	MOV r9, 6;
	JMP .add;
.7:
	MOV r9, 7;
	JMP .add;
.8:
	MOV r9, 8;
	JMP .add;

.9:
	MOV r9, 9;
	JMP .add;

.10:
	MOV r9, 10;
	JMP .add;
.11:
	MOV r9, 11;
	JMP .add;
.12:
	MOV r9, 12;
	JMP .add;
.13:
	MOV r9, 13;
	JMP .add;
.14:
	MOV r9, 14;
	JMP .add;
.15:
	MOV r9, 15;
	JMP .add;
.16:
	MOV r9, 16;
	JMP .add;
.17:
	MOV r9, 17;
	JMP .add;
.18:
	MOV r9, 18;
	JMP .add;
.19:
	MOV r9, 19;
	JMP .add;
.20:
	MOV r9, 20;
	JMP .add;
.21:
	MOV r9, 21;
	JMP .add;
.22:
	MOV r9, 22;
	JMP .add;
.23:
	MOV r9, 23;
	JMP .add;
.24:
	MOV r9, 24;
	JMP .add;
.25:
	MOV r9, 25;
	JMP .add;
.26:
	MOV r9, 26;
	JMP .add;
.27:
	MOV r9, 27;
	JMP .add;
.28:	
	MOV r9, 28;
	JMP .add;
.29:	
	MOV r9, 29;
	JMP .add;
.30:
	MOV r9, 30;
	JMP .add;
.31:
	MOV r9, 31;
	JMP .add;
.32:
	MOV r9, 32;
	JMP .add;
.33:
	MOV r9, 33;
	JMP .add;
.34:
	MOV r9, 34;
	JMP .add;
.35:
	MOV r9, 35;
	JMP .add;




