    preant = 1
    ant = 2
    if (n == 1):
        print(preant)
    if (n == 2):
        print(ant)
    if (n >= 3):
        for j in range(3, n + 1, 1):
            sig = ant + preant
            print(sig)
            preant = ant
            ant = sig