import urllib.request
import xml.etree.ElementTree as ET

url = "https://maven.fabricmc.net/net/fabricmc/fabric-api/fabric-api/maven-metadata.xml"
response = urllib.request.urlopen(url)
xml_data = response.read()

root = ET.fromstring(xml_data)
versions = root.findall(".//version")

latest_version = None
for version in versions:
    if "+1.21.1" in version.text:
        latest_version = version.text

print(latest_version)
