package opencv_camera;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;

public class OpenCV_Camera extends JFrame{

    private JLabel cameraScreen;
    private JButton btnCapture;
    private VideoCapture capture;
    private Mat image;
    private boolean clicked = false;
    private CascadeClassifier faceCascade;

    public OpenCV_Camera(){
        setLayout(null);

        // Initialize the face cascade classifier
        faceCascade = new CascadeClassifier("data/haarcascade_frontalface_alt2.xml");

        cameraScreen = new JLabel();
        cameraScreen.setBounds(0, 0, 640, 480);
        add(cameraScreen);

        btnCapture = new JButton("capture");
        btnCapture.setBounds(300, 480, 80, 40);
        add(btnCapture);

        btnCapture.addActionListener((ActionEvent e) -> {
            clicked = true;
        });

        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                capture.release();
                image.release();
                System.exit(0);                
            }
        });

        setSize(new Dimension(640, 560));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void startCamera(){
        capture = new VideoCapture(0);
        image = new Mat();
        byte[] imageData;

        ImageIcon icon;
        while(true){
            capture.read(image);  // Read image from the camera in matrix form

            if (!image.empty()) {
                detectAndDrawFaces(image);  // Detect and draw faces on each frame
                
                final MatOfByte buf = new MatOfByte(); // Convert matrix to byte array
                Imgcodecs.imencode(".jpg", image, buf);
                imageData = buf.toArray();

                icon = new ImageIcon(imageData);
                cameraScreen.setIcon(icon);

                if (clicked) {
                    String name = JOptionPane.showInputDialog(this, "Enter image name");
                    if (name == null) {
                        name = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
                    }
                    Imgcodecs.imwrite("images/" + name + ".jpg", image);  // Save captured image
                    clicked = false;
                }
            }
        }
    }

    // Method to detect faces and draw rectangles around them
    private void detectAndDrawFaces(Mat frame) {
        MatOfRect faces = new MatOfRect();
        Mat grayFrame = new Mat();
        
        Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);  // Convert to grayscale
        Imgproc.equalizeHist(grayFrame, grayFrame);  // Improve contrast

        int height = grayFrame.height();
        int absoluteFaceSize = Math.round(height * 0.2f);  // Set minimum face size
        
        // Detect faces
        faceCascade.detectMultiScale(grayFrame, faces, 1.1, 2, Objdetect.CASCADE_SCALE_IMAGE,
                                     new Size(absoluteFaceSize, absoluteFaceSize), new Size());

        Rect[] faceArray = faces.toArray();
        for (Rect face : faceArray) {
            Imgproc.rectangle(frame, face, new Scalar(0, 0, 255), 3);  // Draw rectangle around the face
        }
    }

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        EventQueue.invokeLater(() -> {
            OpenCV_Camera camera = new OpenCV_Camera();
            new Thread(() -> {
                camera.startCamera();
            }).start();
        });
    }
}
