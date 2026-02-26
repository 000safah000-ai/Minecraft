import urllib.request
import xml.etree.ElementTree as ET

url = 'https://maven.ladysnake.org/releases/org/ladysnake/satin/maven-metadata.xml'
req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
with urllib.request.urlopen(req) as response:
    xml_data = response.read()

root = ET.fromstring(xml_data)
latest = root.find('./versioning/latest')
if latest is not None:
    print(latest.text)
else:
    versions = root.findall('./versioning/versions/version')
    if versions:
        print(versions[-1].text)
