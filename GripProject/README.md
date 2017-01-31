INSTALLATION:
=============
* Make sure your robot transmits the webcam (for java/c++ see SimpleVision example), and it shows up on the Dashboard
* Install python 3, select "add to PATH"
* Install numpy and opencv from here http://www.lfd.uci.edu/~gohlke/pythonlibs/ (for 32 bit probably, depends on python install)
* Open cmd and type "pip3 install <name of whl file>" for both files (Use the Tab button).
* Type "pip3 install pynetworktables"
* Install grip to create and edit pipelines
* Create or edit pipeline as needed (or modify project.grip file)
* Export pipeline file (grip.py), as python. Make sure to export to the same directory as main.py and post.py. **Do not change default parameters.**
* Edit main.py at this line:

```python
===> cv2.VideoCapture('http://roborio-1573-frc.local:1181/stream.mjpg') <===
```

* to your team number
* where 1573 is your team number
* Edit `post.py` as needed, you can use `cv2.imshow` to create windows, just open `grip.py` and if you want to show (eg):
```python
self.hsl_threshold_0_output
```
* then put
```python
cv2.imshow("My Window", pipeline.hsl_threshold_0_output)
```
* in post.py
* Launch main.py (open cmd, type "python main.py")
* Connect to the robot (It could have been connected this whole time)

BUGS:
=====
* Closing a window won't close the program, this is an opencv limitation, need to implement key to exit. Use Ctrl+C on cmd or close cmd