import urllib.request
import xml.etree.ElementTree as ET

url = 'https://maven.ladysnake.org/releases/org/ladysnake/satin/maven-metadata.xml'
req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
try:
    with urllib.request.urlopen(req) as response:
        xml_data = response.read()

    root = ET.fromstring(xml_data)
    versions = [version.text for version in root.findall('.//version')]
    for v in versions:
        print(v)
except Exception as e:
    print(f"Error: {e}")
