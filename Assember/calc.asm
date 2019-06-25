GLOBAL calc_add

SECTION .text

calc_add:
	PUSH rdx;	saving the values
	PUSH rbx;
	PUSH r12;
	PUSH r13;
	PUSH r14;
	PUSH r15;
	MOV rax, 0;
	MOV rbx, 0;
	MOV rcx, 0;
	MOV rdx, 0;
	MOVD eax, XMM0;
	MOVD ebx, XMM1;
	MOV r8d, eax;
	SHR r8d, 31;   	im Register bleibt nur Vorzeichen des ersten Operands

	MOV r9d, eax;
	SHL r9d, 1;    	Vorzeichen wird weggelassen
	SHR r9d, 24;   	im Register bleibt nur Charakteristik des ersten Operands

	CMP r9d, 255;	for test if inf/NaN
	JGE .error;

	MOV r10d, eax;	Vorzeichen und Charakteristik werden weggelassen
	SHL r10d, 9;
	SHR r10d, 9;	im Register bleibt nur Mantisse des ersten Operands

	MOV r11d, ebx;
	SHR r11d, 31;   im Register bleibt nur Vorzeichen des zweiten Operands

	MOV r12d, ebx;
	SHL r12d, 1;    Vorzeichen wird weggelassen
	SHR r12d, 24;   im Register bleibt nur Charakteristik des zweiten Operands

	CMP r12d, 255;	for test if inf/NaN
	JGE .error;

	MOV r13d, ebx;	Vorzeichen und Charakteristik werden weggelassen
	SHL r13d, 9;
	SHR r13d, 9;	im Register bleibt nur Mantisse des zweiten Operands
	CMP r8d, r11d;
	JNE .oneNegative
	CMP r9d, r12d;	compare EXPONET
	JE .test;	gleiche Exponente
	JB .first;	der Exponent von 2 Zahl größer
	JG .second;	der Exponent von 1 Zahl größer


.makeNumber:
	MOV edx, r8d;	edx=r8d
	SHL edx, 8;	8 Stellen nach links schieben 
	ADD edx, r9d;	edx=r9d
	SHL edx, 23;	23 Stellen nach links schieben 
	ADD edx, r10d;	edx=r10d
	JMP .end	

.test:
	ADD r9d, 1;	 Exponent + 1, da die beiden Zahlen gleichen Exponenten haben.
	ADD r10d, r13d;	 add both mantisse
	SHR r10d, 1;	
	JMP .makeNumber;

.first:	
	ADD r10d, 8388608;	r10 + 2 ^ 23
	ADD ecx, r12d;
	SUB ecx, r9d;	
	MOV r9d, r12d;        Wir benutzen Register r9 für Endergebniss und dann speichern wir die größte Charakteristik in r9.
	JMP .change_first;

.change_first:
	SHR r10d, 1;
	DEC ecx;
	CMP ecx, 0;
	JA .change_first;
	ADD r13d, 8388608; Normalisierte Form von der größeren Zahl
	ADD r10d, r13d;	 add both mantisse
	JMP .checkMantisse

.second:	
	ADD r13d, 8388608;	r13 + 2 ^ 23
	ADD ecx, r9d;		ecx = r9d
	SUB ecx, r12d;		ecx = r9d -r12d
	JMP .change_second;

.change_second:
	SHR r13d, 1;		Rekursion, Exponent anpassen
	DEC ecx;
	CMP ecx, 0;
	JA .change_second;
	ADD r13d, 8388608; 	Normalisierte Form von der größeren Zahl
	ADD r10d, r13d;	 	add both mantisse
	JMP .checkMantisse;
	
.checkMantisse:
	SHL r10d, 8;
	JC .normalize;
	SHR r10d, 8;
	SUB r10d, 8388608; 	Normalisieren ohne "1,"
	JMP .makeNumber;

.normalize:
	SHR r10d, 9;
	ADD r9d, 1;		Exponent anpassen
	JMP .makeNumber;

;ONE OF NUMBERS IS NEGAIVE

.oneNegative:
	CMP r9d, r12d;	compare both Exponents
	JE .expEq
	JB .firstN;
	JG .secondN;	

.expEq:
	CMP r10d, r13d;		vergleichen mit den Mantissen
	JE .null;		wenn gleich --> .null
	JG .sub_first;
	JB .sub_second;

.sub_first:
	SUB r10d, r13d;		Mantisse substrahieren
	JMP .makeNumber;	

.sub_second:
	MOV r8d, r11d;		VZ von größerer Zahl 
	SUB r13d, r10d;		MAntisse substrahieren
	MOV r10d, r13d;		r10d = r13d
	JMP .makeNumber;

.null:
	MOV edx, 0;		einfach 0 zurückgeben
	JMP .end;


.firstN:
	MOV r8d, r11d; 		Wir benutzen VZ von größeren Zahl;	
	ADD r10d, 8388608;	r10 + 2 ^ 23
	ADD ecx, r12d;
	SUB ecx, r9d;	
	MOV r9d, r12d;        Wir benutzen Register r9 für Endergebniss und dann speichern wir die größte Charakteristik in r9.
	JMP .change_firstN;

.change_firstN:
	SHR r10d, 1;		Rekursion
	DEC ecx;
	CMP ecx, 0;
	JA .change_firstN;
	ADD r13d, 8388608; 
	SUB r13d, r10d;	 	sub both mantisse
	MOV r10d, r13d;
	JMP .check
	JMP .makeNumber;

.secondN:	
	ADD r13d, 8388608;
	ADD ecx, r9d;
	SUB ecx, r12d;
	JMP .change_secondN;

.change_secondN:
	SHR r13d, 1;
	DEC ecx;
	CMP ecx, 0;
	JA .change_secondN;
	ADD r10d, 8388608; 
	SUB r10d, r13d;	 	sub both mantisse
	JMP .check
	JMP .makeNumber;

.check:	
	SHL r10d, 9;		prüfen den Fall : 1x,...
	JNC .normalizeN;
	SHR r10d, 9;
	JMP .makeNumber;
	
.normalizeN:
	SHL r10d, 1;		Normalisierung
	DEC r9d;
	JNC .normalizeN;
	SHR r10d, 9;
	JMP .makeNumber;

.error:	
	MOV edx, 0;		die Fälle: NaN und Unendlichkeit(Overflow) 
	JMP .end;
	

.end:
	MOV [rdi], edx;
	POP r15;		loading values
	POP r14;
	POP r13;
	POP r12;
	POP rbx;
	POP rdx;
	RET






