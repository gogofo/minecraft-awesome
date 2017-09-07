def get_mod_version(java_file_path):
    f = open(java_file_path, "r+")

    cur_version = None

    for l in f:
        if l.find("public final static String VERSION") >= 0:
            cur_version = l.split('"')[1]

    if cur_version is None:
        raise Exception("Unable to find version")

    return cur_version