import math
from decimal import Decimal


def ln(x):
    max_iterations = 2500
    if x <= 0:
        raise ValueError("There is no log for negative number")

    result = 0
    if abs(x - 1) < 1:
        for n in range(1, max_iterations):
            numerator = Decimal(pow(-1, n) * pow(x-1, n))

            result += float(numerator / n)

        result = -result

    else:
        sum_ = 0
        for n in range(1, max_iterations):
            sum_ += float(Decimal(pow(-1, n)) / Decimal(n * pow(x - 1, n)))
        result = ln(x - 1) - sum_

    return result
