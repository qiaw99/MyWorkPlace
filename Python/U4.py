"""
Übungsgruppe: Qianli Wang und Nazar Sopiha
"""
#import pysnooper
#@pysnooper.snoop()
import random
import datetime

def merge(low,high):
    res=[]
    i,j=0,0
    while i<len(low) and j<len(high):
        if low[i]<=high[j]:
            res.append(low[i])
            i+=1
        else:
            res.append(high[j])
            j+=1
    res+=low[i:]
    res+=high[j:]
    return res

def mergesort(A):
    if len(A)<2:
        return A
    else:
        m=len(A)//2
        return merge(mergesort(A[:m]),mergesort(A[m:]))
        
def insertsort(seq):
    for j in range(1,len(seq)):
        key=seq[j]
        k=j-1
        while k>=0 and seq[k]>key:
            seq[k+1]=seq[k]
            k-=1
        seq[k+1]=key
    return seq    

def new_mergesort(A):#Aufgabe 1 a)
    if len(A)<2:
        return A
    else:
        m=len(A)//2
        if m<9:
            if(len(A)<18):
                return insertsort(A)
            else:
                merge(insertsort(A[:m]),mergesort(A[m:]))
        else:
            return merge(mergesort(A[:m]),mergesort(A[m:]))

def hilfeArray_mergesort(A):    #Aufgabe 1 b)
    N = len(A)
    partsLength = 1
    while partsLength < N:
        for i in range(0, N // partsLength, 2):
            hilfeArray_merge(A, i * partsLength, (i + 1) * partsLength)
        partsLength <<= 1 #partsLength *= 2
    return A

def hilfeArray_merge(A, firstPartlowerIndex, secondPartlowerIndex):
    firstPartIndex = firstPartlowerIndex
    secondPartIndex = secondPartlowerIndex
    rightBoundary = min(2 * secondPartlowerIndex - firstPartlowerIndex - 1, len(A) - 1)
    mergedList = []
    while secondPartlowerIndex - 1 - firstPartIndex >= 0 and rightBoundary - secondPartIndex >= 0:
        if A[firstPartIndex] <= A[secondPartIndex]:
            mergedList.append(A[firstPartIndex])
            firstPartIndex += 1
        else:
            mergedList.append(A[secondPartIndex])
            secondPartIndex += 1
    while secondPartlowerIndex - 1 - firstPartIndex >= 0:
        mergedList.append(A[firstPartIndex])
        firstPartIndex += 1
    while rightBoundary - secondPartIndex >= 0:
        mergedList.append(A[secondPartIndex])
        secondPartIndex += 1
    for i in range(firstPartlowerIndex, rightBoundary + 1):
        A[i] = mergedList[i - firstPartlowerIndex]    
   
            
def parent(i):
    return i//2
    
def left(i):
    return i*2
    
def right(i):
    return i*2+1
    
def heap_size(H):
    return H[0]
    
def dec_heap_size(H):
    H[0]=H[0]-1

#Komplexität=c1+c2+c3(+c4)+c5+c6+n*log(n) =O(n*log(n))    
def max_heapify(H,pos):
    left_t=left(pos)    #c1
    right_t=right(pos)  #c2
    if left_t<=heap_size(H) and H[left_t][1]>H[pos][1]:  
        biggest=left_t  #c3
    else:   
        biggest=pos #c4
    if right_t<=heap_size(H) and H[right_t][1]>H[biggest][1]:   
        biggest=right_t     #c5
    if biggest!=pos:    
        H[pos],H[biggest]=H[biggest],H[pos] #c6
        max_heapify(H,biggest)  #n*log(n)/2

#Komplexität=c1+c2+c3*n//2+c4=O(n)
def buildPriorityQueue(taskList):	#Aufgabe 2
    temp=len(taskList)#c1
    taskList.insert(0,temp)#c2
    for i in range(heap_size(taskList)//2,0,-1):# n//2-mal
        max_heapify(taskList,i) #c3
    return taskList #c4

#Komplexität=c1+c2+(gleich wie oben)=O(n)    
def my_insert(priorityQueue,newTask): #newTask=(an,n)
    priorityQueue.append(newTask)   #c1
    priorityQueue.pop(0)    #c2
    return buildPriorityQueue(priorityQueue)
            
#Komplexität=c1 or c2 =O(1)            
def isEmpty(priorityQueue):
    if priorityQueue==[]:#c1
        return True
    else:   #c2
        return False
#Komplexität=c1 or c2 =O(1)    
def removeTask(priorityQueue):
    if(isEmpty(priorityQueue)): #c1
        return None
    else:    #c2
        return priorityQueue.pop(1) 

"""        
test() Funktion: <-- Wir haben mithilfe "hilfe" Variable so gemacht, dass es zufällig entweder einfügt oder entfernt        
"""        
def test():
	n=int(input("lowerBound of priority?\n"))
	m=int(input("upperBound of priority?\n"))
	l=int(input("What about the length of priorityQueue?\n"))
	hilfe=random.randint(0,1)   #<-----
	liste=[0]*l
	for i in range (1,l):   #Eine beliebige Liste generieren
		a=random.randint(n,m)
		b=random.randint(n,m)
		temp=(a,b)
		liste[i]=temp
	liste[0]=l-1   #Füge die Größe von Heap 
	if(hilfe):	#1 ->Einfügen		
		task=random.randint(n,m)
		prio=random.randint(n,m)    
		tupel=(task,prio)
		print("Add a random task!\n")
		print(liste)
		print("The result is: ",my_insert(liste,tupel))
	else:   # 0->Entfernen
		print("Remove the highest priority task\n")
		print(liste)
		print("The removed task is: ",removeTask(liste))
	       
"""
Komplexität: c1+c2+n*c3+c4+(c5+c6+c6)*n*n=O(n^2)
"""        
def counting_sort(A,maxval):#Aufgabe 3 
    m=maxval+1  #c1
    count=[0]*m #c2
    for a in A: 
        count[a]+=1 #c3
    i=0 #c4
    for a in range(0,m):    
        for c in range(count[a]):   
            A[i]=a  #c5
            i+=1    #c6
    return A   #c7  

def quicksort(A,low,high):
    if low<high:
        m=partition(A,low,high)
        quicksort(A,low,m-1)
        quicksort(A,m+1,high)
    return A    

def partition(A,low,high):
    pivot=A[low]
    i=low
    for j in range(low+1,high+1):
        if(A[j]<pivot):
            i+=1
            A[i],A[j]=A[j],A[i]
    A[i],A[low]=A[low],A[i]
    return i
    
def sort_Integers():
    n=10000000
    liste=[]
    for x in range(0,n):
        a=random.randint(0,pow(2,32)-1)
        liste.append(a)
    starttime=datetime.datetime.now 
    quicksort(liste,0,n-1)
    endtime=datetime.datetime.now()
    print("The execute time is: ",(endtime-starttime).seconds)
        

def test1_1():
    liste=eval(input("Please enter a list:\n"))
    print("The sorted list with mergesort and insertsort is: ",new_mergesort(liste))
    
def test1_2():
    liste=eval(input("Please enter a list:\n"))
    print("The sorted list with new mergesort is:",hilfeArray_mergesort(liste))   

def test3():
    liste=eval(input("Please enter a list:\n"))
    if liste==[]:
        print("The sorted list with new countingsort is:",[])
    else:
        a=max(liste)
        print("The sorted list with new countingsort is: ",counting_sort(liste,a))
    
print("**********Aufgabe1_1***********")
test1_1()
print("**********Aufgabe1_2***********")
test1_2()
print("**********Aufgabe2***********")
print("test() Funktion: <-- Wir haben mithilfe \"hilfe\" Variable so gemacht,\n dass es zufällig entweder einfügt oder entfernt\n")
test()
print("**********Aufgabe3***********")
test3()




    
