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

#define NOT_FOUND_RESPONSE "File or directory not found - "

typedef int (*pfn_sent_response)(int , char *);

static int send_dir_info (int fd, char *path);
static int send_file_content(int fd, char *path);
static int send_not_found (int fd, char *path);

static pfn_sent_response response_funtions[] = {
    &send_not_found,
    &send_dir_info,
    &send_file_content,
};

static void print_usage () {
    printf("\nUsage:\n");
    printf("./server -U <server_path> startet den Server mit Unix-Sockets,\n"
           "./server -u <server_address> startet den Server mit UDP-Sockets,\n"
           "./server -t <server_address> startet den Server mit TCP-Sockets,\n\n");
}

static int init_unix_socket (char *addr) {
    struct sockaddr_un saddr;
    int fd;

    saddr.sun_family = AF_UNIX;
    strncpy(saddr.sun_path, addr, sizeof(saddr.sun_path));

    fd = socket(AF_UNIX, SOCK_STREAM, 0);
    if (fd < 0) {
        LOGE("Error during socket creation %s'n", strerror(errno));
        return -1;
    }

    if (bind(fd, (struct sockaddr *)&saddr, sizeof(saddr))) {
        LOGE("Error during socket creation %s'n", strerror(errno));
        close(fd);
        return -1;
    }

    return fd;
}

static int init_inet_socket (int type) {
    int fd;
    struct sockaddr_in addr;

    if (type != SOCK_STREAM && type != SOCK_DGRAM) {
        LOGE("Illegal socket type");
        return -1;
    }

    fd = socket(AF_INET, type, 0);
    if (fd < 0) {
        LOGE("socket error = %s", strerror(errno));
        return -1;
    }

    addr.sin_family = AF_INET;
    addr.sin_port = htons(INET_PORT_DEFAULT);
    addr.sin_addr.s_addr = htonl(INADDR_ANY);

    if (bind(fd, (struct sockaddr *)&addr, sizeof(addr)) < 0) {
        LOGE("bind error = %s", strerror(errno));
        close(fd);
        return -1;
    }

    return fd;
}

static int indicate_path_type(const char *path) {
   struct stat statbuf;
   if (stat(path, &statbuf) != 0) {
       LOGI("File %s not found.", path);
       return 0;
   }

   return S_ISDIR(statbuf.st_mode) ? 1 : 2;
}

static int send_file_content(int fd, char *path) {
    FILE *hFile = fopen(path, "rb");
    size_t file_size;
    unsigned char *file_content;
    if(!hFile) {
        LOGE("This should not happen");
        return -1;
    }

    fseek(hFile, 0L, SEEK_END);
    file_size = (size_t)ftell(hFile);
    rewind(hFile);

    file_content = malloc(file_size);
    if (!file_content) {
        LOGE("Run out of memory");
        fclose(hFile);
        return -1;
    }

    if (fread(file_content, sizeof(unsigned char), file_size, hFile) != file_size) {
        LOGE("Faled to read the file %s path", path);
        fclose(hFile);
        free(file_content);
        return -1;
    }

    fclose(hFile);

    if (write(fd, file_content, file_size) != (ssize_t)file_size) {
        LOGE("Failed to send data");
        free(file_content);
        return -1;
    }

    free(file_content);

    return 0;
}

static int send_dir_info (int fd, char *path) {
    DIR *d = opendir(path);
    if (d) {
        struct dirent *dir = readdir(d);

        while (dir!= NULL) {
            if (write(fd, dir->d_name, strlen(dir->d_name) != strlen(dir->d_name)))
                LOGE("Failed to write data to a socket");

            dir = readdir(d);
        }

        closedir(d);
    }
    else {
        LOGE("This should not happen");
        return -1;
    }
    return 0;
}

static int send_not_found (int fd, char *path) {
    size_t data_len = strlen(path) + strlen(NOT_FOUND_RESPONSE);
    char *data = malloc(data_len);
    if (!data) {
       LOGE("Run out of memory");
       return -1;
    }

    sprintf(data, "%s%s", NOT_FOUND_RESPONSE, path);
    if (write(fd, data, data_len) != (ssize_t)data_len) {
        LOGE("Failed to send response");
        return -1;
    }

    return 0;
}

static int handle_input_connection(int fd) {
    char path [PATH_MAX] = { 0, };
    ssize_t len = read(fd, path, sizeof(path));
    pfn_sent_response response_fn;

    if (len <= 0 || len == sizeof(path)) {
        LOGE("Something went wrong. Read returned %zu", len);
        return -1;
    }

    response_fn = response_funtions[indicate_path_type(path)];

    if (response_fn(fd, path)) {
        LOGE("Response function failed");
    }
    else
        LOGI("Response sent");

    close(fd);
    return 0;
}

int main(int argc, char *argv[]) {
    int fd = -1;

    if (argc != 3 || argv[1][0] != '-') {
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
    if (argv[1][1] == 'U' || argv[1][1] == 't') {
        if (listen(fd, 1)) {
            LOGE("listen err = %s", strerror(errno));
            close(fd);
        }
    }
    else {
        if ()
    }
    

    while (1) {
        int session_fd = accept(fd, NULL, NULL);
        if (session_fd <= 0) {
            LOGE("Failed to accept. something went wrong...");
            break;
        }

        if (handle_input_connection(session_fd))
            close(session_fd);
    }

    return 0;
}


