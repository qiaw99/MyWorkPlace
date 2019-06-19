#include <stdio.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <sys/un.h>
#include <errno.h>
#include <unistd.h>
#include <netdb.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <limits.h>
#include <dirent.h>
#include <stdlib.h>

#include "log.h"

#define INET_PORT_DEFAULT 8080

static void print_usage () {
    printf("\nUsage:\n");
    printf("./client -U <server_path> <file/folder_request> startet den Client mit Unix-Sockets,\n"
           "./client -u <server_address> <file/folder_request> startet den Client mit UDP-Sockets,\n"
           "./client -t <server_address> <file/folder_request> startet den Client mit TCP-Sockets.\n\n");
}



static int init_unix_socket (char *addr) {

}

static int init_inet_socket (int type) {
    struct sockaddr_in sockaddrin;
    //struct hostent *host;
    int fd;

    if (type != SOCK_STREAM && type != SOCK_DGRAM) {
        LOGE("Illegal socket type");
        return -1;
    }

    fd = socket(AF_INET, type, 0);
    if (fd == -1) {
        LOGE("Socket Error %s", strerror(errno));
        return -1;
    }

  /*  host = gethostbyname(ip);
    if (host == NULL) {
        LOGE("|%s| - unknown host.", ip);
        return -1;
    } */
    sockaddrin.sin_family = AF_INET;
    sockaddrin.sin_addr.s_addr = inet_addr("127.0.0.1");
    sockaddrin.sin_port = htons(INET_PORT_DEFAULT);

   /* sockaddrin.sin_family = AF_INET;
    sockaddrin.sin_port = htons(INET_PORT_DEFAULT);
    memcpy(&sockaddrin.sin_addr, host->h_addr, host->h_length);*/


    if (connect(fd, (struct sockaddr*)&sockaddrin, sizeof(sockaddrin)) != 0) {
            printf("connection with the server failed...\n");
            exit(0);
        }

    return fd;
}

int main (int argc, char* argv []) {
    FILE *hFile;
    size_t file_size;
    int c;
    int char_read = 0;
    unsigned char *file_content;
    int fd = -1;
    if (argc != 4 || argv[1][0] != '-') {
        print_usage();
        return -1;
    }

    switch (argv[1][1]) {
        case 'U':
            LOGI("Starting UNIX with %s adress...", argv[2]);
            fd = init_unix_socket(argv[2]);
            break;
        case 'u':
            LOGI("Starting UDP inet socket on local IP address. Any input address will be ignored...");
            fd = init_inet_socket(SOCK_DGRAM);
            break;
        case 't':
            LOGI("Starting TCP inet socket on local IP address. Any input address will be ignored...");
            fd = init_inet_socket(SOCK_STREAM);
            break;
        default:
            LOGE("Unknown argument");
            print_usage();
    }

    if (fd < 0) {
        LOGE("Error happened. Leaving...");
        return -1;
    }

    if( write(fd, argv[3], sizeof(argv[3])) != sizeof (argv[3])) {
        LOGE("Failed to send folder/file_path\n");
        return -1;
    }

    hFile = fdopen(fd, "rb");
    if(!hFile) {
        LOGE("This should not happen");
        return -1;
    }
/*
    fseek(hFile, 0L, SEEK_END);
    file_size = (size_t)ftell(hFile);
    printf("filesize is %zu\n", file_size);
    rewind(hFile);

    file_content = malloc(file_size);
    if (!file_content) {
        LOGE("Run out of memory");
        fclose(hFile);
        return -1;
    }

    fscanf(hFile, "%d", &size);
    if (fgetc(hFile) != '#') {
        LOGE("something went wrong\n");
        return -1;
    } */
    while ((c = fgetc(hFile)) != EOF) {
            fputc(c, stdout);
            char_read++;
    }

    /*
    if ((size_t)char_read != file_size) {
        LOGE("\n\nFailed to get the whole answer from server\n");
        return -1;
    }


    free(file_content); */



    /*
    write(fd, argv[3], sizeof(argv[3]));
    read(fd, buff, sizeof (buff));
    printf("%c\n\n\n\n", buff[1]); */
    return 0;
}
