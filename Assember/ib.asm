GLOBAL asm_fib_it
GLOBAL asm_fib_rek

section .text

asm_fib_it:
	MOV r8, 0;	x=0
	MOV r9, 1;	y=1
	MOV r10, 0;	k=0
	CMP rdi, 0;	n==0?		
	JG .while;	n>0 -> .while


.end:	MOV rax, r10;	return k
	RET

.while:
	MOV r8, r9;	x=y
	MOV r9, r10;	y=k
	MOV r10, 0;	k=0
	ADD r10, r9;	k=k+y
	ADD r10, r8;	k=k+x -> k= 0 +x +y
	DEC rdi;	n--
	CMP rdi, 0;	n==0?
	JG .while;	n>0 -> .while
	JMP .end;	spring es zu .end

asm_fib_rek:		
	CMP rdi,3;	vergleichen die Eingabe mit 3
	JL .ende;	wenn < , dann springt es zu .ende
	SUB rdi,1;	rdi=rdi-1
	PUSH rdi;	
	CALL asm_fib_rek;	ruf die Funktion auf
	POP rdi;	
	PUSH rax;
	SUB rdi,1;	rdi=rdi-1
	CALL asm_fib_rek;	ruf die Funktion wieder auf
	POP r8;		r8=fib(n-2)
	ADD rax,r8;	rax=fib(n-1)+fib(n-2)
	RET	
	


.ende:
	MOV rax,1;	rax=1
	RET

	






