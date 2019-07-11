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
#include <dirent.h>
#include <stdlib.h>
#include <errno.h>
#include <linux/limits.h>

#include "log.h"

#define INET_PORT_DEFAULT 8080
#define CLIENT_PATH "tpf_unix_sock.client"

struct sockaddr_in connect_addr;
socklen_t addrlen;

static void print_usage () {
    printf("\nUsage:\n");
    printf("./client -U <server_path> <file/folder_request> startet den Client mit Unix-Sockets,\n"
           "./client -u <server_address> <file/folder_request> startet den Client mit UDP-Sockets,\n"
           "./client -t <server_address> <file/folder_request> startet den Client mit TCP-Sockets.\n\n");
}



static int init_unix_socket (char *addr) {
    struct sockaddr_un saddr;
    int fd;

    if ( (fd = socket(AF_UNIX, SOCK_STREAM, 0)) == -1) {
        LOGE("Socket Error: %s", strerror(errno));
        return -1;
    }

    memset(&saddr, 0, sizeof(saddr));
    saddr.sun_family = AF_UNIX;
    strncpy(saddr.sun_path, addr, sizeof(saddr.sun_path));

    if (connect(fd, (struct sockaddr*)&saddr, sizeof(saddr))) {
        printf("connection with the server failed...\n");
        return -1;
    }
    return fd;
}

static int init_inet_socket (int type) {
    struct sockaddr_in sockaddrin;
    int fd;

    if (type != SOCK_STREAM && type != SOCK_DGRAM) {
        LOGE("Illegal socket type");
        return -1;
    }

    fd = socket(AF_INET, type, 0);
    if (fd == -1) {
        LOGE("Socket Error: %s", strerror(errno));
        return -1;
    }

    sockaddrin.sin_family = AF_INET;
    sockaddrin.sin_addr.s_addr = inet_addr("127.0.0.1");
    sockaddrin.sin_port = htons(INET_PORT_DEFAULT);

    if (connect(fd, (struct sockaddr*)&sockaddrin, sizeof(sockaddrin))) {
            printf("connection with the server failed...\n");
            return -1;
    }

    return fd;
}

int main (int argc, char* argv []) {
    char buff[4048];
    ssize_t char_read = 0;
    int fd = -1;
    if (argc != 4 || argv[1][0] != '-') {
        print_usage();
        return -1;
    }
    connect_addr.sin_family = AF_INET;
    connect_addr.sin_addr.s_addr = inet_addr("127.0.0.1");
    connect_addr.sin_port = htons(INET_PORT_DEFAULT);
    addrlen = sizeof(connect_addr);

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
    if (sendto (fd, argv[3], strlen(argv[3]), 0, (struct sockaddr*)&connect_addr, addrlen) != strlen (argv[3])) {
        LOGE("Failed to send folder/file_path\n");
        return -1;
    }

    char_read = recvfrom (fd, buff, sizeof(buff), 0, (struct sockaddr*)&connect_addr, &addrlen);
    if(!char_read) {
        LOGE("I DON'T SEE ANYTHING OVER HERE!\n");
    }
    while (char_read > 0) {
        fwrite (buff, (unsigned long)char_read, 1, stdout);
        char_read = recvfrom (fd, buff, sizeof(buff), 0, (struct sockaddr*)&connect_addr, &addrlen);
    }

    return 0;
}
