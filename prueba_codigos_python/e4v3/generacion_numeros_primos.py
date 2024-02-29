n = int(input())

if (n <= 0):
    print('ERROR')
else:
    primos_encontrados = 0
    num = 2
    while (primos_encontrados < n):
        sw = False
        for i in range(2, num + 1, 1):
            j = 2
            sw = False
            while (j <= (num // 2) and sw == False):
                if (num % j == 0):
                    sw = True
                j += 1
        if (sw == False):
            print(num)
            primos_encontrados += 1
        num += 1