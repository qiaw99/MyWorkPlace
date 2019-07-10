"""
Übungsgruppe:Qianli Wang und Nazar Sopiha
"""
import operator
import random
import math
#import pysnooper
#@pysnooper.snoop()

def isSorted(f,ls):#Aufgabe 2
    temp=ls[0]
    i=1
    while (i<len(ls)):
        if f(temp,ls[i]):
            temp=ls[i]
            i+=1
        else:
            return False
    return True

def insertsort(ls):#Aufgabe 3 a)
    counter=1
    while(counter<len(ls)):
        key=ls[counter]
        k=counter-1
        while k>=0 and ls[k]>key:
            ls[k+1]=ls[k]
            k=k-1
        ls[k+1]=key
        counter+=1
    return ls

def test():#Aufgabe 3 b)
    n=int(input("How many elements in the list do you want to test?\n"))
    a=int(input("What about the lowerbound?\n"))
    b=float(input("What about the upperbound?\n"))
    final_list=[]
    for i in range(0,n):
        temp=random.randint(a,b)
        final_list.append(temp)
    print("The list is",insertsort(final_list))
    print("Is the list sorted? ",isSorted(operator.le,final_list))
    
"""
Komplexität:    
c1+c2+c3+c4+c6+c5(n-1)=O(n) 
"""    
def min_diff(ls):   #Aufgabe 4
    liste=sorted(ls)    #c1
    a=liste[0]          #c2
    b=liste[1]          #c3
    dif=abs(liste[0]-liste[1])  #c4
    for i in range (1,len(liste)-1):#c5
        temp=abs(liste[i]-liste[i+1])  
        if(dif<=temp):      
            pass        
        else:
            dif=temp
            a=liste[i]
            b=liste[i+1]
    return (a,b)    #c6

"""    
Komplexität:
c1+c2+n*(c3+c4+n*c5(+c6)(+c7))+c8=O(n2)    
"""    
def same_average(ls):   #Aufgabe 5
    ave=sum(ls)//(len(ls))  #c1
    final_list=[]   #c2
    for x in ls:
        ziel1=2*ave-x   #c3
        ziel2=2*ave-x+1 #c4
        if (ziel1 in ls):   #c5
            final_list.append((x,ziel1))
        elif(ziel2 in ls):  #c6
            final_list.append((x,ziel2))
        else:   #c7
            pass
    return final_list      #c8     
            
def showGameField(n,m):#Aufgabe 7
    for i in range (0,n):
        for j in range (0,m):
            print('.',end='')
        print()

def newGame(p):
    n=int(input("How many lines?\n"))
    m=int(input("How many rows?\n"))
    num=int(p*n*m)    
    liste=bomb(num,n,m)       
    for i in range(0,n):
        for j in range(0,m):
            if (i,j) in liste:
                 print('O',end='')
            else:
                print('.',end='')
        print()   
        
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

def genSolution():
    n=int(input("How many lines?\n"))
    m=int(input("How many rows?\n"))
    if n<0 or m<0:
        raise Exception("Wrong input!\n")
    else:
        pass
    p=float(input("How about the probability?\n"))
    if p<0 or p>1:
        raise Exception("Wrong! The probability can not less than 0 or more than 1!\n")
    else: 
        pass
    num=int(n*m*p)  #bei uns immer abrunden
    liste=bomb(num,n,m)
    for i in range(0,n):
        for j in range(0,m):
            if (i,j) not in liste:
                counter=0
                for x in range (j-1,j+2):
                    if (i-1,x) in liste:
                        counter+=1
                    else:
                        pass
                for y in range(j-1,j+2):
                    if(i,y) in liste:
                        counter+=1
                    else:
                        pass
                for z in range (j-1,j+2):
                    if (i+1,z) in liste:
                        counter+=1
                    else:
                        pass
                if counter==0:
                    print('.',end='')
                else:    
                    print(counter,end='')
            else:
                print('O',end='')
        print()        
                       
    
def test2():
    liste=[2,2,4,5,8,9]
    print("Die Liste ist:",liste," is sorted? ",isSorted(operator.lt,liste))
    
def test4():
    liste=[3,10,3,9,5,1,2,7,6,8]
    print("Die am nächsten liegenden Zahlen sind von ",liste," ist: ",min_diff(liste))

def test5():
    liste=[6,25,29,10,6,5,8,0,20,19]
    print("Die Paar mit gleichem Durchschnitt von ",liste," ist:",same_average(liste))

def test7():    
    genSolution()


print("Aufgabe2 *************************** ")    
test2()
print("Aufgabe3 *************************** ")
test()
print("Aufgabe4 *************************** ")
test4()
print("Aufgabe5 *************************** ")
test5()
print("Aufgabe7 *************************** ")
test7()    
   
