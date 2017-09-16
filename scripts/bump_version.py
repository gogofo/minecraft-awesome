from utils import *

JAVA_FILE_PATH = "../src/main/java/gogofo/minecraft/awesome/AwesomeMod.java"
GRADLE_FILE_PATH = "../build.gradle"
CHANGELOG_FILE_PATH = "../changelog.txt"

def replace_in_file(path, original, new):
    f = open(path, "r+")
    content = "".join([l for l in f])
    f.close()

    content = content.replace(original, new)

    f = open(path, "w+")
    f.write(content)
    f.close()

def update_changelog(new_version):
    f = open(CHANGELOG_FILE_PATH, "r+")
    content = "".join([l for l in f])
    f.close()

    f = open(CHANGELOG_FILE_PATH, "w+")
    f.write(new_version + """
-------------------


-------------------\n""" + content)
    f.close()

if __name__ == "__main__":
    cur_version = get_mod_version(JAVA_FILE_PATH)

    major, minor = [int(x) for x in cur_version.split(".")]
    minor += 1

    new_version = str(major) + "." + str(minor)
    next_version = str(major) + "." + str(minor+1)

    replace_in_file(JAVA_FILE_PATH, cur_version, new_version)
    replace_in_file(GRADLE_FILE_PATH, cur_version, new_version)

    update_changelog(next_version)
