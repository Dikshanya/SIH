

![](images/agroracleLogo.png)

**AgrOracle** is a user-friendly mobile application that helps improve the crop yield by suggesting users different crops that are suitable for their soil, by : 
Estimating the pH of the soil using digital image processing and analyzing the weather parameters like temperature, humidity and rainfall in that location. 



Agroracletry contains the code of the mobile application.The app was developed using android studio (version 4.0.1).
Backend in java and used Flask to connect the Machine Learning models with the app.

Folder Datasets contains the dataset used to train the machine learning models:
1)pH Dataset - Sheet1.csv is used for pH prediction 
2)cpdata.csv is used for crop prediction

CropPrediction contains the ML model used to predict crops written in python
ph_model contains the ML model trained to predict the pH of the soil using the soil sample image.

ResearchPapers contains different reseach papers and sources that we referred to build our mobile app.


