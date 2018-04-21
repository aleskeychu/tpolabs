import unittest
from lab2.main import BigFunction
from cmath import pi, sqrt, sin

class TestBigFun(unittest.TestCase):

    def setUp(self):
        self.f = BigFunction()

    def test_down(self):
        self.assertAlmostEqual(self.f.calculate(0.05), 310.888, 0)

    def test_curve(self):
        self.assertAlmostEqual(self.f.calculate(0.09), 6.098, 0)

    def test_zero(self):
        with self.assertRaises(ZeroDivisionError):
            self.f.calculate(1)

    def test_up(self):
        self.assertAlmostEqual(self.f.calculate(20), 310.8878, 0)


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