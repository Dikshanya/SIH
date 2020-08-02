#!/usr/bin/python
from statistics import mean 
import sys
from wwo_hist import retrieve_hist_data
import datetime 
import pandas as pd  
frequency=3
datex = datetime.datetime.now()
start_date = str(datex.month)+'-'+str(datex.day)+'-'+str(datex.year-1)
end_date = str(datex.month)+'-'+str(datex.day)+'-'+str(datex.year)
api_key = '06909010f11242eaae354051202907'
location_list = [str(sys.argv[1])]

hist_weather_data = retrieve_hist_data(api_key,
                                location_list,
                                start_date,
                                end_date,
                                frequency,
                                location_label = False,
                                export_csv = True,
                                store_df = True)
data = pd.read_csv(str(location_list[0])+".csv")
print(data.mean(axis = 0)) 
