from csv import DictWriter


def dump(func, start, end, step):
    results = []
    x = start

    while x < end:
        results.append({'x': x, func.__name__: func(x)})
        x += step

    filename = "-".join([func.__name__, str(start), str(end), str(step)])
    filename += ".csv"

    write2csv(filename, results)


def write2csv(filename, obj):
    with open(filename, "w+") as fp:
        writer = DictWriter(fp, fieldnames=list(obj[0].keys()))
        writer.writeheader()
        for o in obj:
            writer.writerow(o)

