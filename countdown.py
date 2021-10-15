import time

def countdowntimer(time_sec):
    while time_sec:
        mins, secs = divmod(time_sec, 60)
        timeform = '{:02d}:{:02d}'.format(mins, secs)
        print(timeform, end='\r')
        time.sleep(1)
        time_sec -= 1

    print("stop")
#countdown for 30 seconds
countdowntimer(30)
