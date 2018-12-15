import csv
import random

def randomize(outfile):
    with open("allarticles.csv", 'r') as f:
        r = csv.reader(f)
        header, l = next(r), list(r)

    rows = [x for x in l]
    random.shuffle(rows)

    with open(outfile, "wb") as f:
        print(header)
        csv.writer(f).writerow([item for item in header])
        for row in rows:
            csv.writer(f).writerow(row)


for i in range(1,10):
    randomize("randomorder_" + str(i) + ".csv")
