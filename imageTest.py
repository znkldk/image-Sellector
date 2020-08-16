import cv2


def detect(frame, temp, w, h):
    """
    @param frame: templatenin içinde aranacağı fotoğraf
    @param temp : template
    @param w: templatenin genişliği
    @param h: templatenin yüksekliği
    """

    frame = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

    result = cv2.matchTemplate(frame, temp, cv2.TM_CCORR_NORMED)
    min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(result)

    top_left = max_loc
    bottom_right = (top_left[0] + w, top_left[1] + h)

    return (max_val, top_left, bottom_right)


if __name__ == '__main__':
    image = cv2.imread('temp_1.png')
    template = cv2.imread('temp_2.png', 0)

    tW, tH = template.shape[::-1]
    result = detect(image, template, tW, tH);
    print(result)

    cv2.rectangle(image, *result[1:], (0, 255, 0), 2)
    cv2.imshow('match', image)

    if cv2.waitKey(0) & 0xFF == ord('q'):
        cv2.destroyAllWindows()