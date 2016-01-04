import cv2
from Tkinter import Tk
from tkFileDialog import askopenfilename

Tk().withdraw() # we don't want a full GUI, so keep the root window from appearing
filename = askopenfilename() # show an "Open" dialog box and return the path to the selected file
print(filename)
image = cv2.imread(filename)

size = float (input('Please write size from 0.1 to 1 to resize:'))

# we need to keep in mind aspect ratio so the image does
# not look skewed or distorted -- therefore, we calculate
# the ratio of the new image to the old image

newx, newy = int(image.shape[1]*size), int(image.shape[0]*size) #new size (w,h)
if input:
    newimage = cv2.resize(image,(newx,newy))
cv2.imshow("original image",image)
cv2.imshow("resize image",newimage)
cv2.waitKey(0)
cv2.destroyAllWindows()