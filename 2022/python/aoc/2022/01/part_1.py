#!/usr/bin/env python

import argparse
import json
import sys


def parse_input(input_handle):
    """
    """
    input = []
    for row in input_handle:
        input.append(row.strip())

    return input


def group_calories_by_elf(calories):
    """
    """
    current_elf_num = 0
    current_elf_calories = []
    calories_by_elf = {}
    for calorie_count in calories:
        if calorie_count != '':
            current_elf_calories.append(int(calorie_count))
        else:
            calories_by_elf[current_elf_num] = current_elf_calories
            current_elf_num += 1
            current_elf_calories = []

    if calories[:-1] != '':
        calories_by_elf[current_elf_num] = current_elf_calories

    return calories_by_elf


def sum_calories(calories_by_elf):
    """
    """
    summed_calories_by_elf = {}
    for elf_num, calories in calories_by_elf.items():
        summed_calories_by_elf[elf_num] = sum(calories)

    return summed_calories_by_elf


def main(args):

    input = parse_input(sys.stdin)
    calories_by_elf = group_calories_by_elf(input)
    summed_calories_by_elf = sum_calories(calories_by_elf)

    print(max(summed_calories_by_elf.values()))


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    args = parser.parse_args()
    main(args)
