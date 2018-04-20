from decimal import Decimal
from cmath import pi, inf
import math

import scipy as sp

def sin(x):
    # choose the closest anchor point to the `x`
    x_in_period = abs(x) % pi

    if x_in_period > (7 * pi / 8):
        x0 = pi
    elif x_in_period > (5 * pi / 8):
        x0 = 3 * pi / 4
    elif x_in_period > (3 * pi / 8):
        x0 = pi / 2
    elif x_in_period > pi / 8:
        x0 = pi / 4
    else:
        x0 = 0

    sin_at_x0 = _sin_lookup(x0)
    cos_at_x0 = _cos_lookup(x0)

    sin_part = 0
    for n in range(7):
        numerator = Decimal(((-1)**n * sin_at_x0 * pow(x_in_period - x0, 2*n)))
        denumerator = Decimal(sp.math.factorial(2*n))

        sin_part += numerator / denumerator

    cos_part = 0
    for n in range(7):
        numerator = Decimal((-1)**n * cos_at_x0 * pow(x_in_period - x0, 2*n + 1))
        denumerator = Decimal(sp.math.factorial(2*n + 1))

        cos_part += numerator / denumerator

    # if the number is negative change the sign of the answer
    sign = -1 if x < 0 else 1
    # but x mod PI is not even -- change the sign again
    if (x // pi) % 2 != 0:
        sign = 1 if sign == -1 else -1

    return sign * float(sin_part + cos_part)


def _sin_lookup(x):
    if x == pi:
        return 0
    elif x == 3 * pi / 4:
        return 0.7071067811865476
    elif x == pi / 2:
        return 1
    elif x == pi / 4:
        return 0.7071067811865476
    elif x == 0:
        return 0
    else:
        raise ValueError("invalid x {}".format(x))

def _cos_lookup(x):
    if x == pi:
        return -1
    elif x == 3 * pi / 4:
        return -0.7071067811865476
    elif x == pi / 2:
        return 0
    elif x == pi / 4:
        return 0.7071067811865476
    elif x == 0:
        return 1
    else:
        raise ValueError("invalid x {}".format(x))

