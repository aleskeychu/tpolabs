from ln import ln as ln_impl


def log_base_10(x):
    return ln(x) / ln(10)


def log_base_5(x):
    return ln(x) / ln(5)


def ln(x):
    return ln_impl(x)

