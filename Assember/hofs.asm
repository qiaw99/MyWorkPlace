GLOBAL zipWith
GLOBAL fold

SECTION .text

fold:
	;rdi die Funktion
	;rsi die Länge 
	;rdx Array a 
	;rcx links oder rechts
	PUSH rbx;
	PUSH r12;
	PUSH r13;
	MOV r8, 1; 		POINTER
	MOV r9, rdi;   		Adress of function
	MOV r10, rsi;		Length of array
	CMP r10, 1;		Vergleichen die Länge mit 1, wenn die Länge gleich 1 ist, dann geben wir die Liste einfach zurück 
	JE .getBack;
	SUB r10, 2;		LENGTH OF ARRAY - 2
	XOR rdi, rdi;		rdi leer machen
	XOR rsi, rsi;		rsi leer machen
	CMP rcx, 1;		Vergleichen rcx mit 0 und 1 
	JE .right
	CMP rcx, 0;
	JE .left
	JMP .error;


.error:
	MOV rax, 0;
	

.left:
	MOV rdi, [rdx];		rdi -->erstes Operand
	MOV rsi, [rdx + r8 * 8];rśi -->zweites Operand
	
	PUSH rdx;
	PUSH r8;
	PUSH rdi;
	PUSH rsi;
	PUSH r10;
	CALL r9;		Die Funktion wird aufgerufen. 
	POP r10;
	POP rsi;
	POP rdi;
	POP r8;
	POP rdx;

	CMP r8, r10;		Vergleichen den Pointer mit der (Länge - 1) 
	JG .ende;		wenn größer --> .ende
	JMP .calculateL;	Springt zu .calculate

.right:
	MOV rax, 0;		rax leer machen
	MOV r8, 0;		Pointer wird am Anfang gestellt.
	MOV rdi, [rdx + r10 * 8];	
	INC r10;		r10=r10+1
	MOV rsi, [rdx + r10 * 8];
	SUB r10, 2;
	
	PUSH rdx;
	PUSH r10;
	PUSH r8;
	PUSH rdi;
	PUSH rsi;	
	CALL r9;		Die Funktion wird aufgerufen.
	POP rsi;
	POP rdi;
	POP r8;
	POP r10;
	POP rdx;
	
	CMP r8, r10;		Die Rekursion.
	JG .ende
	JMP .calculateR;

.calculateR:
	MOV rdi, [rdx + r10 * 8];	
	MOV rsi, rax;		Das Ergebnis(vorher wird es in rax gespeichert) wird jetzt in rdi gespeichert. Also als erster Operand
		
	PUSH rdx;
	PUSH r10;
	PUSH r8;
	PUSH rdi;
	PUSH rsi;
	CALL r9;		Die Funktion wird aufgerufen. 
	POP rsi;
	POP rdi;
	POP r8;
	POP r10;
	POP rdx;
	
	DEC r10;		r10 = r10 - 1
	CMP  r8, r10;		Die Rekursion.
	JG .ende
	JMP .calculateR;
	
.calculateL:
	MOV rdi, rax;
	INC r8;
	MOV rsi, [rdx + r8 * 8];
	
	PUSH rdx;
	PUSH r10;
	PUSH r8;
	PUSH rdi;
	PUSH rsi;
	CALL r9;
	POP rsi;
	POP rdi;
	POP r8;
	POP r10;
	POP rdx;

	CMP r8, r10;
	JG .ende
	JMP .calculateL		

.getBack:
	MOV rax, [rdx];		Einfachen geben wir die eingegebene Liste zurück.
	JMP .ende;

.ende:
	POP r13;
	POP r12;
	POP rbx;
	RET




zipWith:
	;rdi die Addresse
	;rsi Länge 
	;rdx a 
	;rcx b
	;r8  c--> die Rückgabe
	PUSH rdi;		save the adress of function
	PUSH rsi;		save the length of array
	
	MOV r9, rdi;  		Die Adresse wird in r9 gespeichert.
	MOV r10, rsi;		DIe Länge wird in r10 gespeichert.
	DEC r10;		r10=r10-1
	MOV r11, 0;		r11=0 also der Pointer wird am Anfang gestellt.
	XOR rdi, rdi;		rdi leer machen
	XOR rsi, rsi;		rsi leer machen
	CMP r11, r10;		Vergleichen Pointer mit (Länge - 1)
	JG .end;		wenn größer --> .end
	JMP .calc;		wenn nicht, dann .calc

.calc:
	MOV rdi, [rdx + r11 * 8];	rdi --> erstes Operand
	MOV rsi, [rcx + r11 * 8];	rsi --> zweites Operand
	
	PUSH rcx;
	PUSH rdx
	PUSH rdi;
	PUSH rsi;
	PUSH r11;
	PUSH r10;
	CALL r9;		Die Funktion wird aufgerufen.
	POP r10;
	POP r11;
	POP rsi;
	POP rdi;
	POP rdx;
	POP rcx;	

	MOV [r8 + r11 * 8], rax;	Die Ergebnisse werden in r8 (also Árray c) gespeichert.
	INC r11;		r11 = r11 + 1
	CMP r11, r10;	 	Rekursion
	JG .end
	JMP .calc


.end:
	POP rsi;
	POP rdi;
	RET
