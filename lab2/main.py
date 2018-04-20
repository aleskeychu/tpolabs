from sin import sin
from logs import log_base_5, log_base_10, ln


def calculate(x):
    if x <= 0:
        result = sin(x)
    else:
        numerator = pow(pow(log_base_10(x) * log_base_5(x), 2), 2)
        denominator = (ln(x) - log_base_10(x)) * ln(x)

        result = pow(numerator / denominator, 3)

    return result


def main():
    x = -10
    print(calculate(x))


if __name__ == "__main__":
    main()
