JAVA_FILE_PATH = "../src/main/java/gogofo/minecraft/awesome/AwesomeMod.java"
GRADLE_FILE_PATH = "../build.gradle"

def replace_in_file(path, original, new):
    f = open(path, "r+")
    content = "".join([l for l in f])
    f.close()

    content = content.replace(original, new)

    f = open(path, "w+")
    f.write(content)
    f.close()

if __name__ == "__main__":
    f = open(JAVA_FILE_PATH, "r+")

    for l in f:
        if l.find("public final static String VERSION") >= 0:
            cur_version = l.split('"')[1]

    major, minor = [int(x) for x in cur_version.split(".")]
    minor += 1

    new_version = str(major) + "." + str(minor)

    replace_in_file(JAVA_FILE_PATH, cur_version, new_version)
    replace_in_file(GRADLE_FILE_PATH, cur_version, new_version)
