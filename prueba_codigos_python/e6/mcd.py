a = int(input())
b = int(input())

if (a < b):
    temp = a
    a = b
    b = temp

q1 = a / b
r1 = a % b
if (r1 != 0):
    q2 = b / r1
    r2 = b % r1
    if (r2 != 0):
        q = r1 / r2
        r3 = r1 % r2
        while (r3 != 0):
            r1 = r2
            r2 = r3
            q = r1 / r2
            r3 = r1 % r2
        print(r2)
    else:
        print(r1)
else:
    print(b)