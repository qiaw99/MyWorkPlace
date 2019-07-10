"""
Übungsgruppe: Nazar Sopiha und Qianli Wang
"""
import math

def leap_year(year):	#Aufgabe 1
	if ((year % 4== 0) and ((year % 100 != 0) or (year % 400 == 0))):
		return True
	else:
		return False

def weekday(day,month,year):	#Aufgabe 2
	a=[1,3,5,7,8,10,12]
	b=[4,6,9,11]
	if((day <= 0) or (month in a and day > 31) or (month in b and day >30) or (month == 2 and ((leap_year(year) and day > 29) or (not(leap_year(year)) and day > 28)))):
		raise Exception("ERROR: " +str(day)+" is an illegal day value!")
	elif(month<1 or month>12):
		raise Exception("ERROR: "+str(month)+" is an illegal month value!")
	elif(year<=0):
		raise Exception("ERROR: "+str(year)+" is an illegal year value!")
	else:	
		week=dict([(0,"Sonntag"),(1,"Montag"),(2,"Dienstag"),(3,"Mottwoch"),(4,"Donnerstag"),(5,"Freitag"),(6,"Samstag")])
		y0=year-(14-month)//12
		x=y0+y0//4-y0//100+y0//400
		m0=month+12*((14-month)//12)-2
		name=(day+x+31*m0//12) % 7
		tag=week[name]
		return tag

def triangle_area(a,b,c):       #Aufgabe 3 a) und b)
    if(((a+b>c) and (a+c>b) and (b+c>a)) and a>0 and b>0 and c>0):
        s=(a+b+c)/2
        A=math.sqrt(s*(s-a)*(s-b)*(s-c))
        return A
    else:
        raise Exception("a,b und c  können nicht als Dreiecke bilden.")

def lineLength(p1,p2):
    punkt1=p1[0]-p2[0]
    punkt2=p1[1]-p2[1]
    lineLength=math.sqrt(punkt1**2+punkt2**2)
    return lineLength
    
def convex_polygon(liste):  #Aufgabe 3 c)
    temp=0
    for i in range(1,len(liste)-1):
        temp+=triangle_area(lineLength(liste[0],liste[i]),lineLength(liste[i],liste[i+1]),lineLength(liste[0],liste[i+1]))
    if(math.ceil(temp)-temp<0.1):
        return math.ceil(temp)
    else:
        return temp
    
    
def factors(n): #Aufgabe 4
        if n==1:
                return 1
        else:
                liste=[]
                while n !=1:
                        for i in range(2,int(n+1)):
                                if n % i==0:
                                        liste.append(i)
                                        n=n/i
                                        break
        return liste

def test1():
        year=int(input("Bitte geben Sie ein beliebiges Jahr:\n"))
        if(leap_year(year)):
        	print(str(year)+" is a leap year");
        else:
            print(str(year)+" is not a leap year");
def test2():
        day=int(input("Bitte geben Sie ein beliebiger Tag:\n"))
        month=int(input("Bitte geben Sie ein beliebiger Monat:\n"))
        year=int(input("Bitte geben Sie ein beliebiges Jahr:\n"))
        print(str(day)+"."+str(month)+"."+str(year)+" ist am "+weekday(day,month,year))


def test3_1_2():
        print("Bitte geben Sie 3 Integer als Seitenlängen vom Dreiecke ein:")
        a=int(input("a="))
        b=int(input("b="))
        c=int(input("c="))
        print("Die Fläche ist gleich:",end='')
        print("%.2f"%triangle_area(a,b,c))
        
def test3_3():
    liste=[(0,0),(1,0),(1,1),(0,1)]
    print("Die Fläche von Polygon ",liste," ist:"+str(convex_polygon(liste)))
 
def test4():
        print("Geben Sie eine natürliche Zahl zur Primfaktorzerlegung:")
        n=int(input("n="))
        print("Primzahlzergung: "+str(factors(n)))

print("----------------------------------------------------Aufgabe 1")
test1()
print("----------------------------------------------------Aufgabe 2")
test2()
print("----------------------------------------------------Aufgabe 3_1_2")
test3_1_2()
print("----------------------------------------------------Aufgabe 3_3")
test3_3()
print("----------------------------------------------------Aufgabe 4")
test4()


