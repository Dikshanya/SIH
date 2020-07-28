from flask import Flask, jsonify, render_template
from flask import request
import numpy as np
import pickle
app = Flask(__name__)
model = pickle.load(open('model.pkl','rb'))
# @app.route('/', methods=['POST', 'GET'])
# def index():
    # if(request.method == 'POST'):
        # some_json = request.get_json()
        # return jsonify({'you sent : ': some_json}), 201
    # else:
        # return jsonify({"about": "Hello World"})
        
@app.route('/')
def home():
    return render_template('index.html')

@app.route('/predict',methods=['POST'])
def predict():
    '''
    For rendering results on HTML GUI
    '''
    int_features = [int(x) for x in request.form.values()]
    final_features = [np.array(int_features)]
    prediction = model.predict(final_features)

    output = round(prediction[0], 3)

    return render_template('index.html', prediction_text='pH of soil is  {}'.format(output))

@app.route('/get_ph',methods=['POST'])
def get_ph():
    print(request.get_json())
    rgb = request.get_json()
    print(rgb['rgb'])

    final_features = [np.array(rgb["rgb"])]
    prediction = model.predict(final_features)

    output = round(prediction[0], 3)

    return "{}".format(output)

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
