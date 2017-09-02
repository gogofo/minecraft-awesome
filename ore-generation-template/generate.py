import os
from sys import argv
from shutil import copy

LANG_FILE_PATH = "../src/main/resources/assets/awesome/lang/en_US.lang"

def camel_name(name):
    return name.strip().lower().replace(" ", "_")

def edit_lang(ore_name):
    f = open(LANG_FILE_PATH, "r+")
    lang = ""
    for l in f:
        lang += l
    f.close()

    lang_add = """tile.template_ore.name=Template Ore
item.template_ingot.name=Template Ingot
item.template_dust.name=Template Dust""".replace("template", camel_name(ore_name)).replace("Template", ore_name.title())
    lang = lang.replace("## ORE_ADD_MARKER", lang_add + "\n\n## ORE_ADD_MAKER")

    f = open(LANG_FILE_PATH, "w+")
    f.write(lang)
    f.close()


def copy_json(ore_name):
    ore_name = camel_name(ore_name)
    files = os.walk("./ore-template")

    for base_path,directories,files in files:
        for d in directories:
            d_path = os.path.join(base_path, d).replace("./ore-template", "..").replace("template", ore_name)
            
            if not os.path.exists(d_path):
                os.makedirs(d_path)
                
        for f in files:
            of_path = os.path.join(base_path, f)
            f_path = of_path.replace("./ore-template", "..").replace("template", ore_name)

            if not os.path.exists(f_path):
                copy(of_path, f_path)

if __name__ == "__main__":
    ore_name = argv[1]

    copy_json(ore_name)
    edit_lang(ore_name)
