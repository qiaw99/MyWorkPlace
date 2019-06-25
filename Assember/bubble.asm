GLOBAL sort
	
section .text

sort:
	; rdi - length of the array
	; rsi - array	
	MOV r8, 0;			r8=0
	MOV r8, rdi;  			r8 ist i
	JMP .outerLoop;			

.outerLoop:	
	CMP r8, 1;			comparing r8 with 1
	DEC r8;	     			i--	
	MOV r9, 0;  			r9 ist j = 0
	JG .innerLoop;			if r9 > 0, --> .innerLoop
	JMP .end;			otherwise --> .end

.innerLoop:
	CMP r9, r8;  			Comparing j with i-1
	JGE .outerLoop;			if j >= i --> .outerLoop
	MOV r11, r9;			r11 = j
	INC r11;        		r11 = j + 1
	MOV r10, [rsi + r9 * 8];    	r10 = value of A[j]
	CMP r10, [rsi + r11 * 8];   	cmp A[j] mit A[j + 1]
	JG .swap;			if r10 bigger --> .swap
	INC r9;   			j + 1	
	JMP .innerLoop;			

.swap:
	XCHG r10, [rsi + r11 * 8];      A[j + 1] = Value of A[j]
	MOV [rsi + r9 * 8], r10;        A[j] = Value of A[j + 1]
	INC r9;				r9++
	JMP .innerLoop;
			
.end:
	RET;
