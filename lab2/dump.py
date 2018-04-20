from csv import DictWriter


def get_filename(func, start, end, step):
    ''' Function to get filename of the file function is being dumped to. '''
    filename = "-".join([func.__name__, str(start), str(end), str(step)])
    filename += ".csv"

    return filename


def dump(func, start, end, step):
    ''' Helper for creating stubs for functions
    
    Args:
        func (function) -- function which takes single argument `x`
        start (int)     -- staring value of `x`
        end (int)       -- end value of `x`
        step (float)    -- step
    '''

    results = []
    x = start

    while x < end:
        results.append({'x': x, func.__name__: func(x)})
        x += step

    filename = get_filename(func, start, end, step)

    _write2csv(filename, results)

    return None


def _write2csv(filename, obj):
    with open(filename, "w+") as fp:
        writer = DictWriter(fp, fieldnames=list(obj[0].keys()))
        writer.writeheader()
        for o in obj:
            writer.writerow(o)

