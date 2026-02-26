import urllib.request
import xml.etree.ElementTree as ET

url = "https://maven.blamejared.com/foundry/veil/veil-fabric-1.21.1/maven-metadata.xml"
req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
with urllib.request.urlopen(req) as response:
    xml_data = response.read()

root = ET.fromstring(xml_data)
versions = []
for version in root.findall(".//version"):
    versions.append(version.text)

for v in versions:
    print(v)
