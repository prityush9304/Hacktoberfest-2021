#pip install all the packages present after import statement
#The program uses weather api
#The arguments that must given are the Valid city names
#Null Handling is taken care of
#It can handle invalid city names too
#Thank You Hacktoberfest 2021




import urllib.request
import pyttsx3
import json


api_key = '48a90ac42caa09f90dcaeee4096b9e53'
city=input("Enter The City Name :")
try:
    source = urllib.request.urlopen('http://api.openweathermap.org/data/2.5/weather?q=' + city + '&appid='+api_key).read()
    list_of_data = json.loads(source)
    data = {
        "country_code": str(list_of_data['sys']['country']),
        "coordinate": str(list_of_data['coord']['lon']) + ' ' + str(list_of_data['coord']['lat']),
        "temp": str(list_of_data['main']['temp']) + 'k',
        "pressure": str(list_of_data['main']['pressure']),
        "humidity": str(list_of_data['main']['humidity']),
        "cityname":str(city),
        }
    engine = pyttsx3.init()
    engine.say("The weather Report at "+ str(city) + "says that the temperature is at"+str(list_of_data['main']['temp'])+"kelvin"  +"with the humidity of"+str(list_of_data['main']['humidity'])+"and pressure "+str(list_of_data['main']['pressure'])+""+"Thank you")
    engine.setProperty('rate',0.1)
    engine.setProperty('volume', 0.9)
    engine.runAndWait()
    print("Thank you")
except:
    print("Check The City Name Given")

