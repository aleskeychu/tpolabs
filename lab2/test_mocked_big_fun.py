import unittest
from lab2.main import BigFunction
from cmath import pi, sqrt, sin

def sin_mock(x):
    if x == 0:
        return 0
    elif x == -pi/6:
        return -1/2
    elif x == -pi/2:
        return -1
    elif x == -2*pi/3:
        return -sqrt(3) / 2
    elif x == -pi:
        return 0
    elif x == -7*pi/6:
        return 1/2
    elif x == -3*pi/2:
        return 1
    elif x == -5*pi/3:
        return sqrt(3) / 2
    elif x == -2*pi:
        return 0
    elif x == -13*pi/6:
        return -1/2
    else:
        raise ValueError

def log5_mock(x):
    if x == 0.05:
        return -1.8613531161467862
    elif x == 0.09:
        return -1.4961407271748157
    elif x == 1:
        return 0
    elif x == 20:
        return 1.8613531161467862
    else:
        raise ValueError

def log10_mock(x):
    if x == 0.05:
        return -1.301029995663981
    elif x == 0.09:
        return -1.0457574905606752
    elif x == 1:
        return 0
    elif x == 20:
        return 1.301029995663981
    else:
        raise ValueError

def ln_mock(x):
    if x == 0.05:
        return -2.995732273553991
    elif x == 0.09:
        return -2.4079456086518722
    elif x == 1:
        return 0
    elif x == 20:
        return 2.995732273553991
    else:
        raise ValueError

class TestBigFun(unittest.TestCase):

    def setUp(self):
        f = BigFunction()
        f.log_base_5 = log5_mock
        f.log_base_10 = log10_mock
        f.ln = ln_mock
        f.sin = sin_mock
        self.f = f

    def test_down(self):
        self.assertAlmostEqual(self.f.calculate(0.05), 310.888, 3)

    def test_curve(self):
        self.assertAlmostEqual(self.f.calculate(0.09), 6.098, 3)

    def test_zero(self):
        with self.assertRaises(ZeroDivisionError):
            self.f.calculate(1)

    def test_up(self):
        self.assertAlmostEqual(self.f.calculate(20), 310.8878, 3)


    def test_sin1(self):
        self.assertEqual(self.f.calculate(0), sin(0))

    def test_sin2(self):
        self.assertAlmostEqual(self.f.calculate(-pi/6), sin(-pi/6), 3)

    def test_sin3(self):
        self.assertAlmostEqual(self.f.calculate(-pi/2), sin(-pi/2), 3)

    def test_sin5(self):
        self.assertAlmostEqual(self.f.calculate(-2*pi/3), sin(-2*pi/3), 3)

    def test_sin6(self):
        self.assertAlmostEqual(self.f.calculate(-pi), sin(-pi), 3)

    def test_sin7(self):
        self.assertAlmostEqual(self.f.calculate(-7*pi/6), sin(-7*pi/6), 3)

    def test_sin8(self):
        self.assertAlmostEqual(self.f.calculate(-3 * pi / 2), sin(-3 * pi / 2), 3)

    def test_sin9(self):
        self.assertAlmostEqual(self.f.calculate(-5 * pi / 3), sin(-5 * pi / 3), 3)

    def test_sin10(self):
        self.assertAlmostEqual(self.f.calculate(-2 * pi), sin(-2 * pi), 3)

    def test_sin10(self):
        self.assertAlmostEqual(self.f.calculate(-13 * pi / 6), sin(-13 * pi / 6), 3)
if __name__ == '__main__':
    unittest.main()