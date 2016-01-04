import cv2
import numpy as np
from Tkinter import Tk
from tkFileDialog import askopenfilename

Tk().withdraw() # we don't want a full GUI, so keep the root window from appearing
filename = askopenfilename() # show an "Open" dialog box and return the path to the selected file
print(filename)

def hist_lines(im):
    h = np.zeros((300,256,3))
    if len(im.shape)!=2:
        print "hist_lines applicable only for grayscale images"
        #print "so converting image to grayscale for representation"
        im = cv2.cvtColor(im,cv2.COLOR_BGR2GRAY)
    hist_item = cv2.calcHist([im],[0],None,[256],[0,256])
    cv2.normalize(hist_item,hist_item,0,255,cv2.NORM_MINMAX)
    hist=np.int32(np.around(hist_item))
    for x,y in enumerate(hist):
        cv2.line(h,(x,0),(x,y),(255,255,255))
    y = np.flipud(h)
    return y

im = cv2.imread(filename)

cv2.imshow('image',im)

gray = cv2.cvtColor(im,cv2.COLOR_BGR2GRAY)

equ = cv2.equalizeHist(gray)
lines = hist_lines(equ)
cv2.imshow('histograme_image', equ)
cv2.imshow('histogram', lines)

cv2.waitKey(0)
cv2.destroyAllWindows()