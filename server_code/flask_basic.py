from flask import Flask, jsonify, render_template
from flask import request
import numpy as np
import pickle
from statistics import mean 
import sys
from wwo_hist import retrieve_hist_data
import datetime 
import pandas as pd 
app = Flask(__name__)
model = pickle.load(open('model.pkl','rb'))

@app.route('/get_ph',methods=['POST'])
def get_ph():
    print(request.get_json())
    rgb = request.get_json()
    print(rgb['rgb'])

    final_features = [np.array(rgb["rgb"])]
    prediction = model.predict(final_features)

    output = round(prediction[0], 4)

    return "{}".format(output)

@app.route('/get_weather',methods=['POST'])
def get_weather():
    frequency=3
    datex = datetime.datetime.now()
    start_date = str(datex.month-2)+'-'+str(datex.day)+'-'+str(datex.year)
    end_date = str(datex.month)+'-'+str(datex.day)+'-'+str(datex.year)
    api_key = '06909010f11242eaae354051202907'
    location = rgb = request.get_json()
    location_list = [(location["location"])]

    hist_weather_data = retrieve_hist_data(api_key,
                                    location_list,
                                    start_date,
                                    end_date,
                                    frequency,
                                    location_label = False,
                                    export_csv = True,
                                    store_df = True)
    data = pd.read_csv(str(location_list[0])+".csv")
    maxTempValue = round((data["maxtempC"].mean(axis = 0)),2) 
    minTempValue = round((data["mintempC"].mean(axis = 0)),2)
    feelsLikeValue = round((data["FeelsLikeC"].mean(axis = 0)),2) 
    humidityValue = round((data["humidity"].mean(axis = 0)),2)
    rainfallValue = round((data["precipMM"].mean(axis = 0)),2)
    tempValue = round((data["tempC"].mean(axis = 0)),2)
    return "{}".format( str(humidityValue)+" " + str(tempValue) +" "+ str(rainfallValue))

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
