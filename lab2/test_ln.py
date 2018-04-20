import unittest
import sys
import numpy as np

import ln

class TestLogs(unittest.TestCase):

    def test_log_base5_zero(self):
        self.assertRaises(ValueError, ln.ln, 0)

    def test_log_base5_negative(self):
        self.assertRaises(ValueError, ln.ln, -1)

    def test_log_base(self):
        self.assertAlmostEqual(ln.ln(0.1), np.log(0.1), 3)

    def test_log_base_positive_limit(self):
        self.assertAlmostEqual(ln.ln(10), np.log(10), 3)

if __name__ == '__main__':
    unittest.main()
