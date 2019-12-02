#!/usr/bin/env python

import argparse
import sys

def main(args):
    with open(args.input) as f:
        for line in f:
            print(line.strip())

if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument("--input", dest="input", help="input data")
    args = parser.parse_args()
    main(args)
