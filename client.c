#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>
#include <sys/ipc.h>
#include <sys/shm.h>

#define PROJECT_ID 65

int main()
{
    struct flock lock;
    int shmid;
    int fd;
    key_t key;
    char *str;
    lock.l_type = F_WRLCK;
    lock.l_whence = SEEK_SET;
    lock.l_start = 0;
    lock.l_len = 0;
    lock.l_pid = getpid();
    key = ftok("shm_file",PROJECT_ID);
    shmid = shmget(key,1024,0666|IPC_CREAT);
    str = (char*) shmat(shmid,(void*)0,0);
    printf("Data read from memory: %s\n",str);
    if ((fd = open("shm_file", O_RDONLY)) < 0)
        fprintf(stderr,"Can't be opened!\n");
    fcntl(fd, F_GETLK, &lock);
    if (lock.l_type != F_UNLCK)
        fprintf(stderr,"Write locked!\n");
    lock.l_type = F_RDLCK;
    if (fcntl(fd, F_SETLK, &lock) < 0)
        fprintf(stderr,"Can't get a read-only lock!\n");
    lock.l_type = F_UNLCK;
    if (fcntl(fd, F_SETLK, &lock) < 0)
        fprintf(stderr,"Can't be unlocked!\n");
    close(fd);
    shmdt(str);
    shmctl(shmid,IPC_RMID,NULL);
    return 0;
}
