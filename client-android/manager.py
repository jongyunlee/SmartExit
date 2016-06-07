#!/usr/local/bin/python3
import sys
import configparser
from android_generator.android_generator import AndroidGenerator

config = configparser.ConfigParser()
config.read('config.ini')

default_config = config['DEFAULT']
project = default_config['PROJECT_NAME']
package = default_config['PACKAGE_NAME']
json_dir = default_config['JSON_DIR']
server = default_config['TEMPLATES_DIR']
sender_id = default_config['GCM_SENDER_ID']
ag = AndroidGenerator(project, package, json_dir, server, sender_id)

def generate():
    ag.generate_project()

def bulk():
    ag.bulk_generate()

def add():
    ag.add_action(sys.argv[2])

def delete():
    ag.delete_action(sys.argv[2])

def list():
    ag.list_actions()

def globalize():
    ag.globalize()

def compile():
    ag.compile()

if __name__ == "__main__":
    globals()[sys.argv[1]]()
