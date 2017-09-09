from utils import *
import os
import config

JAVA_FILE_PATH = "../src/main/java/gogofo/minecraft/awesome/AwesomeMod.java"

if __name__ == "__main__":
    cur_version = get_mod_version(JAVA_FILE_PATH)

    changelog = ""
    f = open("../changelog.txt")
    changelog_version = f.readline()
    if changelog_version.strip() == cur_version.strip():
        f.readline() # skip "------" line
        for l in f:
            if l.find("-----") >= 0:
                break
            changelog += l
    f.close()

    changelog = changelog.replace("\n", "\\n")
    os.system("java -jar KSPCurse.jar --game minecraft --key {key} --mod 236166 --version 1.12,1.12.1 --changelog \"{changelog}\" ../build/libs/awesome-{version}.jar".format(key=config.key, changelog=changelog, version=cur_version))