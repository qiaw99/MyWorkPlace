#include<sys/types.h> 
#include<sys/socket.h> 
#include<unistd.h> 
#include<netinet/in.h> 
#include<arpa/inet.h> 
#include<stdio.h> 
#include<stdlib.h> 
#include<errno.h> 
#include<netdb.h> 
#include<stdarg.h> 
#include<string.h> 

#define SERVER_PORT 8000 
#define BUFFER_SIZE 1024 
#define FILE_NAME_MAX_SIZE 512 

int main() 
{ 
	/* 服务端地址 */ 
	struct sockaddr_in server_addr; 
	bzero(&server_addr, sizeof(server_addr)); 
	server_addr.sin_family = AF_INET; 
	server_addr.sin_addr.s_addr = inet_addr("127.0.0.1"); 
	server_addr.sin_port = htons(SERVER_PORT); 

	/* 创建socket */ 
	int client_socket_fd = socket(AF_INET, SOCK_DGRAM, 0); 
	if(client_socket_fd < 0) 
	{ 
		perror("Create Socket Failed:"); 
		exit(1); 
	} 

	/* 输入文件名到缓冲区 */ 
	char file_name[FILE_NAME_MAX_SIZE+1]; 
	bzero(file_name, FILE_NAME_MAX_SIZE+1); 
	printf("Please Input File Name On Server:\t"); 
	scanf("%s", file_name); 

	char buffer[BUFFER_SIZE]; 
	bzero(buffer, BUFFER_SIZE); 
	strncpy(buffer, file_name, strlen(file_name)>BUFFER_SIZE?BUFFER_SIZE:strlen(file_name)); 

	/* 发送文件名 */ 
	if(sendto(client_socket_fd, buffer, BUFFER_SIZE,0,(struct sockaddr*)&server_addr,sizeof(server_addr)) < 0) 
	{ 
		perror("Send File Name Failed:"); 
		exit(1); 
	} 
	int fp=fopen(file_name,"r");
	char test[1024];
	read(fp,test,1024);
	fprintf(stdout,test);
	close(client_socket_fd); 
	return 0; 
} 
