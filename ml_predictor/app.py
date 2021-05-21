# Dependencies
from flask import Flask, request, jsonify
import traceback
import json
import requests
from datetime import datetime

import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler

from sklearn.neighbors import KNeighborsClassifier

from sklearn import metrics

# from sklearn.externals import joblib

# Your API definition
app = Flask(__name__)
global knn, scaler
knn = None
scaler = StandardScaler()


@app.route('/', methods=['GET'])
def root():
    return jsonify({'message': "Welcome to Heart Disease Prediction\'s ML Model API"})


@app.route('/untrain', methods=['GET'])
def untrain():
    global knn
    knn = None
    return jsonify({'message': "Model Untrained"})


@app.route('/train', methods=['GET'])
def train():
    dataset = pd.read_csv('dataset.csv')

    dataset = pd.get_dummies(dataset, columns = ['sex', 'cp', 'fbs', 'restecg', 'exang', 'slope', 'ca', 'thal'])
    columns_to_scale = ['age', 'trestbps', 'chol', 'thalach', 'oldpeak']
    dataset[columns_to_scale] = scaler.fit_transform(dataset[columns_to_scale])

    y = dataset['target']
    X = dataset.drop(['target'], axis = 1)
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size = 0.33, random_state = 0)

    global knn
    knn = KNeighborsClassifier(n_neighbors = 8)
    knn.fit(X_train, y_train)

    return jsonify({'message': "Model Trained"})


@app.route('/predict', methods=['POST'])
def predict():
    global knn
    x = knn
    if x == None:
        return jsonify({'message': 'Please train the model first'})
    else:
        try:
            r = request.json
            print(r)
            p = {
                'age': r['age'],
                'trestbps': r['trestbps'], 
                'chol': r['chol'], 
                'thalach': r['thalach'],
                'oldpeak': r['oldpeak'],

                'sex_0': 0,'sex_1': 0,
                
                'cp_0':0,  'cp_1':0,    'cp_2':0,    'cp_3':0, 
                
                'fbs_0': 0,  'fbs_1': 0,
                
                'restecg_0': 0,  'restecg_1': 0, 'restecg_2': 0,
                
                'exang_0': 0,'exang_1': 0,
                
                'slope_0': 0, 'slope_1': 0, 'slope_2': 0,
                
                'ca_0': 0,'ca_1': 0, 'ca_2': 0, 'ca_3': 0, 'ca_4': 0,
                
                'thal_0': 0, 'thal_1': 0, 'thal_2': 0, 'thal_3': 0,
            }

            p['sex_' + str(r['sex'])] = 1
            p['cp_' + str(r['cp'])] = 1
            p['ca_' + str(r['ca'])] = 1
            p['fbs_' + str(r['fbs'])] = 1
            p['thal_' + str(r['thal'])] = 1
            p['slope_' + str(r['slope'])] = 1
            p['exang_' + str(r['exang'])] = 1
            p['restecg_' + str(r['restecg'])] = 1

            # Creates DataFrame.
            df = pd.DataFrame([p])

            columns_to_scale = ['age', 'trestbps', 'chol', 'thalach', 'oldpeak']

            global scaler
            df[columns_to_scale] = scaler.transform(df[columns_to_scale])
            return jsonify({
                'risk': str(x.predict(df)[0]),
                'created_at': str(datetime.timestamp(datetime.now())).replace('.','')
            })
        except:
            return jsonify({'trace': traceback.format_exc()})

if __name__ == '__main__':
    with app.app_context():
        train()
    app.run(host='0.0.0.0')
    # app.run()