GCC_FLAGS = -std=c11 -Wall -Wextra -pedantic -O2 -no-pie

sort: sort.asm wrapper.asm
	nasm -f elf64 sort.asm
	nasm -f elf64 wrapper.asm
	gcc $(GCC_FLAGS) -o sort wrapper.o sort.o

.PHONY: test_sort
test_sort: sort.asm wrapper.c
	nasm -f elf64 sort.asm
	gcc $(GCC_FLAGS) -o sort wrapper.c sort.o

.PHONY: clean
clean:
	rm *.o
	rm sort
