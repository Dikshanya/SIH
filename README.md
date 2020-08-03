
![](https://github.com/Dikshanya/SIH/blob/master/images/agroracleLogo.png)
# Agroracle


**AgrOracle** is a user-friendly mobile application that helps improve the crop yield by suggesting users different crops that are suitable for their soil, by : Estimating the **pH** of the soil using digital image processing and analyzing the weather parameters like **temperature**, **humidity** and **rainfall** in that location.

# Files

*Agroracletry* contains the code of the mobile application. The app was developed using android studio (version 4.0.1). Backend in Java and used Flask to connect the Machine Learning models with the app.
The server is run in localhost.

Folder *Datasets* contains the dataset used to train the machine learning models: 

1) pH Dataset - Sheet1.csv is used for pH prediction 

2) cpdata.csv is used for crop prediction

*CropPrediction* contains the ML model used to predict crops written in python. ph_model contains the ML model trained to predict the pH of the soil using the soil sample image.

*login_server* contains the code for running a server using Flask to host sqlite database, and to run the get_ph and get_weather functions that compute the pH and weather parameters.
On running the following command, the server is started :

`flask run --host=0.0.0.0`

*ResearchPapers* contains different reseach papers and sources that we referred to build our mobile app.

## Unique Selling Point of our app

 - Our app is able to compute the pH of the soil in real-time, that is, without the farmer having to visit a soil testing laboratory. With just a click of the mobile, he will be able to obtain the suitable crop that can be grown for his land.
 - Our app can also show the trend in pH over time with the help of data analytics in the form of moving averages, so that the farmer can have a picture of how his soil pH is varying over time.
 - Our app is also available in multiple regional languages.
