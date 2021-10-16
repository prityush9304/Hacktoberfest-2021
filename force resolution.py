# -*- coding: utf-8 -*-
"""
Created on Thu Aug 29 18:58:11 2019

@author: Suyash
"""

import math
pi=math.pi 
sum_Fx=0
sum_Fy=0


#0=3.67394039744206e-15

ques=int(input("How many forces you want to enter "))

for i in range(0,ques):
    

    F1=float(input("Enter the magnitude of the force  "))
    Quad=int(input("Enter the Quadrant of Your Force "))
    T1=float(input("Enter the angle of the force"))
    A1=int(input("Enter the 0 if angle with x-axis , Enter the 1 if angle with y-axis"))
    
    
    if Quad==1:
        if A1==0:
            T1=T1
        else:
            T1=90-T1
    elif Quad==2:
         if A1==0:
            T1=180-T1
         else:
            T1=90+T1
    elif Quad==3:
        if A1==0:
            T1=180+T1
        else:
            T1=270-T1
    elif Quad==4:
        if A1==0:
            T1=360-T1
        else:
            T1=270+T1
        
        
   
    
    F_x=F1*math.cos(T1*pi/180)
    F_y=F1*math.sin(T1*pi/180)
    
    sum_Fx=sum_Fx+F_x
    sum_Fy=sum_Fy+F_y
    
    Res=math.sqrt(sum_Fx**2+sum_Fy**2)
    
    Ax=math.atan(F_y/F_x)
    Ax=(Ax*(180/pi))
    Ay=90-Ax
    if Ax<0 :
        Ax=-Ax
    else :
        Ax=Ax
    
print("Summation of Fx = " , sum_Fx)
print("Summation of Fy = " , sum_Fy)

print("Angle Alpha of Resultant with x axis is " , Ax)
print("Angle Alpha of Resultant with y axis is " , Ay)
if sum_Fx>0 and sum_Fy>0:
    print("Resultant of the above force system is in 1st Quadrant" )
if sum_Fx<0 and sum_Fy>0:
    print("Resultant of the above force system is in 2nd Quadrant ")
if sum_Fx<0 and sum_Fy<0:
    print("Resultant of the above force system is in 3rd Quadrant ")
if sum_Fx>0 and sum_Fy<0:
    print("Resultant of the above force system is in 4th Quadrant ")    
if sum_Fx==0 and sum_Fx==0:
    print("Resultant = 0")  
else :
    print(" Resultant = " , Res)   

    