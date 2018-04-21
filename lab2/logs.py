from lab2.ln import ln as ln_impl



class Logs:

	def log_base_10(self, x):
	    return self.base_ln(x) / self.base_ln(10)


	def log_base_5(self, x):
	    return self.base_ln(x) / self.base_ln(5)


	def ln(self, x):
	    return self.base_ln(x)

	def base_ln(self, x):
		return ln_impl(x)