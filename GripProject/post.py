#!/usr/bin/python3

"""
Post-processing program, takes the data from the GRIP outputs and publishes it to the robot, or displays it in a window
"""

"""
Copyright (c) 2017 David Shlemayev

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at http://mozilla.org/MPL/2.0/.
"""

import cv2
from networktables import NetworkTable
from grip import GripPipeline
import numpy as np
import operator
import heapq

def init():
    print('Initializing NetworkTables')
    NetworkTable.setClientMode()
    NetworkTable.setIPAddress('roborio-1573-frc.local')
    NetworkTable.initialize()

def process(frame, pipeline):
    table = NetworkTable.getTable('/SmartDashboard')
    table.putNumber('imgWidth', frame.shape[1])
    table.putNumber('imgHeight', frame.shape[0])
    #cv2.imshow("original", frame)
    #cv2.imshow("marks", pipeline.hsl_threshold_output)
    
    contourframe = frame.copy()
    
    if len(pipeline.convex_hulls_output) > 1:
        largestContours = heapq.nlargest(2, pipeline.convex_hulls_output, key=cv2.contourArea)
        
        leftContour = []
        rightContour = []
        if cv2.boundingRect(largestContours[0])[0] < cv2.boundingRect(largestContours[1])[0]:
            leftContour = largestContours[0]
            rightContour = largestContours[1]
        else:
            leftContour = largestContours[1]
            rightContour = largestContours[0]
        
        lx, ly, lw, lh = cv2.boundingRect(leftContour)
        rx, ry, rw, rh = cv2.boundingRect(rightContour)
        cv2.drawContours(contourframe, [leftContour], 0, (255,255,255), 1)
        cv2.drawContours(contourframe, [rightContour], 0, (255,255,255), 1)
        table.putNumber('LeftMarkX', lx)
        table.putNumber('LeftMarkWidth', lw)
        table.putNumber('LeftMarkHeight', lh)
        table.putNumber('RightMarkX', rx)
        table.putNumber('RightMarkWidth', rw)
        table.putNumber('RightMarkHeight', rh)
        table.putBoolean('MarksFound', True)
    else:
        table.putBoolean('MarksFound', False)
        pass
        
    cv2.imshow("contours", contourframe)
    cv2.waitKey(1)

    