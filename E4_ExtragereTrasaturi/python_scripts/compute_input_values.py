import sys
import os.path
import statistics

current_dir = os.path.dirname(__file__)


def file_lines_generator(filename):
    with open(filename, "r") as f:
        for line in f:
            yield line


def get_float(string):
    try:
        return float(string)
    except ValueError:
        return None


def remove_null(list):
    return [x for x in list if x is not None]

all_data = {}
DEFAULT_DICT_VAL = {
                "f1_mean": None,
                "f2_mean": None,
                "f3_mean": None,
                "f4_mean": None,
                "f1_stdev": None,
                "f2_stdev": None,
                "f3_stdev": None,
                "f4_stdev": None,
                "pitch_mean": None,
                "pitch_stdev": None,
                "mfcc_1_mean": None,
                "mfcc_2_mean": None,
                "mfcc_3_mean": None,
                "mfcc_4_mean": None,
                "mfcc_5_mean": None,
                "mfcc_6_mean": None,
                "mfcc_7_mean": None,
                "mfcc_8_mean": None,
                "mfcc_9_mean": None,
                "mfcc_10_mean": None,
                "mfcc_11_mean": None,
                "mfcc_12_mean": None,
                "mfcc_1_stdev": None,
                "mfcc_2_stdev": None,
                "mfcc_3_stdev": None,
                "mfcc_4_stdev": None,
                "mfcc_5_stdev": None,
                "mfcc_6_stdev": None,
                "mfcc_7_stdev": None,
                "mfcc_8_stdev": None,
                "mfcc_9_stdev": None,
                "mfcc_10_stdev": None,
                "mfcc_11_stdev": None,
                "mfcc_12_stdev": None,
            }


def compute_formants(formant_file):
    data = {}
    for line in file_lines_generator(formant_file):
        items = line.split()
        if items[0] == 'Filename':
            continue

        name, _, f1, f2, f3, f4 = items
        f1, f2, f3, f4 = get_float(f1), get_float(f2), get_float(f3), get_float(f4)

        if name not in data:
            data[name] = {
                "f1": [], "f2": [], "f3": [], "f4": []
            }

        if f1:
            data[name]["f1"].append(f1)
            if f2:
                data[name]["f2"].append(f2)
            if f3:
                data[name]["f3"].append(f3)
            if f4:
                data[name]["f4"].append(f4)
        data[name]["f1"] = remove_null(data[name]["f1"])
        data[name]["f2"] = remove_null(data[name]["f2"])
        data[name]["f3"] = remove_null(data[name]["f3"])
        data[name]["f4"] = remove_null(data[name]["f4"])

    for key in data:
        if key not in all_data:
            all_data[key] = DEFAULT_DICT_VAL

        all_data[key]["f1_mean"] = statistics.mean(data[key]["f1"])
        all_data[key]["f2_mean"] = statistics.mean(data[key]["f2"])
        all_data[key]["f3_mean"] = statistics.mean(data[key]["f3"])
        all_data[key]["f4_mean"] = statistics.mean(data[key]["f4"])
        all_data[key]["f1_stdev"] = statistics.stdev(data[key]["f1"])
        all_data[key]["f2_stdev"] = statistics.stdev(data[key]["f2"])
        all_data[key]["f3_stdev"] = statistics.stdev(data[key]["f3"])
        all_data[key]["f4_stdev"] = statistics.stdev(data[key]["f4"])

    del data


def compute_pitch(pitch_file):
    data = {}
    for line in file_lines_generator(pitch_file):
        items = line.split()
        if items[0] == 'Filename':
            continue

        name, _, pitch = items
        if name not in data:
            data[name] = []

        pitch = get_float(pitch)
        if pitch:
            data[name].append(pitch)

    for key in data:
        if key not in all_data:
            all_data[key] = DEFAULT_DICT_VAL

        all_data[key]["pitch_mean"] = statistics.mean(data[key])
        all_data[key]["pitch_stdev"] = statistics.stdev(data[key])


def compute_mfcc(mfcc_dir):
    data = {}
    for mfcc_file in os.listdir(mfcc_dir):
        name = mfcc_file.split('.')[0]
        data[name] = {}
        files = file_lines_generator(os.path.join(mfcc_dir, mfcc_file))
        for _ in range(4):
            next(files)

        for i in range(1, 13):
            current_line = next(files)
            data[name][i] = [get_float(x) for x in current_line.split()]
            data[name][i] = remove_null(data[name][i])

        for key in data:
            if key not in all_data:
                all_data[key] = DEFAULT_DICT_VAL

            for index in data[key]:
                all_data[name]["mfcc_{}_mean".format(index)] = statistics.mean(data[key][index])
                all_data[name]["mfcc_{}_stdev".format(index)] = statistics.stdev(data[key][index])


if __name__ == '__main__':
    import argparse
    parser = argparse.ArgumentParser()
    parser.add_argument("--mfcc", action="store", dest="mfcc", default=None)
    parser.add_argument("--pitch", action="store", dest="pitch", default=None)
    parser.add_argument("--formants", action="store", dest="formants", default=None)
    args = parser.parse_args(sys.argv[1:])

    if args.formants:
        compute_formants(args.formants)

    if args.pitch:
        compute_pitch(args.pitch)

    if args.mfcc:
        compute_mfcc(args.mfcc)

    import json
    print(json.dumps(all_data, sort_keys=True, indent=4))
