from tkinter import *
from PIL import ImageTk, Image

root = Tk()
root.title("Icon, Image and Exit")
loc1 = r'image location'
loc2 = r'image location'
loc3 = r'image location'
# You can add more locations 

img1 = ImageTk.PhotoImage(Image.open(loc1))
img2 = ImageTk.PhotoImage(Image.open(loc2))
img3 = ImageTk.PhotoImage(Image.open(loc3))

IMG = [img1, img2, img3]

stat = Label(root, text="image 1 of"+ str(len(IMG)), bd=1, relief=SUNKEN, anchor=E)
lb = Label(image=img1)
lb.grid(row=0, column=0, columnspan=3)

def forward(num):
    global lb
    global btn_back
    global btn_ford

    lb.grid_forget()
    lb = Label(image=IMG[num])
    lb.grid(row=0, column=0, columnspan=3)

    btn_back = Button(root, text="<=", command=lambda :back(num-1))
    btn_ford = Button(root, text="=>", command=lambda: forward(num+1))
    stat = Label(root, text="image " + str(num+1) +"of"+ str(len(IMG)), bd=1, relief=SUNKEN, anchor=E)
    stat.grid(row=2, column=0, columnspan=3,sticky=W+E)

    if num == 2:
        btn_ford = Button(root, text="=>", state=DISABLED)

    btn_back.grid(row=1, column=0)
    btn_ford.grid(row=1, column=2)
    
def back(num):
    global lb
    global btn_back
    global btn_ford

    lb.grid_forget()
    lb = Label(image=IMG[num])
    lb.grid(row=0, column=0, columnspan=3)

    btn_back = Button(root, text="<=", command=lambda :back(num-1))
    btn_ford = Button(root, text="=>", command=lambda: forward(num+1))

    if num == 0:
        btn_back = Button(root, text="<=", state=DISABLED)

    btn_back.grid(row=1, column=0)
    btn_ford.grid(row=1, column=2)

    stat = Label(root, text="image " + str(num+1) +"of"+ str(len(IMG)), bd=1, relief=SUNKEN, anchor=E)
    stat.grid(row=2, column=0, columnspan=3,sticky=W+E)    


btn_back = Button(root, text="<=", state=DISABLED)
btn_quit = Button(root, text="Quit", command=root.quit)
btn_ford = Button(root, text="=>", command=lambda: forward(1))

btn_back.grid(row=1, column=0)
btn_quit.grid(row=1, column=1)
btn_ford.grid(row=1, column=2)
stat.grid(row=2, column=0, columnspan=3,sticky=W+E)

root.mainloop()
