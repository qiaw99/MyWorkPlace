"""
Übungsgrupe: Qianli Wang und Nazar Sopiha
"""
import random
import math
import sys
sys.setrecursionlimit(100000000)
#import pysnooper
#@pysnooper.snoop()

def bomb(num,n,m):
    liste=[]
    while len(liste)<num:    
        a=random.randint(0,n-1)
        b=random.randint(0,m-1)
        temp=(a,b)
        if temp not in liste:
            liste.append(temp)
        else:
            pass
    return liste

def start_game(p,n,m):
    for i in range (0,n):
        for j in range(0,m):
            print('X',end='')
        print()
        
def play():
    p=float(input("Probability?\n"))
    if p<0 or p>1:
        raise Exception("The probability cannot less than 0 or more than 1!\n")
    else:
        pass
    n=int(input("Rows?\n"))
    m=int(input("Cols?\n"))
    if n<0 or m<0:
        raise Exception("Wrong Input!\n")
    else:
        pass
    start_game(p,n,m)
    if(math.ceil(p*n*m)==p*n*m):
        num=p*n*m
    elif(p*n*m<1):
        num=1
    else:    
        num=math.ceil(p*n*m)-1   
    bombe=bomb(num,n,m)
    print("The coordinate of bomb: ",bombe)
    count=n*m-num
    d={}
    used_list=[]
    while count!=0:
        tupel=eval(input("Please enter a coordinate:\n"))
        if(tupel in bombe):#(i,j)
            break
        else:
            hilfe_array=show_bomb(bombe,tupel,m,n)
            used_list.extend(hilfe_array)
            count-=1
            i=tupel[0]
            j=tupel[1]
            counter=0
            for x in range (j-1,j+2):
                if (i-1,x) in bombe:
                    counter+=1
                else:
                    pass
            for y in range(j-1,j+2):
                if(i,y) in bombe:
                    counter+=1
                else:
                    pass
            for z in range (j-1,j+2):
                if (i+1,z) in bombe:
                    counter+=1
                else:
                    pass
            d[(i,j)]=counter
            punkt_array=[]
            if(counter>0):
                punkt_array.append(tupel)
            for a in range(0,n):
                for b in range (0,m):
                    if ((a,b) in d.keys()) and counter>0:    
                        print(d[(a,b)],end='')
                        continue
                    elif(((a,b) in d.keys()) and counter==0):
                        print('.',end='')
                        continue
                    elif((a,b) in used_list and counter==0 and a!=0 and a!=n-1 and b!=0 and b!=m-1):
                        print('.',end='')
                        continue
                    elif((a,b) in used_list and (a==0 or a==n-1 or b==0 or b==m-1)):
                        print('0',end='')
                        continue
                    elif (a,b) in punkt_array:
                        print(d[(a,b)],end='')
                        continue
                    else:
                        print('X',end='')
                print()        
    if(count<=0):#==0?
        print("You win!\n") 
    else:
        print("Game over!\n")

def ifBomb(bombe,tupel):    #schon gezeigt,ob alle anderen 8 Punkte BOMB sind 
    i=tupel[0]
    j=tupel[1]   
    liste=[(i,j-1),(i,j+1),(i-1,j-1),(i-1,j),(i-1,j+1),(i+1,j-1),(i+1,j),(i+1,j+1)]
    for x in liste:
        if(x in bombe):
            return True
        else:    
            pass        
    return False

def show_bomb(bombe,tupel,m,n): #"Bomb-frei" Liste generieren
    final_list=[]   
    i=tupel[0]
    j=tupel[1]
    liste=[(i,j-1),(i,j+1),(i-1,j-1),(i-1,j),(i-1,j+1),(i+1,j-1),(i+1,j),(i+1,j+1)] 
    if(not ifBomb(bombe,tupel) and i>=0 and i<n and j>=0 and j<m):    #wenn es in der Nähe kein Bomb hat
        for x in liste:     #prüfe alle Nachbarn
            a=x[0]
            b=x[1]
            if(not ifBomb(bombe,x) and a>=0 and a<n and b>=0 and b<m):    #Nachbarn der Nachbarn auch kein Bomb haben
                final_list.append(x)
                #show_bomb(bombe,x,m,n)
            else:   
                pass
        return final_list
    else:
        return final_list
           
play()        
        
        
        
        
        
