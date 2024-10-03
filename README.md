# emotion-detection
This Java-based project uses OpenCV for face detection, recognition, and emotion analysis. It detects faces from images or video, recognizes known faces, and analyzes emotions. If unrecognized, it prompts for details and stores them in a MySQL database using JDBC. The system combines facial recognition, emotion detection, and database integration.

### Description:

This project is a Java-based application that integrates face detection, recognition, emotion detection, and database management. Using **OpenCV**, it detects faces in real-time from images or live video feeds, recognizes known faces, and analyzes emotions. If a face is unrecognized, the system prompts the user for details and stores them in a database.

#### Features:

1. **Face Detection**:
   - Detects faces using **OpenCV** with **Haar Cascade** or **LBP** classifiers from images or live video feeds.

2. **Face Recognition**:
   - Recognizes faces using **Eigenfaces**, **Fisherfaces**, or **LBPH** algorithms, or integrates with APIs like **Amazon Rekognition** and **Microsoft Azure Face API**.

3. **Emotion Detection**:
   - Analyzes emotions (e.g., happy, sad, angry) using machine learning models such as **CNNs** through libraries like **DL4J** or **TensorFlow**. External APIs like **Microsoft Azure Face API** can also be used for emotion detection.

4. **Database Integration**:
   - Stores unrecognized user details (e.g., name, age) along with facial data in a **MySQL** or **SQLite** database using **JDBC**.

#### Workflow:

1. Detect face with OpenCV.
2. Recognize the face or prompt for details if unrecognized.
3. Detect and display emotions.
4. Store unrecognized user data.

This project combines face recognition, emotion analysis, and database management into one integrated system.
