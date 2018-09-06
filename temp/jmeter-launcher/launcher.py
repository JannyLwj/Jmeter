import datetime
import os

# Product Env
import subprocess
import sys
import zipfile

from xlrd import open_workbook

# Env parameters
URL_ENV = 'url'
URL = 'http://127.0.0.1'

CONN_TIMEOUT_ENV = 'conn_timeout'
CONN_TIMEOUT = 0

READ_TIMEOUT_ENV = 'read_timeout'
READ_TIMEOUT = 0

GOLDEN_FILE_IN_ARTI_ENV = 'golden_file_url'
GOLDEN_FILE_IN_ARTI = 'http://artifactory/temp.zip'

RESULT_IN_ARTI_ENV = 'result_file_url'
RESULT_IN_ARTI = 'http://artifactory/result/'

ARTI_AUTH_ENV = 'artifactory_auth'
ARTI_AUTH = 'chengdu_qa:c?F&-=aPjUSN#4F$'

# Gitlab parameters
GIT_IP_ENV = 'git_ip'
GIT_IP = '10.33.56.44'

GIT_REPO_ENV = 'git_repo'
GIT_REPO = 'git@git.labs.nuance.com:mobility-ncs-tts-components/tts-cdqa-tool.git'

GIT_BRANCH_ENV = 'git_branch'
GIT_BRANCH = 'master'

TEST_CASE_SHEET_ENV = 'test_case_sheet'
TEST_CASE_SHEET = 'jobs'

GIT_TEST_CASE_FILE_ENV = 'git_test_case_file'
GIT_TEST_CASE_FILE = '/script/JMeter/testcases/ttssystem/ttssystem.xlsx'

GIT_JMETER_PORJECT_FOLDER_ENV = 'git_jmeter_project_folder'
GIT_JMETER_PORJECT_FOLDER = '/script/JMeter/testcases/ttssystem/'

GIT_TEST_DATA_FOLDER_ENV = 'git_test_data_folder'
GIT_TEST_DATA_FOLDER = '/script/JMeter/testcases/ttssystem/testData'

# Docker container path
GOLDEN_FOLDER_ENV = 'golden_folder'
GOLDEN_FOLDER = '/workspace/golden/'

GIT_PATH_ENV = 'git_path'
GIT_PATH = '/workspace/git'

RESULT_FOLDER_ENV = 'result_folder'
RESULT_FOLDER = '/workspace/result/'

JMETER_BIN_ENV = 'jmeter_home'
JMETER_BIN = 'java -jar /jmeter/bin/ApacheJMeter.jar -n -t'


class ExcelData:
    def __init__(self):
        self.order = 0
        self.plan = ''
        self.case_data = ''
        self.thread_number = 0
        self.loadduration = 0
        self.loopcount = 0
        self.recreate = False
        self.result_file = ''
        self.log_file = ''

    def is_vaild(self):
        return self.order > 0 and self.plan != '' and self.case_data != '' and self.thread_number > 0 and self.recreate

    def get_paramerter(self, jmeter_project_folder, data_folder, result_folder):
        output_file = "No_" + str(self.order) + "_ThreadNum_" + str(self.thread_number) + "_LoopCount_" + str(
            self.loopcount)

        para = " " + jmeter_project_folder + self.plan + " -JtestDataFile " + data_folder + self.case_data + " -JthreadNum " + str(
            self.thread_number) + " -JthreadLoopCount " + str(self.loopcount)
        if self.loadduration > 0:
            para = para + " -JloadDuration " + str(self.loadduration)

        self.result_file = result_folder + output_file + ".csv"
        self.log_file = result_folder + output_file + ".log"

        para = para + " -JresultFile " + self.result_file + " -l " + self.log_file
        return para

    def get_result_file(self):
        return self.result_file

    def get_log_file(self):
        return self.log_file


def run_command(cmd):
    print(cmd)
    f = open(os.devnull, 'w')
    ret = subprocess.call(cmd, shell=True, stdout=f, stderr=subprocess.STDOUT)
    f.close()
    if ret != 0:
        print("%s error" % cmd)
        sys.exit(1)


def read_env():
    if os.environ.get(GIT_IP_ENV):
        global GIT_IP
        GIT_IP = os.environ.get(GIT_IP_ENV)

    if os.environ.get(GIT_REPO_ENV):
        global GIT_REPO
        GIT_REPO = os.environ.get(GIT_REPO_ENV)

    if os.environ.get(RESULT_IN_ARTI_ENV):
        global RESULT_IN_ARTI
        RESULT_IN_ARTI = os.environ.get(RESULT_IN_ARTI_ENV)

    if os.environ.get(ARTI_AUTH_ENV):
        global ARTI_AUTH
        ARTI_AUTH = os.environ.get(ARTI_AUTH_ENV)

    if os.environ.get(GOLDEN_FILE_IN_ARTI_ENV):
        global GOLDEN_FILE_IN_ARTI
        GOLDEN_FILE_IN_ARTI = os.environ.get(GOLDEN_FILE_IN_ARTI_ENV)

    if os.environ.get(GIT_BRANCH_ENV):
        global GIT_BRANCH
        GIT_BRANCH = os.environ.get(GIT_BRANCH_ENV)

    if os.environ.get(GIT_PATH_ENV):
        global GIT_PATH
        GIT_PATH = os.environ.get(GIT_PATH_ENV)

    if os.environ.get(GIT_TEST_CASE_FILE_ENV):
        global GIT_TEST_CASE_FILE
        GIT_TEST_CASE_FILE = os.environ.get(GIT_TEST_CASE_FILE_ENV)

    if os.environ.get(TEST_CASE_SHEET_ENV):
        global TEST_CASE_SHEET
        TEST_CASE_SHEET = os.environ.get(TEST_CASE_SHEET_ENV)

    if os.environ.get(GIT_JMETER_PORJECT_FOLDER_ENV):
        global GIT_JMETER_PORJECT_FOLDER
        GIT_JMETER_PORJECT_FOLDER = os.environ.get(GIT_JMETER_PORJECT_FOLDER_ENV)

    if os.environ.get(GIT_TEST_DATA_FOLDER_ENV):
        global GIT_TEST_DATA_FOLDER
        GIT_TEST_DATA_FOLDER = os.environ.get(GIT_TEST_DATA_FOLDER_ENV)

    if os.environ.get(URL_ENV):
        global URL
        URL = os.environ.get(URL_ENV)

    if os.environ.get(GOLDEN_FOLDER_ENV):
        global GOLDEN_FOLDER
        GOLDEN_FOLDER = os.environ.get(GOLDEN_FOLDER_ENV)

    if os.environ.get(CONN_TIMEOUT_ENV):
        global CONN_TIMEOUT
        CONN_TIMEOUT = os.environ.get(CONN_TIMEOUT_ENV)

    if os.environ.get(READ_TIMEOUT_ENV):
        global READ_TIMEOUT
        READ_TIMEOUT = os.environ.get(READ_TIMEOUT_ENV)

    if os.environ.get(JMETER_BIN_ENV):
        global JMETER_BIN
        JMETER_BIN = os.environ.get(JMETER_BIN_ENV)

    if os.environ.get(RESULT_FOLDER_ENV):
        global RESULT_FOLDER
        RESULT_FOLDER = os.environ.get(RESULT_FOLDER_ENV)


def download_git_files():
    run_command('echo "$' + GIT_IP + ' git.labs.nuance.com" >> /etc/hosts')
    # run_command('ssh -o StrictHostKeyChecking=no git@git.labs.nuance.com')
    # run_command('set -e')
    git_command = 'git clone  -q --single-branch -b ' + GIT_BRANCH + ' ' + GIT_REPO + ' ' + GIT_PATH
    run_command(git_command)
    if os.path.isfile(GIT_PATH + GIT_TEST_CASE_FILE):
        print("The Test Case Excel is " + GIT_PATH + GIT_TEST_CASE_FILE)
    else:
        print("Can't get the Test Case Excel from git")
        sys.exit(1)


def unzip(file, dest):
    f = zipfile.ZipFile(file, 'r')
    for file in f.namelist():
        f.extract(file, dest)


def download_golden_from_aritifactory():
    run_command(
        'curl -u "' + ARTI_AUTH + '" -k ' + GOLDEN_FILE_IN_ARTI + ' -o ' + GOLDEN_FOLDER + 'tmp.zip' + ' --progress')

    try:
        unzip(GOLDEN_FOLDER + 'tmp.zip', GOLDEN_FOLDER)
    except:
        print("File is not a zip file")

    if os.path.exists(GOLDEN_FOLDER + 'tmp.zip'):
        os.remove(GOLDEN_FOLDER + 'tmp.zip')


def upload_result_log_to_artifacotry(timestamp, file):
    if not file or not timestamp:
        print("File or timestamp is null")
        return

    if os.path.isfile(file):
        run_command('curl -u "' + ARTI_AUTH + '" -X PUT ' + RESULT_IN_ARTI + timestamp + "/" + " -T " + file)
    else:
        print(file + " is not exists")


def read_test_case():
    wb = open_workbook(GIT_PATH + GIT_TEST_CASE_FILE)
    find_sheet = False
    test_data = []
    for sheet in wb.sheets():
        print('find sheet ' + sheet.name)
        if sheet.name == TEST_CASE_SHEET:
            find_sheet = True
            number_of_rows = sheet.nrows
            for row in range(1, number_of_rows):
                data = ExcelData()
                data.order = int(sheet.cell(row, 0).value)
                data.plan = sheet.cell(row, 1).value
                data.case_data = sheet.cell(row, 2).value
                data.thread_number = int(sheet.cell(row, 3).value)
                data.loadduration = int(sheet.cell(row, 4).value)
                data.loopcount = int(sheet.cell(row, 5).value)
                data.recreate = bool(sheet.cell(row, 6).value)

                if data.is_vaild():
                    test_data.append(data)

    if not find_sheet:
        print("Can't get find the " + TEST_CASE_SHEET)
        sys.exit(1)

    print("Find valid case number is " + str(len(test_data)))
    return test_data


if __name__ == '__main__':
    print('Start')
    timestamp = '{0:%Y-%m-%d_%H:%M:%S}'.format(datetime.datetime.now())
    print(timestamp)
    os.makedirs(GIT_PATH, exist_ok=True)
    os.makedirs(RESULT_FOLDER, exist_ok=True)
    os.makedirs(GOLDEN_FOLDER, exist_ok=True)

    read_env()
    download_git_files()
    download_golden_from_aritifactory()
    test_data = read_test_case()

    env_parameters = " -Jurl " + URL + " -JgoldenFolder " + GOLDEN_FOLDER + " -JconnectionTimeout " + str(
        CONN_TIMEOUT) + " -JreadTimeout " + str(READ_TIMEOUT)
    for data in test_data:
        command = JMETER_BIN + data.get_paramerter(GIT_PATH + GIT_JMETER_PORJECT_FOLDER,
                                                   GIT_PATH + GIT_TEST_DATA_FOLDER, RESULT_FOLDER)
        command = command + env_parameters
        run_command(command)
        upload_result_log_to_artifacotry(timestamp,data.get_result_file())
        upload_result_log_to_artifacotry(timestamp,data.get_log_file())

    print("End")
