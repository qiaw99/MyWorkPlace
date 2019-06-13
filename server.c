#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>
#include <stdio.h>
#include <sys/ipc.h>
#include <sys/shm.h>

#define MAX_SIZE 256
#define PROJECT_ID 65

int main()
{
    int shmid;
    key_t key;
    int fd;
    char *str;
    struct flock lock;
    lock.l_type = F_WRLCK;
    lock.l_whence = SEEK_SET;
    lock.l_start = 0;
    lock.l_len = 0;
    lock.l_pid = getpid();
    key = ftok("shm_file",PROJECT_ID);
    shmid = shmget(key,1024,0666|IPC_CREAT);
    str = (char*) shmat(shmid,(void*)0,0);
    printf("Write Data : ");
    fgets(str, MAX_SIZE, stdin);
    printf("Data written in memory: %s\n",str);
    if ((fd = open("shm_file", O_RDWR | O_CREAT, 0666)) ==-1){
        fprintf(stderr,"Can't be opened!\n");
        exit(-1);
    }
    if (fcntl(fd, F_SETLK, &lock) < 0){
        fprintf(stderr,"Can't not be locked!\n");
        exit(-1);
    }
    else {
        fprintf(stdout, "Locked by process %d\n", lock.l_pid);
    }
    lock.l_type = F_UNLCK;
    if (fcntl(fd, F_SETLK, &lock) < 0){
        fprintf(stderr,"Can't be unlocked!\n");
        exit(-1);
    }
    close(fd);
    shmdt(str);
    return 0;
}
