//Übungsgruppe:Qianli und Nazar
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int read_file (char *path) {
    FILE *source;
    int c;
    int bytes = 0;
    int words = 0;
    int lines = 0;
    int ifWord = 0;
    source = fopen (path, "rd");
    if (source == NULL) {
        fprintf(stderr,"Error opening file!\n");
        return -1;
    }
    while ( (c = fgetc(source)) != EOF ) {
        bytes++;
        if (c == ' ') {
            if (ifWord) {
                words++;
                ifWord = 0;
            }
        }
        else if (c == '\n') {
            if (ifWord) {
                words++;
                ifWord = 0;
            }
            lines++;
        }
        else {
            ifWord = 1;
        }
    }
    fprintf (stdout,"Information about file %s: \n", path);
    fprintf (stdout,"Bytes: %d, \nWords: %d,\nLines: %d\n", bytes, words, lines);
    fprintf(stdout,"\n");
    fclose(source);
    return 0;
}

int main (int argc, char *argv[]) {
    int c;
    int bytes = -1;                                                                         //terminating with ENTER, then CTRL+D, that's why -1 byte
    int words = 0;
    int lines = 0;
    int ifWord = 0;
    if (argc == 2) {
        if (read_file(argv[1])) {
            return -1;
        }
    }
    else if (argc == 1) {
        fprintf(stdout,"Print any text. Print ENTER and then CTRL+D to terminate \n");
        while ( (c = getchar()) != EOF) {
            bytes++;
            if (c == ' ') {
                if (ifWord) {
                    words++;
                    ifWord = 0;
                }
            }
            else if (c == '\n') {
                if (ifWord) {
                    words++;
                    ifWord = 0;
                }
                lines++;
            }
            else {
                ifWord = 1;
            }
        }
        fprintf (stdout,"Bytes: %d, \nWords: %d,\nLines: %d\n", bytes, words, lines);
    }
    else {
        int i = 1;
                pid_t pid;
                for(; i < argc; i++) {
                    pid = fork();
                    if(pid == 0) {
                        read_file(argv[i]);
                        exit(0);
                    }
                    else {
                            int status;
                            wait(&status);
                    }
                }
    }
    return(0);
}


