n = int(input())

if (n <= 0):
    print('ERROR')
else:
    preant = 1
    ant = 1
    if (n == 1):
        print(preant)
    if (n == 2):
        print(preant)
        print(ant)
    if (n >= 3):
        print(preant)
        print(ant)
        for j in range(3, n + 1, 1):
            sig = ant + preant
            print(sig)
            preant = ant
            ant = sig