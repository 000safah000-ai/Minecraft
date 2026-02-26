import urllib.request
import xml.etree.ElementTree as ET

url = "https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/software/bernie/geckolib/geckolib-fabric-1.21.1/maven-metadata.xml"
req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
try:
    with urllib.request.urlopen(req) as response:
        xml_data = response.read()
        root = ET.fromstring(xml_data)
        
        # Try to find the latest version
        latest = root.find('.//versioning/latest')
        if latest is not None and latest.text:
            print(latest.text)
        else:
            # Fallback to the last version in the versions list
            versions = root.findall('.//versioning/versions/version')
            if versions:
                print(versions[-1].text)
            else:
                print("Version not found")
except Exception as e:
    print(f"Error: {e}")
