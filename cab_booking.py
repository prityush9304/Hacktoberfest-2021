import pandas as pd
while(1):
    menu = {1:"Driver Login",
            2:"Customer Login",
            3:"ZULA Administarator",
            4:"Exit"}

    intial_cab_drivers = {"id":[1,2,3,4],
                        "Name":["aaa","bbb","ccc","ddd"],
                        "Pass":[111,222,333,444],
                        "Age":[25,36,31,28] }

    intial_customers = {"id":[1,2,3,4],
                        "Name":["ww","xx","yy","zz"],
                        "Pass":[55,66,77,88],
                        "Age":[25,36,31,28]
                        }
    intial_locations = {"id":[1,3,4,6,2,7,8,5],
                        "Name":["A","C","D","F","B","G","H","E"],
                        "Dist_from_origin":[0,4,7,9,15,18,20,23] 
                        }
    intial_cab_positions = {
        "Location":["D","G","H","A"],
        "cabid":[1,2,3,4]
    }
    cabdrivers_summary = {
        "cabid":{1:
                    {"Source":["D","E","C"],
                    "Destination":["H","G","B"],
                    "CustomerDetail":[4,2,2],
                    "Fare":[130,50,110],
                    "ZulaCommision":[39,15,33]

                    },2:{"Source":["C","E","D"],
                    "Destination":["B","G","H"],
                    "CustomerDetail":[4,3,2],
                    "Fare":[145,50,187],
                    "ZulaCommision":[87,25,55]

                    },
                    3:{"Source":["F","E","D","H"],
                    "Destination":["A","B","G","E"],
                    "CustomerDetail":[2,3,4,7],
                    "Fare":[187,150,145,96],
                    "ZulaCommision":[55,58,36,47]

                    },
                    4:{"Source":["A","C","B"],
                    "Destination":["E","H","E"],
                    "CustomerDetail":[5,4,1],
                    "Fare":[125,30,158],
                    "ZulaCommision":[65,5,35]

                    }

                    }
    }
    customer_ride_summary = {"custid":{1:
                                        {
                                            "Source":["A","E","C"],
                                            "Destination":["E","G","B"],
                                            "Cab Detail":[3,1,1],
                                            "Fare":[230,50,110]
                                        },
                                        2:
                                        {
                                            "Source":["H","E","G"],
                                            "Destination":["A","G","H"],
                                            "Cab Detail":[4,2,2],
                                            "Fare":[220,40,100]
                                        },
                                        3:
                                        {
                                            "Source":["A","E","C"],
                                            "Destination":["E","G","B"],
                                            "Cab Detail":[5,3,2],
                                            "Fare":[225,45,115]
                                        },
                                        4:
                                        {
                                            "Source":["H","E","F"],
                                            "Destination":["F","H","G"],
                                            "Cab Detail":[5,2,3],
                                            "Fare":[150,45,86]
                                        },
                                        
                                        }
                                        }
    cab_summary = {"cabid":{1:{"Total Number of Trips":3,
                                "Total Fare Collected":290,
                                "Total Zula Commision":87
                                    },
                            2:{"Total Number of Trips":10,
                                "Total Fare Collected":2900,
                                "Total Zula Commision":1000
                                    },
                            3:{"Total Number of Trips":7,
                                "Total Fare Collected":1500,
                                "Total Zula Commision":500
                                    },
                            4:{"Total Number of Trips":5,
                                "Total Fare Collected":700,
                                "Total Zula Commision":150
                                    }

                            }
    }
    Welcome = ["Welcome to  !!*** ZULA***!!","1.Cab driver login","2.Customer login","3.Administration","4.Quit","Please choose a service"]
    for i in Welcome:
        print(i)
    option = int(input())
    if option==1:
        id = int(input("Enter your ID: "))
        password = int(input("Enter your password: "))
        if id in intial_cab_drivers["id"] and password in intial_cab_drivers["Pass"]:
            print("Congratulations You are logged in!")
            inp_ = input("Press 1 to know your summary!\nPress 2 to continue\n")
            if inp_=="1":
                cabid = id
                print("Cabid: ",cabid)
                print("Cab Driver Name: ",intial_cab_drivers["Name"][cabid-1])
                print("Trip Details")
                print(pd.DataFrame(cabdrivers_summary["cabid"][cabid]))
                continue
        else:
            if id not in intial_cab_drivers["id"]:
                    print("Please Enter Your Id correctly")
            else:
                print("Check Your Password and Try Again")
        continue
    elif option==2:
        print("1.Login")
        print("2.Create Account")
        print("Choose one option from above")
        cust = int(input())
        # while(1):
        if cust==1:
            id = int(input("Enter your ID: "))
            password = int(input("Enter your password: "))
            if id in intial_customers["id"] and password in intial_customers["Pass"]:
                    # print("Congratulations You are logged in!")
                while(1):
                
                    inp_ = input("Press 1 to know your summary!\nPress 2 to continue\n")
                    if inp_=="1":
                        custid = id
                        print("Customerid: ",custid)
                        print("Customer Name: ",intial_customers["Name"][custid-1])
                        print("Trip Details")
                        print(pd.DataFrame(customer_ride_summary["custid"][custid]))
                    print("Availble Locations are------------------->")
                    print(intial_locations["Name"])
                    source = input("Choose source location: ").upper()
                    destination = input("Choose destination location: ").upper()
                    # if source== destination:
                    #     print("Invalid Ride")
                    #     continue
                    locs = intial_locations["Name"]
                    dist = intial_locations["Dist_from_origin"]
                    fare = abs(dist[locs.index(source)] - dist[locs.index(destination)])*10
                    print()
                    print(f"Your Estimasted Fare is {fare}Rs!")
                    print()
                    print("CAB LOCATIONS!!!")
                    print(pd.DataFrame(intial_cab_positions))
                    print()
                    cabride = input("Press Y if you want to start your ride or Press N to Quit ")
                    if cabride.lower()=="n":
                        break
                    distances = intial_cab_positions["Location"]
                    source_ = dist[locs.index(source)]
                    mini = 10000
                    cab_location,cabid = "",1000        
                    for i in distances:
                        index = intial_locations["Name"].index(i)
                        temp = intial_locations["Dist_from_origin"][index]
                        dis = temp - source_
                        if dis < mini:
                            mini = dis
                            cab_location = i
                            cabidindex = intial_cab_positions["Location"].index(i)
                            cabid = intial_cab_positions["cabid"][cabidindex]
                    print(f"Near Available cab is CABID:{cabid},CABLOCATION:{cab_location} ")
                            
                    if cabride.lower()=="y":
                        print("Your Ride Started!")
                else:
                    if id not in intial_customers["id"]:
                        print("Please Enter Your Id correctly")
                    else:
                        print("Check Your Password and Try Again")
                    n = input("N to quit")
                    if n.lower()=='n':
                        break 
                
            elif cust==2:
                id_ = int(input("Enter id "))
                name_ = input("Enter Your Name: ")
                pass_ = input("Set Your Password: ")
                age_ = input("Enter Your Age") 
                intial_customers["id"].append(id_) 
                intial_customers["Name"].append(name_)
                intial_customers["Pass"].append(pass_)
                intial_customers["Age"].append(age_)
                print("Thank you account has been sucessfully created!") 
                break   
        
    elif option==3:
        inp = input("Press 1 to see Cabs Summary")
        if inp=="1":
            cabid = int(input("Enter cabid: "))
            print("Cabid: ",cabid)
            print("Cab Driver Name: ",intial_cab_drivers["Name"][cabid-1])
            print("Total Number of Trips: ",cab_summary["cabid"][cabid]["Total Number of Trips"])
            print("Total Fare Collected: ",cab_summary["cabid"][cabid]["Total Fare Collected"])
            print("Total Zula Commision: ",cab_summary["cabid"][cabid]["Total Zula Commision"])
            print("Trip Details--->")
            print(pd.DataFrame(cabdrivers_summary["cabid"][cabid]))
            print()
        continue
    elif option==4:
        print("Thank you!")
        break

