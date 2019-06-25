GLOBAL formula

section .text

formula:	
	MOV r10, [rsp + 8];	r10 = g
	MOV r11, [rsp + 16];	r11 = h
	MOV rax, 0; 		rax = 0	
	ADD rax, rsi;		rax = b
	ADD rax, rdi;   	rax = b + a
	SUB rdx, rcx;		c = c - d
	IMUL rdx;    		rax = rax * rdx	
	SAL r8, 3;		e = e*8
	SAL r9, 2;		f = f*4
	SAR r10, 1;		g = g/2
	SAR r11, 2;		h = h/4
	ADD r8, r9;		r8 = e*8+f*4
	SUB r8, r10;		r8 = r8-g/2
	ADD r8, r11;		r8 = r8+h/4
	IMUL r8;		rax = rax * r8
	PUSH r12;		saving r12
	MOV r12, 3;		r12=3
	IDIV r12;		rax=rax/3
	POP r12;		loading r12
	RET

