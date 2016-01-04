import cv2
import numpy as np
from Tkinter import Tk
from tkFileDialog import askopenfilename

Tk().withdraw() # we don't want a full GUI, so keep the root window from appearing
filename = askopenfilename() # show an "Open" dialog box and return the path to the selected file
print(filename)
image = cv2.imread(filename)

print ('If you want "smooth" press: 1')
toDo = int (input('If you want "blur" press: 2'))

cv2.imshow("original image",image)

if toDo == 2:
    blur = cv2.boxFilter(image,-1,(5,5))
    cv2.imshow("blur_image",blur)
elif toDo == 1:
    smooth = cv2.GaussianBlur(image,(5,5), 0)
    cv2.imshow("smooth_image",smooth)

cv2.waitKey(0)
cv2.destroyAllWindows()