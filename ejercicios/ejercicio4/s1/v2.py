    for i in range(2, n + 1, 1):
        j = 2
        sw = False
        while (j <= (n // 2) and sw == False):
            if (n % j == 0):
                sw = True
            j += 1
        if (sw == False):
            print(i)