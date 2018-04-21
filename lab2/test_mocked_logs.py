import unittest
import sys
import numpy as np

from lab2.logs import Logs

def mock(x):
    if x == 0:
        raise ValueError
    elif x == -1:
        raise ValueError
    elif x == 1:
        return 0
    elif x == 0.1:
        return -2.3025850929940455
    elif x == 0.2:
        return -1.6094379124341003
    elif x == 10:
        return 2.3025850929940455
    elif x == 5:
        return 1.6094379124341003
    elif x == 2.3:
        return 0.83290912293510388
    elif x == 2.1:
        return 0.74193734472937733
    elif x == 0.3:
        return -1.2039728043259361
    else:
        raise ValueError

class TestLn(unittest.TestCase):

    def setUp(self):
        self.l = Logs()
        self.l.base_ln = mock

    def test_log_base5_zero(self):        
        self.assertRaises(ValueError, self.l.ln, 0)

    def test_log_base5_negative(self):
        self.assertRaises(ValueError, self.l.ln, -1)

    def test_log_base(self):
        self.assertAlmostEqual(self.l.ln(0.1), np.log(0.1), 3)

    def test_log_out_of_default_range(self):
        ''' this case checks invariant of the formula '''
        self.assertAlmostEqual(self.l.ln(10), np.log(10), 3)


class TestLogBase5(unittest.TestCase):

    def setUp(self):
            self.l = Logs()
            self.l.base_ln = mock

    def test_log_base5_zero(self):
        self.assertRaises(ValueError, self.l.log_base_5, 0)

    def test_log_base5_negative_result(self):
        self.assertAlmostEqual(self.l.log_base_5(0.2), -1, 3)

    def test_log_base5_at_one(self):
        self.assertEqual(self.l.log_base_5(1), 0)

    def test_log_base5_at_two(self):
        self.assertAlmostEqual(self.l.log_base_5(2.1), 0.460992, 3)


class TestLogBase10(unittest.TestCase):

    def setUp(self):
        self.l = Logs()
        self.l.base_ln = mock

    def test_log_base10_zero(self):
        self.assertRaises(ValueError, self.l.log_base_10, 0)

    def test_log_base10_negative_result(self):
        self.assertAlmostEqual(self.l.log_base_10(0.3), -0.522879, 3)

    def test_log_base10_at_one(self):
        self.assertEqual(self.l.log_base_10(1), 0)

    def test_log_base10_at_two(self):
        self.assertAlmostEqual(self.l.log_base_10(2.3), 0.361727, 3)


if __name__ == '__main__':
    unittest.main()
