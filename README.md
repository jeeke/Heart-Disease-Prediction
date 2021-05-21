# Health-Disease-Prediction
![](https://upload.wikimedia.org/wikipedia/en/thumb/9/96/Logo_of_NIT_Jalandhar.png/220px-Logo_of_NIT_Jalandhar.png)

### This is a 2021 BTech final year college project developed under the guidance of our mentor 
### [Dr. Prashant Kumar - Department of CSE, NIT Jalandhar](https://www.nitj.ac.in/index.php/nitj_cinfo/Faculty/201 "Dr. Prashant Kumar")<br></br>

#### Contributors : 
##### Gyanesh Kumar 17103033
##### Sonal Keshari 17103083

#### [OPEN SOURCE LICENSE - Apache License 2.0](./LICENSE)<br></br>


#### Project Description :

This project aims to predict whether a particular person has some risk of heart problem or not.
It uses An Android mobile app to interact with the end users and predicts result based on predefined characteristics / attributes.

#### Note - 
- To use the app you just have to install the apk, the [Backend](https://hdp-backend.herokuapp.com/admin) and the [ML Model](https://hdp-ml-predictor.herokuapp.com/train) are already deployed and configured on Heroku
- Before using the app first time, please open both the link once, as heroku shuts down the server if they are idle for long time

#### Software Workflow :
![](https://github.com/jeeke/Heart-Disease-Prediction/blob/main/images/workflow.jpg)

- Android App Calls APIs of Python Flask Rest API for
predicting the results
- Flask API internally uses our ML model to predict result
- For other Rest APIs Android App calls the NodeJs Backend
- Data on App can be added by accessing the admin panel of
NodeJs Admin
- NodeJs Backend is connected to PostgreSQL Database

#### Working Project Description : 

##### Android App
##### Supported Languages
- To increase the accessibility of our app we have given the support for
English as well as Hindi.
- During the first usage of the app or from clicking the globe icon we can
launch the “Choose Language” screen and change the preferred language.

![](https://github.com/jeeke/Heart-Disease-Prediction/blob/main/screenshots/app_1.png)<br></br>

##### Home Screen
- On the home screen, a brief is shown about the app and the motivation
behind it.
- Click the “TAKE THE TEST NOW” button launches the form screen for
heart disease prediction<br></br>
![](https://github.com/jeeke/Heart-Disease-Prediction/blob/main/screenshots/app_2_en.png)<br></br>

##### Prediction Response
![](https://github.com/jeeke/Heart-Disease-Prediction/blob/main/screenshots/app_10.png)<br></br>
![](https://github.com/jeeke/Heart-Disease-Prediction/blob/main/screenshots/app_11.png)<br></br>

##### Admin Panel
![](https://github.com/jeeke/Heart-Disease-Prediction/blob/main/screenshots/Screenshot%20from%202021-05-18%2015-54-29.png)<br></br>

##### Train/Untrain ML Model
![](https://github.com/jeeke/Heart-Disease-Prediction/blob/main/screenshots/Screenshot%20from%202021-05-18%2015-53-06.png)<br></br>
![](https://github.com/jeeke/Heart-Disease-Prediction/blob/main/screenshots/Screenshot%20from%202021-05-18%2015-53-23.png)<br></br>


#### For More Detailed Guide - [READ OUR REPORT](./HDP_REPORT.pdf)<br></br>


