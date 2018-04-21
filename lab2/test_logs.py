import unittest
import sys
import numpy as np

from lab2.logs import Logs

class TestLn(unittest.TestCase):

    def test_log_base5_zero(self):
        self.assertRaises(ValueError, Logs().ln, 0)

    def test_log_base5_negative(self):
        self.assertRaises(ValueError, Logs().ln, -1)

    def test_log_base(self):
        self.assertAlmostEqual(Logs().ln(0.1), np.log(0.1), 3)

    def test_log_out_of_default_range(self):
        ''' this case checks invariant of the formula '''
        self.assertAlmostEqual(Logs().ln(10), np.log(10), 3)


class TestLogBase5(unittest.TestCase):

    def test_log_base5_zero(self):
        with self.assertRaises(ValueError):
            Logs().log_base_5(0)

    def test_log_base5_negative_result(self):
        self.assertAlmostEqual(Logs().log_base_5(0.2), -1, 3)

    def test_log_base5_at_one(self):
        self.assertEqual(Logs().log_base_5(1), 0)

    def test_log_base5_at_two(self):
        self.assertAlmostEqual(Logs().log_base_5(2.1), 0.460992, 3)


class TestLogBase10(unittest.TestCase):

    def test_log_base10_zero(self):
        self.assertRaises(ValueError, Logs().log_base_10, 0)

    def test_log_base10_negative_result(self):
        self.assertAlmostEqual(Logs().log_base_10(0.3), -0.522879, 3)

    def test_log_base10_at_one(self):
        self.assertEqual(Logs().log_base_10(1), 0)

    def test_log_base10_at_two(self):
        self.assertAlmostEqual(Logs().log_base_10(2.3), 0.361727, 3)


if __name__ == '__main__':
    unittest.main()
