import unittest
import sys
import numpy as np

from logs import ln, log_base_5, log_base_10

class TestLn(unittest.TestCase):

    def test_log_base5_zero(self):
        self.assertRaises(ValueError, ln, 0)

    def test_log_base5_negative(self):
        self.assertRaises(ValueError, ln, -1)

    def test_log_base(self):
        self.assertAlmostEqual(ln(0.1), np.log(0.1), 3)

    def test_log_out_of_default_range(self):
        ''' this case checks invariant of the formula '''
        self.assertAlmostEqual(ln(10), np.log(10), 3)

    def test_log_base_5(self):
        self.assertAlmostEqual(log_base_5(10), 1.4306, 3)

    def test_log_base_10(self):
        self.assertAlmostEqual(log_base_10(30), 1.4771, 3)

if __name__ == '__main__':
    unittest.main()
