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

# @app.route('/', methods=['POST', 'GET'])
# def index():
    # if(request.method == 'POST'):
        # some_json = request.get_json()
        # return jsonify({'you sent : ': some_json}), 201
    # else:
        # return jsonify({"about": "Hello World"})
        
# @app.route('/')
# def home():
#     return render_template('index.html')

# @app.route('/predict',methods=['POST'])
# def predict():
#     '''
#     For rendering results on HTML GUI
#     '''
#     int_features = [int(x) for x in request.form.values()]
#     final_features = [np.array(int_features)]
#     prediction = model.predict(final_features)

#     output = round(prediction[0], 3)

#     return render_template('index.html', prediction_text='pH of soil is  {}'.format(output))

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

# @app.route('/register', methods=['POST'])
# def register():
#     if request.method == 'POST':
#         request_json = request.get_json()
#         username = request_json['username']
#         password = request_json['password']
#         phone = request_json['phone']
#         db = flask_db.get_db()
#         error = None

#         if not username:
#             error = 'Username is required.'
#         elif not password:
#             error = 'Password is required.'
#         elif db.execute(
#             'SELECT id FROM user WHERE username = ?', (username,)
#         ).fetchone() is not None:
#             error = 'User {} is already registered.'.format(username)

#         if error is None:
#             db.execute(
#                 'INSERT INTO user (username, password, phone) VALUES (?, ?, ?)',
#                 (username, generate_password_hash(password), phone)
#             )
#             db.commit()
#             return {"status_code": "200", "message": "Registration successful"}

#         return {"status_code": "400", "message": error}


# @app.route('/login', methods=['POST'])
# def login():
#     if request.method == 'POST':
#         username = request.form['username']
#         password = request.form['password']
#         db = flask_db.get_db()
#         error = None
#         user = db.execute(
#             'SELECT * FROM user WHERE username = ?', (username,)
#         ).fetchone()

#         if user is None:
#             error = 'Invalid Credentials'
#         elif not check_password_hash(user['password'], password):
#             error = 'Invalid Credentials'

#         if error is None:
#             return {"status_code": 200, "message": ''.join(choice(ascii_letters)for i in range(12))}

#     return {"status_code": 200, "message": error}


# @app.route('/login_with_phone', methods=['POST'])
# def login_with_phone():
#     if request.method == 'POST':
#         phone = request_json['phone']
#         db = flask_db.get_db()
#         error = None
#         user = db.execute(
#             'SELECT * FROM user WHERE phone = ?', (phone,)
#         ).fetchone()

#         if user is None:
#             error = 'Invalid Credentials'
#         elif not check_password_hash(user['password'], password):
#             error = 'Invalid Credentials'
#         # todo do SMS OTP stuff
#         if error is None:
#             return {"status_code": 200, "message": ''.join(choice(ascii_letters)for i in range(12))}

#     return {"status_code": 200, "message": error}


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')