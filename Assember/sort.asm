global function

section .text

function:

	xor r8, r8
	xor r9, r9

	cmp r8, rsi
	je _emptyend
	mov rcx, [rdi]
	mov [rdx], rcx
	
_loop:
	inc r8
	cmp r8, rsi
	je _end

	mov rcx, [rdi+r8*8]
	cmp rcx, [rdx+r9*8]
	jl _loop

	inc r9
	mov [rdx+r9*8], rcx
	jmp _loop

_end:
	inc r9

_emptyend:
	mov rax, r9
	ret
