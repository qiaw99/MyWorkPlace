//Übungsgruppe:Qianli Wang und Nazar Sopiha
#include<stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <fcntl.h>
#include <errno.h>
#include <string.h>
#include <dirent.h>
#include <linux/limits.h>
#include <stdio.h>
#include <stdlib.h>
#include<time.h>
#include<errno.h>

#define MAX_SIZE 256	//limit for the length of path
#define LIMIT 100	//limit for amount of files

int get_file_size_time(const char *filename){
	struct stat statbuf;	//status of files
	if(stat(filename,&statbuf) == -1){
		fprintf(stderr,"Get stat on %s Error : %s.\n",filename,strerror(errno));
		exit(1);
	}
	if(S_ISDIR(statbuf.st_mode)){	// if folders
		return 1;
	}
	if(S_ISREG(statbuf.st_mode)){ 	//if files
		printf("%s size: %ld bytes\tmodified at %s",filename, statbuf.st_size,ctime(&statbuf.st_mtime));
	}	
	return 0;
}

int is_begin_with(const char *str1,char *str2){	//check if the folder or files are hidden
	char *p=str2;
	if(*p == str1[0]){
		return 1;
	}else{
		return 0;
	}	
}

int list_content(char*folderPath){		//defined function from trashcan.c
	struct dirent *ptr;
	DIR *dir=opendir(folderPath);
	if(dir==NULL){
		fprintf(stderr,"Error when opening the folder!\n");
		exit(1);	
	}
	fprintf(stdout,"The content is: \n");
	while((ptr=readdir(dir))!=NULL){
		if(strcmp(ptr->d_name,".")==0 || strcmp(ptr->d_name,"..")==0){
			continue;		
		}
		if(is_begin_with(ptr->d_name,".")){
			continue;
		}else{
			printf("%s \n",ptr->d_name);			
		}
	}
	closedir(dir);
	exit(0);
}

int list_hidden_content(char*folderPath){		
	struct dirent *ptr;
	DIR *dir=opendir(folderPath);
	if(dir==NULL){
		fprintf(stderr,"Error when opening the folder!\n");
		exit(1);	
	}
	fprintf(stdout,"The content is: \n");
	while((ptr=readdir(dir))!=NULL){
		if(strcmp(ptr->d_name,".")==0 || strcmp(ptr->d_name,"..")==0){
			continue;		
		}
		printf("%s\n",ptr->d_name);	
	}
	closedir(dir);
	exit(0);
}

void stsrt(char *strings[],int num){	//bubblesort of stringpointers
	char *temp;
	int top,seek;
	for(top=0;top<num-1;top++){
		for(seek=top+1;seek<num;seek++){
			if(strcmp(strings[top],strings[seek])>0){
				temp=strings[top];
				strings[top]=strings[seek];
				strings[seek]=temp;
			}
		}
	}
}

int sort_filename(char *folderPath){	
	char *ptstr[LIMIT];
	int ct=0;
	struct dirent *ptr;
	DIR *dir=opendir(folderPath);
	if(dir==NULL){
		fprintf(stderr,"Error when opening the folder!\n");
		exit(1);	
	}
	fprintf(stdout,"The content is: \n");
	while((ptr=readdir(dir))!=NULL){
		if(is_begin_with(ptr->d_name,".")){
			continue;
		}
		ptstr[ct]=ptr->d_name;
		ct++;
	}
	stsrt(ptstr,ct);
	for(int k=0;k<ct;k++){
		//printf("%s \n", ptstr[k]);
		get_file_size_time(ptstr[k]);
	}
	return 0;
}

int sort_hidden_filename(char *folderPath){	
	char *ptstr[LIMIT];
	int ct=0;
	struct dirent *ptr;
	DIR *dir=opendir(folderPath);
	if(dir==NULL){
		fprintf(stderr,"Error when opening the folder!\n");
		exit(1);	
	}
	fprintf(stdout,"The content is: \n");
	while((ptr=readdir(dir))!=NULL){
		if(strcmp(ptr->d_name,".")==0 || strcmp(ptr->d_name,"..")==0){
			continue;		
		}
		ptstr[ct]=ptr->d_name;
		ct++;
	}
	stsrt(ptstr,ct);
	for(int k=0;k<ct;k++){
		//printf("%s \n", ptstr[k]);
		get_file_size_time(ptstr[k]);
	}
	return 0;
}


int countChar(char *str,char a){	//find out, how many . in the name of file
	int n=0,i=0;
	while(*(str+i)!='\0'){
		if(*(str+i)==a){
			n++;
		}
		i++;
	}
	return n;
}

int main(int argc, char* argv[]){
	char folderPath[MAX_SIZE];
	getcwd(folderPath,MAX_SIZE);
	if(argc==1){
		list_content(folderPath);		
	}else if(argc==2){
		if(strcmp(argv[1],"-a")==0){
			list_hidden_content(folderPath);
		}
		else if(strcmp(argv[1],"-l")==0){
			sort_filename(folderPath);
		}
		else if(strcmp(argv[1],"-la")==0){
			sort_hidden_filename(folderPath);
		}
		else if(strcmp(argv[1],"-al")==0){
			sort_hidden_filename(folderPath);
		}
		else{	
			if(is_begin_with(argv[1],".")){
				if(countChar(argv[1],'.')==1){
					char basePath[MAX_SIZE];
					memset(basePath,'\0',sizeof(basePath));	//initialize the basePath 
					getcwd(basePath,MAX_SIZE-1);			
					strcat(basePath,"/");
					strcat(basePath,argv[1]);
					printf("The current path is %s\n",basePath);			
					list_hidden_content(basePath);
				}else{
					printf("The content is: %s\n",argv[1]);
				}
			}else{
				if(countChar(argv[1],'.')==1){
					printf("The content is %s\n",argv[1]);
				}else{
					char basePath[MAX_SIZE];
					memset(basePath,'\0',sizeof(basePath));	
					getcwd(basePath,MAX_SIZE-1);			
					strcat(basePath,"/");
					strcat(basePath,argv[1]);
					printf("The current path is %s\n",basePath);			
					list_hidden_content(basePath);					
				}
			}
		}
	}else{
		fprintf(stdout,"Wrong amount of input!\n");
		exit(1);
	}		
	exit(0);
}
