import cv2
import numpy as np
from Tkinter import Tk
from tkFileDialog import askopenfilename

Tk().withdraw() # we don't want a full GUI, so keep the root window from appearing
filename = askopenfilename() # show an "Open" dialog box and return the path to the selected file
print(filename)
image = cv2.imread(filename)

print ('If you want "erosion" press: 1')
print ('If you want "dilation" press: 2')
print ('If you want "opening" press: 3')
print ('If you want "closing" press: 4')

kernel = np.ones((5,5),np.uint8)
cv2.imshow("original image",image)

m=1

while True:   
    ch = 0xFF & cv2.waitKey()
    if ch == 27:
        break
    if ch == ord('1'):
        erosion = cv2.erode(image,kernel,iterations = 1)
        cv2.imshow("erosion image",erosion)
    if ch == ord('2'):
        dilation = cv2.dilate(image,kernel,iterations = 1)
        cv2.imshow("dilation image",dilation)
    if ch == ord('3'):
        opening = cv2.morphologyEx(image, cv2.MORPH_OPEN, kernel)
        cv2.imshow("opening image",opening)
    if ch == ord('4'):
        closing = cv2.morphologyEx(image, cv2.MORPH_CLOSE, kernel)
        cv2.imshow("closing image",closing)

cv2.waitKey(0)
cv2.destroyAllWindows()