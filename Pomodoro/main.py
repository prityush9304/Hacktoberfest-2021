from tkinter import *
import math

# ---------------------------- CONSTANTS ------------------------------- #
PINK = "#e2979c"
RED = "#e7305b"
GREEN = "#9bdeac"
YELLOW = "#f7f5dd"
FONT_NAME = "Courier"
WORK_MIN = 25
SHORT_BREAK_MIN = 5
LONG_BREAK_MIN = 20
reps = 0
k = None

# ---------------------------- TIMER RESET ------------------------------- # 

def reset_time():
    windows.after_cancel(k)
    timer.config(text = "TIMER")
    checkmark.config(text = "")
    canvas.itemconfig(timer_, text = "00:00")
    global reps
    reps = 0






# ---------------------------- TIMER MECHANISM ------------------------------- # 

def timer_starter():
    global reps
    reps += 1


    if reps == 8:
        count_down(LONG_BREAK_MIN * 60)
        timer.config(text = "BREAK", fg = RED)
    elif reps % 2 == 0:
       count_down(SHORT_BREAK_MIN * 60)
       timer.config(text = "BREAK", fg = RED)
    else:
        count_down(WORK_MIN * 60)
        timer.config(text = "WORK", fg = GREEN)


# ---------------------------- COUNTDOWN MECHANISM ------------------------------- # 

def count_down(count):
    count_min = math.floor(count / 60)
    count_sec = count % 60
    if count_sec == 0:
        count_sec = "00"
    elif count_sec < 10:
        count_sec = f"0{count-1}"

    if count >= 0:
        global k
        k = windows.after(1000, count_down,count-1)
        canvas.itemconfig(timer_,text =f"{count_min}:{count_sec}")
    else:
        global reps
        timer_starter()
        jackboys = math.floor(reps % 2)
        mark = ""

        for _ in range(jackboys):
            mark += "✓"

        checkmark.config(text = mark)



# ---------------------------- UI SETUP ------------------------------- #
windows = Tk()
windows.title("POMODORA ")
windows.config(padx = 100 , pady = 50, bg = YELLOW)




image_png   = PhotoImage(file="tomato.png")
canvas = Canvas(width = 200,height = 224, bg = YELLOW, highlightthickness =0)
canvas.create_image(100,112 ,image = image_png)
timer_ = canvas.create_text(100,130,text = "00:00" ,fill = "white",font =(FONT_NAME,35,"bold"))
canvas.grid(column =1,row =1)

timer = Label(text = " TIMER", font = (FONT_NAME,24, "bold"), fg = GREEN,bg = YELLOW)
timer.grid(row = 0 ,column = 1)

start_button = Button(text = "START", command = timer_starter)
start_button.grid(column = 0, row = 3)


rest_button = Button(text = "RESET", command = reset_time)
rest_button.grid(column = 2, row = 3)


checkmark = Label(text = "",  font = (FONT_NAME,24,"bold"),fg = GREEN, bg = YELLOW)
checkmark.grid(column = 1, row = 4)
windows.mainloop()
