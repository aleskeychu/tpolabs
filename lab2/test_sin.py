import unittest
import sys
from math import sin as std_sin

import sin

class TestSin(unittest.TestCase):

    n_digits_accurary = 4

    def test_zero(self):
        self.assertAlmostEqual(sin.sin(0), 0, 4)

    def test_close_to_anchor_point(self):
        # PI / 4 = 0.78 -- anchor point
        self.assertAlmostEqual(sin.sin(0.8), std_sin(0.8), 4)

    def test_x_out_of_period(self):
        self.assertAlmostEqual(sin.sin(1000), std_sin(1000), 4)

    def test_negative(self):
        self.assertAlmostEqual(sin.sin(20), std_sin(20), 4)

    def test_many_negatives(self):
        for x in range(-1, -10, -1):
            self.assertAlmostEqual(sin.sin(x), std_sin(x), 4)


if __name__ == '__main__':
    unittest.main()
