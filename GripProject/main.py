#!/usr/bin/python3

"""
Launcher program that sets up either a webcam or robot webcam stream for GRIP processing, and data sending using post.py
"""

"""
Copyright (c) 2017 David Shlemayev

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at http://mozilla.org/MPL/2.0/.
"""

import cv2
from grip import GripPipeline
import post
import numpy as np
import socket
import urllib.request
import struct

import time
import threading
import os
import sys

class RestarterThread(threading.Thread):
    def run(self):
        self.lastFrameTime = time.time()
        self.stopping = False
        while not self.stopping:
            if time.time() - self.lastFrameTime > 3:
                os.execv(sys.executable, ['python'] + sys.argv)
            time.sleep(0.1)

def main():
    restartThread = RestarterThread()
    restartThread.start()
    
    print('Initializing post-processor')
    post.init()
    
    print('Creating video capture')
    # From robot stream
    """
    stream = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    while True: # Loop until we successfully connected
        try:
            stream.settimeout(4.5)
            stream.connect(("roborio-1573-frc.local", 1181))
            print('Connected to video source')
            break # Get out of retry loop, as we were successful
        except (socket.timeout, OSError):
            print('Failed to connect to video source, retrying')
            stream = socket.socket(socket.AF_INET, socket.SOCK_STREAM) # Recreate new socket
            time.sleep(1.5)
    stream.send(struct.pack('!i', 30)) # FPS
    stream.send(struct.pack('!i', -1)) # HW_COMPRESSION
    stream.send(struct.pack('!i', 1))  # framesize
    """
    
    # From MJPEG
    cap = cv2.VideoCapture('http://roborio-1573-frc.local:1181/stream.mjpg')
    
    # From webcam
    """
    cap = cv2.VideoCapture(0)
    print('Connected to video source')
    """

    print('Creating pipeline')
    pipeline = GripPipeline()

    print('Running pipeline')
    # From robot stream
    """sbytes = bytearray()
    while True:
        # TODO: Insert Try Except, and restart on error
        newb = stream.recv(1024)
        sbytes += newb
        a = sbytes.find(b'\xff\xd8')
        b = sbytes.find(b'\xff\xd9')
        if a != -1 and b != -1:
            jpg = sbytes[a:b+2]
            sbytes = sbytes[b+2:]
            if len(jpg) == 0:
                continue
            frame = cv2.imdecode(np.fromstring(bytes(jpg), dtype=np.uint8), cv2.IMREAD_COLOR)
            pipeline.process(frame)
            post.process(frame, pipeline)
    """
    
    # From webcam or MJPEG
    while cap.isOpened() and not restartThread.stopping:
        try:
            have_frame, frame = cap.read()
            if have_frame:
                pipeline.process(frame)
                post.process(frame, pipeline)
                restartThread.lastFrameTime = time.time()
        except:
            restartThread.stopping = True
            
    

    print('Capture closed')


if __name__ == '__main__':
    main()
