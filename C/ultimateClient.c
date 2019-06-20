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
#include <errno.h>

#include "log.h"

#define INET_PORT_DEFAULT 8080
#define CLIENT_PATH "tpf_unix_sock.client"

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
    char buff[2];
    ssize_t char_read = 0;
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

    if (write (fd, argv[3], sizeof(argv[3])) != sizeof (argv[3])) {
        LOGE("Failed to send folder/file_path\n");
        return -1;
    }

    char_read = read (fd, buff, sizeof(buff));
    while (char_read > 0) {
        fwrite (buff, (unsigned long)char_read, 1,stdout);
        char_read = read (fd, buff, sizeof(buff));
    }

    return 0;
}























/*int client_sock, fd;
    socklen_t len;
    struct sockaddr_un server_sockaddr;
    struct sockaddr_un client_sockaddr;
    char buf[256];
    memset(&server_sockaddr, 0, sizeof(struct sockaddr_un));
    memset(&client_sockaddr, 0, sizeof(struct sockaddr_un));

    client_sock = socket(AF_UNIX, SOCK_STREAM, 0);
    if (client_sock == -1) {
        LOGE("SOCKET ERROR = %s\n", strerror(errno));
        exit(1);
    }


    client_sockaddr.sun_family = AF_UNIX;
    strcpy(client_sockaddr.sun_path, CLIENT_PATH);
    len = sizeof(client_sockaddr);

    unlink(CLIENT_PATH);
    fd = bind(client_sock, (struct sockaddr *) &client_sockaddr, len);
    if (fd == -1){
        LOGE("BIND ERROR: %s\n", strerror(errno));
        close(client_sock);
        return -1;
    }

    server_sockaddr.sun_family = AF_UNIX;
    strcpy(server_sockaddr.sun_path, addr);
    fd = connect(client_sock, (struct sockaddr *) &server_sockaddr, len);
    if(fd == -1){
        LOGE("CONNECT ERROR = %s\n", strerror(errno));
        close(client_sock);
        return -1;
    }
    return fd;*/

















/* struct sockaddr_un client, server;
    char buff[2048];
    socklen_t len;
    int server_sock, client_sock, fd;
    ssize_t char_read = 0;
    if ((server_sock = socket(AF_UNIX, SOCK_STREAM, 0)) < 0) {
        LOGE("Socket error\n");
        return -1;
    }

    memset (&client, 0, sizeof(client));
    server.sun_family = AF_UNIX;
    strcpy (server.sun_path, addr);
    len = sizeof(server);

    unlink(addr);

    fd = bind(server_sock, (struct sockaddr *) &server, len);
    if (fd < 0) {
        LOGE("Bind error: %s", strerror(errno));
        close(server_sock);
        return -1;
    }

    LOGI("Socket listening\n");

    client_sock = accept(server_sock, (struct sockaddr*)&client, &len);
    if (client_sock < 0) {
        LOGE("ACCEPT ERROR: %s", strerror(errno));
        close (server_sock);
        close (client_sock);
        return -1;
    }

    len = sizeof(client);
    fd = getpeername(client_sock, (struct sockaddr *)&client, &len);
    if (fd < 0) {
        LOGE(" getpeername() error\n");
        close(server_sock);
        return -1;
    }
    else
        LOGI("Client socket filepath: %s\n", client.sun_path);

    LOGI("Waiting to rea\n");
    char_read = read (client_sock, buff, sizeof (buff));
    if (char_read < 0) {
        LOGE("Send error\n");
        close (server_sock);
        close (client_sock);
        return -1;
    }
    else {
        LOGI("Data recieved!\n");
        fwrite (buff, (unsigned long)char_read, 1,stdout);
    }

    return 0;*/
