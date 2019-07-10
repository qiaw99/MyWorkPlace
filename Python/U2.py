"""
Übungsgruppe: Nazar Sopiha und Qianli Wang
"""
import random

def pythTripelsSmaller(n):      #Aufgabe 1
    liste=[]
    if(n<5):
        raise Exception("Die Zahl ist aber zu klein!")
    else:    
        for c in range(1,n+1):
            for b in range(1,n+1):
                for a in range(1,n+1):
                    if(a**2+b**2==c**2 and a<=b and b<=c):
                        temp=(a,b,c)
                        liste.append(temp)
    return liste 
    
def factorial(n):           #Aufgabe 2
    if n==0:
        return 1
    else:
        return n*factorial(n-1)
        
def odd(n):
    if n%2==1:
        return True
    else:
        return False

def apply_until(f,p,liste):
    final_list=[]
    for x in liste:
        if(p(x)):
            temp=f(x)
            final_list.append(temp) 
        else:
            break    
    return final_list

def filter_rec(f,lst):    #Aufgabe 3
    if(len(lst)==0):
        return []
    if len(lst)>=1:
        x=lst.pop()
    if f(x):
        return filter_rec(f,lst)+[x]
    else:
        return filter_rec(f,lst)
    
def filter_iter(f,liste):
    final_list=[]
    for x in liste:
        if(f(x)):
            final_list.append(x)
        else:
            pass
    return final_list        
            
def repeat(a,b):        #Aufgabe 4
    counter=0
    i=1
    dic={}
    n=random.randint(a,b)
    while(i<=n):
        m=random.randint(1,1000000)
        dic[i]=m
        counter+=1
        i+=1
        if(len(dic.values())!=len(set(dic.values()))):
            break
        else:
            pass
    final_list=list(dic.values())
    return (final_list,counter-1)

def double_birthday():      #Aufgabe 5 a)
    dic={}
    counter=0
    while(counter<367):
        month=random.randint(1,12)
        if(month in [1,3,5,7,8,10,12]):
            day=random.randint(1,31)
        elif(month==2):
            day=random.randint(1,29)
        else:
            day=random.randint(1,30)
        dic[counter]=(month,day)
        counter+=1
        if((len(dic.values())!=len(set(dic.values())))):
            break
        else:
            pass
    return counter 

def repeat_double_birthday():   #Aufgabe 5 b)  
    final_list=[]
    counter=0
    while(counter<367):
        month=random.randint(1,12)
        if(month in [1,3,5,7,8,10,12]):
            day=random.randint(1,31)
        elif(month==2):
            day=random.randint(1,29)
        else:
            day=random.randint(1,30)
        counter+=1    
        temp=(month,day)
        final_list.append(temp)
        if(len(final_list)!=len(set(final_list))):
            break
        else:
            pass
    return counter 

def fact(a,b):
    fac=1
    if a>=b:
        while(a>=b):
            fac*=a
            a-=1
        return fac   
    else:
        return -1
    
def birthday_paradox(n):    #Aufgabe 5 c)  
    final_list=[]
    counter=0
    while(counter<n):
        month=random.randint(1,12)
        if(month in [1,3,5,7,8,10,12]):
            day=random.randint(1,31)
        elif(month==2):
            day=random.randint(1,29)
        else:
            day=random.randint(1,30)
        counter+=1    
        temp=(month,day)
        final_list.append(temp)
    dif=len(final_list)-len(set(final_list))
    print("Es gibt "+str(dif)+" Paare, die am gleichen Tag Geburtstag haben.")
    if dif==0:
        return 0
    else:
        p=1-fact(365,(365-n+dif))/pow(365,n)
        return p

def test1():
    n=int(input("Bitte geben Sie eine natürliche Zahl n:\n"))         
    print(pythTripelsSmaller(n))
    
def test2():
    liste=[3,5,7,4,9,6]
    print("Das Ergebnis der Liste von [3,5,7,4,9,6] ist: ",apply_until(factorial,odd,liste))

def test3_1():
    liste=[2,4,3,7,1,0,8,3]
    print("Mit Iteration von [2,4,3,7,1,0,8,3]: ",filter_iter(odd,liste))
    
def test3_2():    
    liste=[2,4,3,7,1,0,8,3]
    print("Mit Rekursion von [2,4,3,7,1,0,8,3]: ",filter_rec(odd,liste))

def test4():
    print("Nach ",repeat(1,1000000)[1]," kommt die erste Wiederholung wenn wir repeat(1,1000000) aufrufen.")
    
def test5_1():
    print("Nach "+str(double_birthday())+" Versuchen kommt eine Wiederholung mit Dictionary vor.")
    
def test5_2():
    print("Nach "+str(repeat_double_birthday())+" Versuchen kommt eine Wiederholung mit Array vor.")

def test5_3():
    n=int(input("Geben Sie die Anzahl der Gäste,die auf Party kommen:\n"))
    print("Die Wahrscheilichkeit,die zwei Personen am gleichen Tag Geburtstag haben, ist :", birthday_paradox(n))
print("Aufgabe 1**********************************")
test1()
print("Aufgabe 2**********************************")
test2() 
print("Aufgabe 3_1**********************************")
test3_1()
print("Aufgabe 3_2**********************************")
test3_2()
print("Aufgabe 4**********************************")
test4()
print("Aufgabe 5_1**********************************")
test5_1()
print("Aufgabe 5_2**********************************")
test5_2()
print("Aufgabe 5_3**********************************")
test5_3()
   
