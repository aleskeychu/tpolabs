from lab2.sin import sin
from lab2.ln import ln
from lab2.logs import Logs
from lab2.main import BigFunction
from lab2.dump import dump


if __name__ == '__main__':
    dump(sin, -1, 1, 0.1)
    dump(Logs().ln, 0.1, 1, 0.1)
    dump(Logs().log_base_5, 0.1, 1, 0.1)
    dump(Logs().log_base_10, 0.1, 1, 0.1)

    dump(BigFunction().calculate, -1, 1, 0.1)
    dump(ln, 0.1, 1, 0.1)
