import socket
import cv2
import time
# Build connection---------------------------------------------
HOST = "localhost"
PORT = 1994
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
try:
    s.bind((HOST, PORT))
except socket.error as err:
    print('Bind failed. Error Code : ' .format(err))
s.listen(10)
print("Socket Listening")
conn, addr = s.accept()
messageFromJava=str(conn.recv(512).decode("utf-8")) #javanın gonderdigi mesajı okuma
messageFromJava=messageFromJava[2:len(messageFromJava)] # mesajdaki istenmeyen karakterlerden kurtulma
#nameAndTime=messageFromJava.split(",")
#print(str(nameAndTime[0]))

#---------------------------------------------------------------


# Opencv--------------------------------------------------------
def detect(frame, temp, w, h):

    frame = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

    result = cv2.matchTemplate(frame, temp, cv2.TM_CCORR_NORMED)
    min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(result)

    top_left = max_loc
    bottom_right = (top_left[0] + w, top_left[1] + h)

    return (max_val, top_left, bottom_right)
def findMid(result):
    midPoint=int(((result[1][0])+int(result[2][0]))/2), int(((result[1][1])+int(result[2][1]))/2)

    return midPoint
if __name__ == '__main__':
    image = cv2.imread('Screenshot.png')
  #  print(nameAndTime[0])
    template = cv2.imread(messageFromJava, 0)

    tW, tH = template.shape[::-1]
    didWeFindIt=False
  #  for  i in range(int(nameAndTime[1])):
result = detect(image, template, tW, tH);
print(result[1][1])
print(result)
print(findMid(result))
cv2.rectangle(image, *result[1:], (0, 255, 0), 2)
# cv2.imshow('match', image)
if (result[0] > 0.99):
    conn.send(bytes(str(findMid(result)) + "\r\n", 'UTF-8'))
    print("Message sent")
    didWeFindIt = True



if not(didWeFindIt):
    conn.send(bytes("Null" + "\r\n", 'UTF-8'))
s.close()
