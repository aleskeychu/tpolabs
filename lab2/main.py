from lab2.sin import sin
from lab2.logs import Logs


class BigFunction:

    def sin(self, x):
        return sin(x)

    def log_base_5(self, x):
        return Logs().log_base_5(x)

    def log_base_10(self, x):
        return Logs().log_base_10(x)

    def ln(self, x):
        return Logs().ln(x)

    def calculate(self, x):
        if x <= 0:
            result = self.sin(x)
        else:
            numerator = pow(pow(self.log_base_10(x) * self.log_base_5(x), 2), 2)
            denominator = (self.ln(x) - self.log_base_10(x)) * self.ln(x)

            result = pow(numerator / denominator, 3)

        return result
