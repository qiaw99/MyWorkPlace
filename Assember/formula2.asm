GLOBAL formula

section .data
	two:	
		DQ 2.0
	three:	
		DQ 3.0
	four:
		DQ 4.0
	eight:
		DQ 8.0

section .text

formula:
	ADDSD XMM0, XMM1;	XMM0 ist wie rax. XMM0 = a + b
	SUBSD XMM2, XMM3;	XMM2 = c - d
	MULSD XMM0, XMM2;	XMM0 = (a+b)*(c-d)
	MULSD XMM4, [eight];	XMM4=XMM4 * 8
	MULSD XMM5, [four];	XMM5 = XMM5 * 4
	DIVSD XMM6, [two];	XMM6 = XMM6 / 2
	DIVSD XMM7, [four];	XMM7 = XMM7 / 4
	ADDSD XMM4, XMM5;	XMM4 = XMM4 + XMM5
	SUBSD XMM4, XMM6;	XMM4 = XMM4 - XMM6
	ADDSD XMM4, XMM7;	XMM4 = XMM4 + XMM7 
	MULSD XMM0, XMM4;	XMM0 = XMM0 * XMM4
	DIVSD XMM0, [three];	XMM0 = XMMO / 3
	RET; 			return XMM0

;Integer values and memory addresses are returned in the EAX register, 
floating point values in the ST0 x87 register. Registers EAX, ECX, and EDX are caller-saved,
and the rest are callee-saved. The x87 floating point registers ST0 to ST7 must be empty (popped or freed) 
when calling a new function, and ST1 to ST7 must be empty on exiting a function. ST0 must also be empty 
when not used for returning a value. (Return XMM0, und maybe 6 zahlen nach Komma - check)
