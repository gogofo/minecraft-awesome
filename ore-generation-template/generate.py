import os
from sys import argv
from shutil import copy

if __name__ == "__main__":
    ore_name = argv[1]

    files = os.walk("./ore-template")

    for base_path,directories,files in files:
        for d in directories:
            d_path = os.path.join(base_path, d).replace("template", ore_name)
            
            if not os.path.exists(d_path):
                os.makedirs(d_path)
                
        for f in files:
            of_path = os.path.join(base_path, f)
            f_path = of_path.replace("template", ore_name)

            if not os.path.exists(f_path):
                copy(of_path, f_path)
