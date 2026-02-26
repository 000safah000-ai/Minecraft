import urllib.request
import xml.etree.ElementTree as ET

def get_latest_version():
    url = "https://maven.blamejared.com/foundry/veil/veil-fabric-1.21.1/maven-metadata.xml"
    req = urllib.request.Request(url, headers={"User-Agent": "Mozilla/5.0"})
    
    try:
        response = urllib.request.urlopen(req)
        xml_data = response.read()
        
        root = ET.fromstring(xml_data)
        
        # Try to get the <latest> tag first
        latest = root.find(".//versioning/latest")
        if latest is not None and latest.text:
            return latest.text
            
        # Fallback to the last <version> tag
        versions = root.findall(".//version")
        if versions:
            return versions[-1].text
            
    except Exception as e:
        print(f"Error fetching version: {e}")
        
    return None

if __name__ == "__main__":
    version = get_latest_version()
    if version:
        print(version)
